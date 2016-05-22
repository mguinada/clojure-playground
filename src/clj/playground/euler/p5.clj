(ns playground.euler.p5)

(comment
  "2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
  What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?")

;; ineficient solution
(defn evenly-divisable-by?
  "Returns true if n is evenly divisable by all the elements in coll"
  [n coll]
  (every? #(zero? (rem n %)) coll))

(def natural-numbers (iterate inc 1))

;; takes too long
;; (time (first (filter #(evenly-divisable-by? % (range 1 21)) natural-numbers)))

;; more efficien solution with the euclidian method - https://blog.ochronus.com/learn-clojure-with-project-euler-127dedcbff54#.utbd42sam
(defn gcd
  "Greatest common divisor"
  [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(defn lcm
  "Least common multiple"
  [a b]
  (/ (* a b) (gcd a b)))

(reduce #(lcm %1 %2) (range 1 21))
