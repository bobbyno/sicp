(ns test
  (:use clojure.test))

(def tests
     ['clojure.test_koans 'sicp.week_one])

(doseq [test tests] (require test))

(apply run-tests tests)

(shutdown-agents)