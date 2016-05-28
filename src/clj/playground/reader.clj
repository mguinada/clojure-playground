(ns playground.reader
  (:import [clojure.lang Seqable]))

(deftype Point [x y z]
  Seqable
  (seq [_] (seq [x y z])))

(defn point
  [x y z]
  (->Point x y z))

;; the Point printer that prints using our custom literal
(defmethod print-method Point [point ^java.io.Writer w]
  (.write w "#playground/point ")
  (print-method (seq point) w))

;; the Point reader that will be used to create a Point instance from the reader
;; (see data_readers.clj at the root of the CLASS_PATH)
(defn point-reader
  [coll]
  (apply point coll))

(point-reader [1 2 3])

;; The reader literal
#playground/point (1 2 3)
