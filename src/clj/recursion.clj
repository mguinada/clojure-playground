(ns clojure-playground.recursion)

;; refursive factorial implementations
(defn fact
  [n]
  (loop [a n b 1]
    (if (= a 1)
      b
      (recur (dec a) (* a b)))))

(fact 10)

;; in clojure this is usually more eficiente than recursion
(defn fact
  [n]
  (reduce * (range 1 (inc n))))

(fact 7)

;; Fibonacci infinite lazy sequence using co-recursion
;; See more at https://en.wikibooks.org/wiki/Clojure_Programming/Examples/Lazy_Fibonacci
(def fibo (lazy-cat [0 1] (map + fibo (rest fibo))))
