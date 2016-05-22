(ns playground.macros)

(defmacro for-each
  [holder _ coll body]
  `(letfn [(on-each-element# [element#]
             (let [~(symbol holder) element#] ~body))]
     (map on-each-element# ~coll)))

(defmacro for-each
  [holder _ coll body]
  `(doseq [~(symbol holder) ~coll] ~body))

(macroexpand-1
 '(for-each x in [:a :b :c]
           (println x)))

(for-each e in (range 1 10)
          (println "element is" e))

(defmacro lazy? [x] `(instance? clojure.lang.LazySeq ~x))
