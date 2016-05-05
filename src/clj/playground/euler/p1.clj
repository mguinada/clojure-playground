(ns playground.euler.p1)

(defn multiple-of-3-or-5?
  [n]
  (some zero? (map #(mod n %) '(3 5))))

(defn multiples-of-3-or-5-bellow-n
  [n]
  (filter multiple-of-3-or-5? (range n)))

(defn sum-of-multiples-of-3-or-5-bellow-n
  [n]
  (reduce + (multiples-of-3-or-5-bellow-n n)))

(sum-of-multiples-of-3-or-5-bellow-n 1000)
