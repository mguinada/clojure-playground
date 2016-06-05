(ns playground.recursion
  (:refer-clojure :exclude [repeat reverse]))

;; recursive length of a collection

(defn length
  ([coll]
   (length coll 0))
  ([coll sum]
   (if (empty? coll)
     sum
     (recur (rest coll) (inc sum)))))

(length '(1 2 3 4 5))

;; repeats val n times in a collection

(defn repeat
  ([val n] (repeat val n '()))
  ([val n coll]
   (if (zero? n)
     coll
     (recur val (dec n) (cons val coll)))))

(repeat :x 5)

;; Factorial

(defn fact-recur
  [n]
  (loop [a n b 1]
    (if (= a 1)
      b
      (recur (dec a) (* a b)))))

(fact-recur 10)

;; NOTE: non-recursive alternative implementation
;; Compute factorial with a lazy seq.
;; In clojure this is usually more efficient than recursion.
;; It won't blow the stack because range returns a lazy seq, and reduce walks
;; across the seq without holding onto the head. reduce makes use of chunked seqs if it can
;; more at http://stackoverflow.com/a/1663053

(defn fact
  [n]
  (reduce * (range 1 (inc n))))

(fact 7)

;; https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
;;
;; 1. Create a list of consecutive integers from 2 through n: (2, 3, 4, ..., n).
;; 2. Initially, let p equal 2, the smallest prime number.
;; 3. Enumerate the multiples of p by counting to n from 2p in increments of p, and mark them in the list (these will be 2p, 3p, 4p, ... the p itself should not be marked).
;; 4. Find the first number greater than p in the list that is not marked. If there was no such number, stop. Otherwise, let p now equal this new number (which is the next prime), and repeat from step 3.

(defn primes
  [n]
  (loop [p 2 coll (range 2 (inc n))]
    (if (nil? (first (drop-while (partial >= p) coll)))
      coll
      (recur (inc p) (remove #(and (zero? (mod % p)) (not= % p)) coll)))))

(primes 60)
