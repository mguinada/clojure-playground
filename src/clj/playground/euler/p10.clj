(ns playground.euler.p10
  (:require [playground.euler.p7 :as p7]))

(apply + (take-while #(< % 2000000)  (p7/primes)))
