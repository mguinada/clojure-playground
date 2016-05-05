(ns playground.recursion
  (:refer-clojure :exclude [repeat]))

;; Recursive length of a collection

(defn length
  ([coll]
   (length coll 0))
  ([coll sum]
   (if (empty? coll)
     sum
     (recur (rest coll) (inc sum)))))

(length '(1 2 3 4 5))

;; Factorial

(defn fact
  [n]
  (loop [a n b 1]
    (if (= a 1)
      b
      (recur (dec a) (* a b)))))

(fact 10)

;; compute factorial with a lazy seq.
;; In clojure this is usually more efficient than recursion.
;; It won't blow the stack because range returns a lazy seq, and reduce walks
;; across the seq without holding onto the head. reduce makes use of chunked seqs if it can
;; more at http://stackoverflow.com/a/1663053

(defn fact
  [n]
  (reduce * (range 1 (inc n))))

(fact 7)

;; Fibonacci

;; Fibonacci infinite lazy sequence using co-recursion
;; See more at https://en.wikibooks.org/wiki/Clojure_Programming/Examples/Lazy_Fibonacci
;; and http://clojuredocs.org/clojure.core/lazy-seq

(def fibo (lazy-cat [0 1] (map + fibo (rest fibo))))

(take 10 fibo)

;; This approach is safer that the one above.
;; It does not hold to top level vars that will prevent grabage collection

(defn fibo
  ([] (fibo 0 1))
  ([a b]
   (lazy-seq (cons a (fibo b (+ b a))))))

(take 10 (fibo))

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

(primes-recurr 60)

;; Sieve of Eratosthenes prime numbers lazy seq
(defn primes
  ([]
   (primes (iterate inc 2)))
  ([seq]
   (cons (first seq)
         (lazy-seq (primes (filter #(not= 0 (mod % (first seq))) (rest seq)))))))

(take 17 (primes))
(nth (primes) (dec 17))
