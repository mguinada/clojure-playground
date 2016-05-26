(ns playground.linked-list
  (:import [clojure.lang Seqable Counted]))

(defprotocol INode
  (car [node] "yields the value of the node")
  (cdr [node] "yields the next element of the list")
  (set-car! [node value] "sets the value of car")
  (set-cdr! [mode value] "sets the value of cdr"))

(declare LINKED-LIST-EMPTY-NODE)

(defn empty-list?
  [llist]
  (= llist LINKED-LIST-EMPTY-NODE))

(deftype Node [^:volatile-mutable car ^:volatile-mutable cdr]
  INode
  (car [_] car)
  (set-car! [this value] (set! car value) this)
  (cdr [_] cdr)
  (set-cdr! [this value] (set! cdr value) this)
  Counted
  (count [this]
    (if (empty-list? this)
      0
      (loop [current this counter 0]
        (if (nil? current)
          counter
          (recur (.cdr current) (inc counter))))))
  Seqable
  (seq [this]
    (if (empty-list? this)
      (seq [])
      (loop [current this acc []]
        (if (nil? current)
          (seq acc)
          (recur (.cdr current) (conj acc (.car current))))))))

(defn- node
  "Creates a linked list node"
  ([] (node nil nil))
  ([car] (node car nil))
  ([car cdr] (->Node car cdr)))

(def LINKED-LIST-EMPTY-NODE (node))

(defn linked-list
  "Creats a new linked list from the given args"
  ([] LINKED-LIST-EMPTY-NODE)
  ([a] (node a))
  ([a b] (node a (node b)))
  ([a b & more]
   (let [head (linked-list a b)
         tail (cdr head)
         link (fn [current-node new-value]
                (-> current-node
                    (set-cdr! (node new-value))
                    (cdr)))]
     (reduce link tail more)
     head)))

;; printer
(defmethod print-method Node [node ^java.io.Writer w]
  (.write w (str "<" (seq node) ">")))

;; node value reading

(car (node :a))

;; a composed linked list
(node :a (node :b (node :c)))
(def llist (node :a (node :b (node :c))))

;; it's Seqable
(seq llist)

(linked-list)
(linked-list :a)
(linked-list :a :b)
(linked-list :a :b :c)
(linked-list :a :b :c :d :e :f)

(def llist (linked-list :a :b :c :d :e :f))

(map str llist)
(reduce + (apply linked-list (range 10)))

;; Countable

(count (linked-list))
(count (linked-list :a))
(count (linked-list :a :b))
(count (linked-list :a :b :c))
(count (linked-list :a :b :c :d :e :f))

(require '[clojure.tools.trace :as t])

;(t/trace (linked-list :a :b :c))
(t/trace-vars linked-list)



;; TODO

;; implements stadnard clojure protocols
;; inspect
;; reader/writer literal
