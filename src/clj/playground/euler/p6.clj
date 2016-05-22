(ns playground.euler.p6
  (:require [clojure.math.numeric-tower :as math]))

(comment
 "The sum of the squares of the first ten natural numbers is,

  1 ^ 2 + 2 ^ 2 + ... + 10 ^ 2 = 385

  The square of the sum of the first ten natural numbers is,

  (1 + 2 + ... + 10) ^ 2 = 55 ^ 2 = 3025

  Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 − 385 = 2640.
  Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.")

(defn sum-of-squares
  [coll]
  (reduce + (map #(math/expt % 2) coll)))

(defn square-of-sum
  [coll]
  (math/expt (reduce + coll) 2))

(- (square-of-sum (range 1 101)) (sum-of-squares (range 1 101)))
