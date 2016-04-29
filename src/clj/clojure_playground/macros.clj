(ns clojure-playground.macros)

(defmacro for-each
  [holder _ coll body]
  `(letfn [(on-each-element# [element#]
             (let [~(symbol holder) element#] ~body))]
     (map on-each-element# ~coll)))

(macroexpand-1
 '(for-each element in elements
           (println element)))

(for-each e in (range 1 10)
          (println "element is" e))
