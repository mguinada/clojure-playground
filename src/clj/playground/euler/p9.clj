(ns playground.euler.p9)

(defn pythagorean-triplet
  "Calculate the pythagorean triplet for m n with m and n being natual numbers bigger than zero
  and m > n"
  [m n]
  {:pre [(> m 0) (> n 0) (> m n)]}
  [(- (* m m) (* n n) ) (* 2 n m) (+ (* n n) (* m m))])

(defn pythagorean-triplets
  ([] (pythagorean-triplets 2 1))
  ([m n]
   (if (> m n)
     (lazy-seq (cons (pythagorean-triplet m n) (pythagorean-triplets m (inc n))))
     (recur (inc m) 1))))

(apply * (first (drop-while #(not= 1000 (apply + %)) (pythagorean-triplets))))
