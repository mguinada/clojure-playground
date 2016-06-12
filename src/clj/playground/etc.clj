(ns playground.etc)

(defn factorization
  "The factors of x"
  [x]
  (->> (range 2 x)
       (filter (partial factor? x))
       (mapcat #(repeat (/ x %) %))))
