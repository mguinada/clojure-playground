(ns playground.value)

(comment
  "Mimic rails active support blank? & present?

  Notes:

  - Protocol dispach bug

  http://dev.clojure.org/jira/browse/CLJ-825?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel

  - Dispatch on concrete classes may be a need

  https://groups.google.com/forum/#!topic/clojure-dev/-zoLA78--Mo

  - Ways of implementing polymorphism

  http://www.rkn.io/2014/03/05/clojure-cookbook-polymorphic-functions/
  http://stackoverflow.com/a/5027749")

(defprotocol Value
  (blank? [this] [this args]))

(extend-protocol Value
  nil
  (blank? [_]
    true)
  String
  (blank? [str]
    (empty? str))
  clojure.lang.IPersistentCollection
  (blank? [coll]
    (empty? coll))
  java.lang.Object
  (blank? [_]
    false)
  clojure.lang.Fn
  (blank?
    ([f]
     (blank? (f)))
    ([f args]
     (blank? (apply f args)))))

(def present? (complement blank?))

(blank? 1)
(blank? nil)
(present? nil)

(blank? "")
(blank? "string")
(present? "string")

(blank? [])
(present? [])
(blank? {})
(present? '())
(present? {:a 1})

(def x (fn [] nil))
(def y (fn [v] v))

(blank? x)
(blank? y [1])
