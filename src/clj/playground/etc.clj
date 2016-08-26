(ns playground.etc)

(defn factor?
  "Is y a factor of x?"
  [x y]
  (zero? (rem x y)))

(defn factorization
  "The factors of x"
  [x]
  (->> (range 2 x)
       (filter (partial factor? x))
       (mapcat #(repeat (/ x %) %))))
