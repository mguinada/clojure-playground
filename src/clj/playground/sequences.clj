(ns playground.sequences
  (:require [clojure.math.numeric-tower :as math]
            [clojure.string :as string]))

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

;; Triangular numbers
(def triangular-numbers (lazy-seq (cons 0 (map + triangular-numbers (iterate inc 1)))))

(take 10 triangular-numbers)

;; or

(defn triangular-numbers
  ([] (triangular-numbers (iterate inc 0)))
  ([coll]
   (cons (first coll) (lazy-seq (map + (triangular-numbers coll) (rest coll))))))

(take 10 (triangular-numbers))

;; Polygonal number

(defn polygonal-number
  [n]
  (/ (+ (math/expt n 2) n) 2))

;; the best way to do it

(def polygonal-numbers
  (map polygonal-number (iterate inc 1)))

;; with data recursion

(def polygonal-numbers
  (lazy-seq (cons (polygonal-number 1) (map polygonal-number (iterate inc 2)))))

(take 10 polygonal-numbers)

;; Catalan numbers - https://www.quora.com/What-is-most-famous-number-series-besides-Fibonacci/answer/Yash-Farooqui?srid=7MMi

(defn fact
  [n]
  (reduce * (range 1 (inc n))))

(defn catalan-number
  [n]
  {:pre [(>= n 0)]}
  (/ (fact (* 2 n)) (* (fact (inc n)) (fact n))))

(defn catalan-numbers
  ([] (catalan-numbers (iterate inc 0)))
  ([coll]
   (lazy-seq (cons (catalan-number (first coll)) (map catalan-number (rest coll))))))

(take 10 (catalan-numbers))

;; Look and say sequence - https://en.wikipedia.org/wiki/Look-and-say_sequence

(defn num->coll
  "Converts a number to a collection of it's digits"
  [i]
  (map #(Character/digit % 10) (seq (str i))))

(defn look-and-say
  "Look and say a number"
  ([look]
   (let [digits (num->coll look)]
     (loop [current (first digits) coll digits say []]
       (if (empty? coll)
         (-> say (string/join) (bigdec))
         (let [equals-count (count (take-while #(= current %) coll)) tail (nthrest coll equals-count)]
           (recur (first tail) tail (conj say (str equals-count current)))))))))

(defn look-and-say-sequence
  "Infinite lazy sequence of look and say numbers"
  ([] (look-and-say-sequence 1M))
  ([a] (lazy-seq (cons a (look-and-say-sequence (look-and-say a))))))

(take 10 (look-and-say-sequence))

;; Pronic numbers - https://en.wikipedia.org/wiki/Pronic_number

(def pronic-numbers
  (map * (iterate inc 0) (iterate inc 1)))

(take 20 pronic-numbers)

;; Collatz conjecture - https://en.wikipedia.org/wiki/Collatz_conjecture

(defn collatz-number
  [x]
  (when-not (= x 1)
    (if (even? x)
      (/ x 2)
      (inc (* 3 x)))))

(defn collatz-sequence
  [starting-at]
  (if-not (nil? starting-at)
    (lazy-seq (cons starting-at (collatz-number (collatz-number starting-at))))))
