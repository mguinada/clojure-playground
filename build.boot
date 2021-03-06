(set-env!
 :source-paths    #{"src/clj"}
 :dependencies '[[adzerk/boot-reload "0.4.8" :scope "test"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/tools.trace        "0.7.9"]
                 [metosin/boot-alt-test "0.1.2" :scope "test"]])

(require
 '[boot.task.built-in    :refer [aot]]
 '[adzerk.boot-reload    :refer [reload]]
 '[metosin.boot-alt-test :refer [alt-test]])

;; clojure namespace tools integration
(swap! boot.repl/*default-dependencies* conj
      '[org.clojure/tools.namespace "0.2.11"])

;; atom-proto-repl dependency
(swap! boot.repl/*default-dependencies* conj
      '[compliment "0.2.7"])

;; CIDER integration
(swap! boot.repl/*default-dependencies*
       concat '[[cider/cider-nrepl "0.15.1"]
                [refactor-nrepl "2.3.1"]])

(swap! boot.repl/*default-middleware*
       conj 'cider.nrepl/cider-middleware)

;; Light Table integration
;; (swap! boot.repl/*default-dependencies*
;;        concat '[[lein-light-nrepl "0.3.2"]])
;; (swap! boot.repl/*default-middleware*
;;        conj 'lighttable.nrepl.handler/lighttable-ops)

;; Tasks

(deftask build []
  (comp (speak)
        (aot)))

(deftask run []
  (comp (watch)
        (repl)
        (reload)
        (build)))

(deftask run-tests
  [a autotest bool "If no exception should be thrown when tests fail"]
  (comp
   (alt-test :fail (not autotest))))

(deftask autotest []
  (comp
   (watch)
   (run-tests :autotest true)))

(deftask development []
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))
