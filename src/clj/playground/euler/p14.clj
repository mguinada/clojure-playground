(ns playground.euler.p14)

(defn collatz
  [n]
  {:pre [(number? n)]}
  (cond
    (even? n) (/ n 2)
    (odd? n) (inc (* 3 n))))

(defn collatz-seq
  [a]
  (lazy-seq
   (when-not (= 1 a)
     (let [cn (collatz a)]
       (cons cn (collatz-seq cn))))))

(collatz 26)
(collatz 13)
(require '[playground.macros :as m])
(take 10 (collatz-seq 26))
(m/lazy? (collatz-seq 26))

(collatz-seq 26)
(collatz-seq 1)

(defn longest-chain
  "Result for num under one million is correct (837799) but it takes a few second to compute."
  ([] (longest-chain 1000000))
  ([limit]
   (letfn [(collatz-counts [m num] (assoc m num (count (collatz-seq num))))]
     (->> (range 2 (inc limit))
          (reduce collatz-counts {})
          (apply max-key val)
          (first)))))

(longest-chain 1000)
(longest-chain 1000000)
