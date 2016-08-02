(ns playground.matrix
  (:refer-clojure :exclude [update]))

(defprotocol Matrix
  "Protocol to work with 2D structures "
  (dims [matrix])
  (cols [matrix])
  (rows [martix])
  (update [matrix i j value])
  (lookup [matrix i j]))

(extend-protocol Matrix
  clojure.lang.IPersistentVector
  (dims [this]
    [(count this) (count (first this))])
  (cols [this]
    (apply map vector this))
  (rows [this]
    (seq this))
  (lookup [this i j]
    (get-in this [i j]))
  (update [this i j value]
    (assoc-in this [i j] value)))


(def v [[1 2] [3 4] [5 6]])
(dims v)
(cols v)
(rows v)
(lookup v 1 1)
(def v (update v 1 1 5))
(lookup v 1 1)
(v [1 1])
