(ns sicp.week_two
  (:use clojure.test))

; linear process for calculating n factorial
(defn fact-iter [product counter max-count]
  (if (> counter max-count)
      product
      (fact-iter (* counter product) (+ counter 1) max-count)))

(defn factorial [n]
  (fact-iter 1 1 n))

(deftest test-factorial
  (are [expression expected] (= expression expected)
    (factorial 3)   6
    (factorial 4)   24))

; Exercise 1.11.  A function f is defined by the rule that
; f(n) = n if n<3 and f(n) = f(n - 1) + 2f(n - 2) + 3f(n - 3) if n> 3.
; Write a procedure that computes f by means of a recursive process.
; Write a procedure that computes f by means of an iterative process.
(defn f [n]
  (if (< n 3) n
      (+ (f (- n 1))
         (* 2 (f (- n 2)))
         (* 3 (f (- n 3))))))

(deftest test-f
  (are [expression expected] (= expression expected)
    (f 2)   2
    (f 3)   4
    (f 4)   11))

; Work on Exercise 1.16 - In general, the technique of defining an invariant quantity that remains
; unchanged from state to state is a powerful way to think about the design of iterative algorithms.