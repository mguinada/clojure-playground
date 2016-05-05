(ns playground.euler.p3)

;; https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
;;
;; 1. Create a list of consecutive integers from 2 through n: (2, 3, 4, ..., n).
;; 2. Initially, let p equal 2, the smallest prime number.
;; 3. Enumerate the multiples of p by counting to n from 2p in increments of p, and mark them in the list (these will be 2p, 3p, 4p, ... the p itself should not be marked).
;; 4. Find the first number greater than p in the list that is not marked. If there was no such number, stop. Otherwise, let p now equal this new number (which is the next prime), and repeat from step 3.

(defn primes-recurr
  [n]
  (loop [p 2 coll (range 2 (inc n))]
    (if (nil? (first (drop-while (partial >= p) coll)))
      coll
      (recur (inc p) (remove #(and (zero? (mod % p)) (not= % p)) coll)))))

(primes-recurr 600)

;; Prime numbers lazy seq
(defn primes
  ([]
   (primes (iterate inc 2)))
  ([seq]
   (cons (first seq)
         (lazy-seq (primes (filter #(not= 0 (mod % (first seq))) (rest seq)))))))

(take 109 (primes))
(nth (primes) 108)
