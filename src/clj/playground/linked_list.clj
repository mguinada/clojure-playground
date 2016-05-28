(ns playground.linked-list
  (:import [clojure.lang Seqable Counted]
           [java.lang UnsupportedOperationException]))

(defprotocol INode
  (car [node] "yields the value of the node")
  (cdr [node] "yields the next element of the list")
  (set-car! [node value] "sets the value of car")
  (set-cdr! [mode value] "sets the value of cdr"))


(deftype EmptyListNode []
  INode
  (car [_] nil)
  (set-car! [_ _]
    (throw (UnsupportedOperationException. "can't set car on an empty node")))
  (cdr [_] nil)
  (set-cdr! [_ _ ]
    (throw (UnsupportedOperationException. "can't set cdr on an empty node")))
  Counted
  (count [_] 0)
  Seqable
  (seq [_] (seq [])))

(def EMPTY (->EmptyListNode))

(defn empty-list? [ll] (instance? EmptyListNode ll))

(deftype Node [^:volatile-mutable car ^:volatile-mutable cdr]
  INode
  (car [_] car)
  (set-car! [this value] (set! car value) this)
  (cdr [_] cdr)
  (set-cdr! [this value] (set! cdr value) this)
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

(defn node
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
         link (fn [current-node new-value]
                (-> current-node
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

(def llist (node :a (node :b (node :c))))

;; it's Seqable
(seq llist)
(seq (linked-list))

(linked-list)
(linked-list :a)
(linked-list :a :b)
(linked-list :a :b :c)
(linked-list :a :b :c :d :e :f)

(def llist (linked-list :a :b :c :d :e :f))

(map str llist)
(reduce + (apply linked-list (range 10)))

(empty-list? (linked-list))
(empty-list? (linked-list :a))

;; Countable

(count (linked-list))
(count (linked-list :a))
(count (linked-list :a :b))
(count (linked-list :a :b :c))
(count (linked-list :a :b :c :d :e :f))

;; NOTE: I'm not being able able to make the custom data reader due to the
;; encapsulation enforced with :volatile-mutable anottation.
;; the following line throws: No matching field found: car for class playground.linked_list.Node
;;
;; #playground/linked-list (:a :b :c)
