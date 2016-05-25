(ns playground.euler.p10
  (:require [playground.euler.p7 :as p7]))

(comment
  "The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
   Find the sum of all the primes below two million.")

(apply + (take-while #(< % 2000000)  (p7/primes)))
