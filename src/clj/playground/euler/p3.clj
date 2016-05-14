(ns playground.euler.p3)

(comment
  "The prime factors of 13195 are 5, 7, 13 and 29.
   What is the largest prime factor of the number 600851475143 ?")

(defn divisors
  [n]
  (sort (into [n] (filter #(zero? (rem n %)) (range 1 (inc (long (Math/sqrt n))))))))

(defn prime?
  "Predicate that returns true if n is a prime number"
  [n]
  (and (> n 1) (= (set [1 n]) (set (divisors n)))))

(defn prime-factors
  [n]
  (filter prime? (divisors n)))

(last (prime-factors 600851475143))
