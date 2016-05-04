(ns clojure-playground.euler.p2)

;; Fibonacci infinite lazy sequence
(def fibo (lazy-cat [0 1] (map + fibo (rest fibo))))

(reduce + (take-while (partial >= 4000000) (filter even? fibo)))
