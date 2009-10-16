(ns test
  (:use clojure.test))

(def tests
     ['clojure.test_koans 'clojure.test_forms
      'sicp.week_one 'sicp.week_two])

(doseq [test tests] (require test))

(apply run-tests tests)

(shutdown-agents)