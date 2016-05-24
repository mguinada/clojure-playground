(ns playground.euler.p7)

(comment
  "By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
  What is the 10 001st prime number?")

;; Efficient prime number lazy sequence
;;
;; See more at https://web.archive.org/web/20150710134640/http://diditwith.net/2009/01/20/YAPESProblemSevenPart2.aspx
;; or at https://www.cs.hmc.edu/~oneill/papers/Sieve-JFP.pdf (The Genuine Sieve of Eratosthenes2, by Melissa E. O’Neill)

(defn primes
  []
  (letfn [(reinsert [table value factor] (update table (+ value factor) conj factor))
          (primes* [composites value] (if-let [factors (get composites value)]
                                        (recur (reduce #(reinsert %1 value %2) (dissoc composites value) factors) (inc value))
                                        (lazy-seq (cons value (primes* (assoc composites (* value value) [value]) (inc value))))))]
    (primes* {} 2)))

(last (take 10001 (primes)))
