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

(defn fact
  [n]
  (loop [a n b 1]
    (if (= a 1)
      b
      (recur (dec a) (* a b)))))

(fact 10)

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

(defn primes-recur
  [n]
  (loop [p 2 coll (range 2 (inc n))]
    (if (nil? (first (drop-while (partial >= p) coll)))
      coll
      (recur (inc p) (remove #(and (zero? (mod % p)) (not= % p)) coll)))))

(primes-recur 60)

;; Sieve of Eratosthenes prime numbers lazy seq
(defn primes
  ([]
   (primes (iterate inc 2)))
  ([seq]
   (cons (first seq)
         (lazy-seq (primes (filter #(not= 0 (mod % (first seq))) (rest seq)))))))

(take 17 (primes))
(nth (primes) (dec 17))

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

(take 17 (primes))
(nth (primes) (dec 17))

;; see also http://clj-me.cgrand.net/2009/07/30/everybody-loves-the-sieve-of-eratosthenes/

;; https://en.wikipedia.org/wiki/Divisor

(defn divisors
  "Produces a lazy seq of the factors of a number"
  [n]
  (lazy-cat (filter #(zero? (rem n %)) (range 1 (inc (/ n 2)))) [n]))

;; Triangular numbers - https://en.wikipedia.org/wiki/Triangular_number

(def triangular-numbers
  (map #(long (/ (* % (inc %)) 2)) (iterate inc 1)))

(take 28 triangular-numbers)

;; Perfect numbers - http://world.mathigon.org/Sequences

(defn perfect-number?
  "A perfect number is equal to the sum of all of it's
  divisors excluding the numner it self"
  [n]
  (letfn [(factors [n] (->> (range 2 (inc (quot n 2)))
                            (filter #(zero? (rem n %)))
                            (cons 1)))]
    (= n (apply + (factors n)))))

(def perfect-numbers
  (filter perfect-number? (iterate inc 6)))
