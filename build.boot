(set-env!
 :source-paths    #{"src/clj"}
 :dependencies '[[adzerk/boot-reload "0.4.8" :scope "test"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/tools.trace        "0.7.9"]])

(require
 '[boot.task.built-in          :refer [aot]]
 '[adzerk.boot-reload          :refer [reload]])

;;clojure namespace tools integration
(swap! boot.repl/*default-dependencies* conj
      '[org.clojure/tools.namespace "0.2.11"])

;; atom-proto-repl dependency
(swap! boot.repl/*default-dependencies* conj
      '[compliment "0.2.7"])

;; CIDER integration
(swap! boot.repl/*default-dependencies*
       concat '[[cider/cider-nrepl "0.12.0-SNAPSHOT"]
                [refactor-nrepl "2.0.0-SNAPSHOT"]])

(swap! boot.repl/*default-middleware*
       conj 'cider.nrepl/cider-middleware)

;; Light Table integration
(swap! boot.repl/*default-dependencies*
       concat '[[lein-light-nrepl "0.3.2"]])
(swap! boot.repl/*default-middleware*
       conj 'lighttable.nrepl.handler/lighttable-ops)

;; Tasks

(deftask build []
  (comp (speak)
        (aot)))

(deftask run []
  (comp (watch)
        (repl)
        (reload)
        (build)))

(deftask development []
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))
