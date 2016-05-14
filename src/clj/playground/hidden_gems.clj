(ns playground.hidden-gems)

;; Returns a map from distinct items in coll to the number of times they appear.
;; https://clojuredocs.org/clojure.core/frequencies

(frequencies '(:a :b :b :c)) ;;=> {:a 1, :b 2, :c 1}

;; strings used with maps are useful to generate long sequences

(map {\a "water" \b "earth" \c "wind" \d "fire"} "abcaab") ;;=> ("water" "earth" "wind" "water" "water" "earth")

;; set can be used  for filtering

(filter #{1 2 3} (range 100)) ;;=> (1 2 3)
