(ns playground.linked-list
  (:import
   [java.lang UnsupportedOperationException]
   [clojure.lang Seqable Counted IPersistentList IPersistentCollection ITransientCollection Sequential]))

(defprotocol INode
  (car [node] "yields the value of the node")
  (cdr [node] "yields the next element of the list")
  (set-car! [node value] "sets the value of car")
  (set-cdr! [mode value] "sets the value of cdr"))

(declare node linked-list)

(deftype Node [^:volatile-mutable car ^:volatile-mutable cdr]
  INode
  (car [_] car)
  (set-car! [this value] (set! car value) this)
  (cdr [_] cdr)
  (set-cdr! [this value] (set! cdr value) this)
  IPersistentCollection
  (cons [this value]
    (node value this))
  (empty [this]
    (linked-list))
  (equiv [this value]
    (if (instance? Node value)
      (and (= (.car this) (.car value))
           (- (.cdr this) (.cdr value)))))
  ITransientCollection
  (conj [this value]
    (node value this))
  (persistent [this]
    (vec (seq this)))
  IPersistentList
  Sequential
  Counted
  (count [this]
    (loop [current this counter 0]
      (if (nil? current)
        counter
        (recur (.cdr current) (inc counter)))))
  Seqable
  (seq [this]
    (loop [current this acc []]
      (if (nil? current)
        (seq acc)
        (recur (.cdr current) (conj acc (.car current)))))))

(deftype EmptyListNode []
  INode
  (car [_] nil)
  (set-car! [_ _]
    (throw (UnsupportedOperationException. "can't set car on an empty node")))
  (cdr [_] nil)
  (set-cdr! [_ _ ]
    (throw (UnsupportedOperationException. "can't set cdr on an empty node")))
  IPersistentCollection
  (cons [_ value]
    (node value))
  (empty [this] this)
  (equiv [_ value]
    (instance? EmptyListNode value))
  ITransientCollection
  (conj [_ value]
    (node value))
  (persistent [_]
    [])
  IPersistentList
  Sequential
  Counted
  (count [_] 0)
  Seqable
  (seq [_] (seq [])))

(def EMPTY (->EmptyListNode))

(defn- node
  "Creates a linked list node"
  ([] (node nil nil))
  ([car] (node car nil))
  ([car cdr] (->Node car cdr)))

(defn linked-list
  "Creats a new linked list from the given args"
  ([] EMPTY)
  ([a] (node a))
  ([a b] (node a (node b)))
  ([a b & more]
   (let [head (linked-list a b)
         tail (.cdr head)
         link (fn [current new-value]
                (-> current
                    (set-cdr! (node new-value))
                    (.cdr)))]
     (reduce link tail more)
     head)))

;; printers
(defmethod print-method EmptyListNode [node ^java.io.Writer w]
  (.write w (str "#playground/linked-list ()")))

(defmethod print-method Node [node ^java.io.Writer w]
  (.write w "#playground/linked-list ")
  (print-method (seq node) w))

;; the reader, see /data_readers.clj
(defn linked-list-reader
  [coll]
  (apply linked-list coll))

;;
;; demo
;;

;; node value reading

(car (node :a))

;; a composed linked list

(node :a (node :b (node :c)))

(linked-list)
(linked-list :a)
(linked-list :a :b)
(linked-list :a :b :c)

;; map and reduce

(map (comp str name) (linked-list :a :b :c :d :e :f))
(reduce + (apply linked-list (range 10)))

;; it's Seqable

(seq (linked-list :a :b :c :d :e :f))
(seq (linked-list))

(first (linked-list))
(first (linked-list :a :b :c :d :e :f))

(rest (linked-list))
(rest (linked-list :a :b :c :d :e :f))

(next (linked-list))
(next (linked-list :a :b :c :d :e :f))

;; Countable

(count (linked-list))
(count (linked-list :a))
(count (linked-list :a :b))
(count (linked-list :a :b :c))
(count (linked-list :a :b :c :d :e :f))

;; conj & etc.
(conj (linked-list :b :c :d :e :f) :a)
(conj (linked-list) :a)

(cons :a (linked-list :b :c :d :e :f))
(cons :a (linked-list))

(empty (linked-list :a :b :c))
(empty (linked-list))

(empty? (linked-list :a))
(empty? (linked-list))

(into (linked-list) [1 2 3])
(into (linked-list 0) [1 2 3])

(sequential? (linked-list :a :b :c))
(sequential? (linked-list))

(list? (linked-list :a :b :c))
(list? (linked-list))

(coll? (linked-list :a :b :c))
(coll? (linked-list))

;; NOTE: I'm not being able able to make the custom data reader due to the
;; encapsulation enforced with :volatile-mutable anottation.
;; the following line throws: No matching field found: car for class playground.linked_list.Node
;;
;; #playground/linked-list (:a :b :c)
