(ns playground.euler.p4
  (:require [clojure.string :as string]))

(comment
  "A palindromic number reads the same both ways. The largest palindrome made from the product of
  two 2-digit numbers is 9009 = 91 × 99. Find the largest palindrome made from the product of two
  3-digit numbers.")

(defn palindrome?
  [n]
  (let [nstr (str n)]
    (= (reverse nstr) (seq nstr))))

(def three-digit-products
  (let [r (range 100 1000)]
    (for [n1 r n2 r] (* n1 n2))))

(apply max (filter palindrome? three-digit-products))
