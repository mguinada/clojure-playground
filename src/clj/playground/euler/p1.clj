(ns playground.euler.p1)

(comment
  "If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
  Find the sum of all the multiples of 3 or 5 below 1000.")

(defn multiple-of?
  "Returns true if n is a multiple of m.
  If varadiac m is given, this predicate will return true
  when the first m that is multiple of n is computed or false if none."
  ([n] true)
  ([n m] (zero? (rem n m)))
  ([n m & more]
   (boolean (some true? (map (partial multiple-of? n) (conj more m))))))

(defn multiples-of-3-or-5 [n] (filter #(multiple-of? % 3 5) (range n)))

(reduce + (multiples-of-3-or-5 1000))
