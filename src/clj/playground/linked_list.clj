(ns playground.linked-list)

(defprotocol INode
  (car [node] "yields the value of the node")
  (cdr [node] "yields the next element of the list"))

(declare node linked-list)

(deftype Node [car cdr]
  INode
  (car [_] car)
  (cdr [_] cdr)
  clojure.lang.IPersistentCollection
  (cons [this value]
    (node value this))
  (empty [this]
    (linked-list))
  (equiv [this value]
    (if (instance? Node value)
      (and (= (.car this) (.car value))
           (= (.cdr this) (.cdr value)))))
  clojure.lang.IPersistentList
  clojure.lang.Sequential
  clojure.lang.Counted
  (count [this]
    (loop [current this counter 0]
      (if (nil? current)
        counter
        (recur (.cdr current) (inc counter)))))
  clojure.lang.Seqable
  (seq [this]
    (loop [current this acc []]
      (if (nil? current)
        (seq acc)
        (recur (.cdr current) (conj acc (.car current)))))))

(deftype EmptyListNode []
  INode
  (car [_] nil)
  (cdr [_] nil)
  clojure.lang.IPersistentCollection
  (cons [_ value]
    (node value))
  (empty [this]
    this)
  (equiv [_ value]
    (instance? EmptyListNode value))
  clojure.lang.IPersistentList
  clojure.lang.Sequential
  clojure.lang.Counted
  (count [_] 0)
  clojure.lang.Seqable
  (seq [_]
    (seq [])))

(def EMPTY (->EmptyListNode))

(defn- node
  "Creates a linked list node"
  ([] (node nil nil))
  ([car] (node car nil))
  ([car cdr] (->Node car cdr)))

(defn linked-list
  "Creats a new linked list from the given args."
  ([] EMPTY)
  ([a] (node a))
  ([a b] (node a (node b)))
  ([a b c] (node a (node b (node c))))
  ([a b c & more] (node a (node b (node c (into (linked-list) (reverse more)))))))

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
(linked-list :a :b :c :d :e :f :g :h :i)
(apply linked-list (range 100))

;; map and reduce

(map (comp str name) (linked-list :a :b :c :d :e :f))
(reduce + (apply linked-list (range 10)))

;; it's Seqable

(seq (linked-list))
(seq (linked-list :a :b :c :d :e :f))

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

; Custom literal

(into [] #playground/linked-list ())
(into [] #playground/linked-list (:a :b :c))
(into [] #playground/linked-list (:a :b :c :d :e :f :g :h :i :j))
