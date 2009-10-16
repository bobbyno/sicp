(ns sicp.week_one
  (:use clojure.test))

; Week One: 1.1 - 1.8 - http://groups.google.com/group/wizardbookstudy/web/weekly-assignments

; 1.1  The Elements of Programming - http://mitpress.mit.edu/sicp/full-text/book/book-Z-H-10.html

; Exercise 1.1.  Below is a sequence of expressions. 
; What is the result printed by the interpreter in response to each expression? 
; Assume that the sequence is to be evaluated in the order in which it is presented.
(deftest expressions
  (are [expression expected] (= expression expected)
    10                                  10
    (+ 5 3 4)                           12
    (- 9 1)                             8
    (/ 6 2)                             3
    (+ (* 2 4) (- 4 6))                 6

    (def a 3)                           #'sicp.week_one/a
    (def b (+ a 1))                     #'sicp.week_one/b

    (+ a b (* a b))                     19
    (= a b)                             false
    
    (if (and (> b a) (< b (* a b)))
      b
      a)                                4

    (cond (= a 4) 6
          (= b 4) (+ 6 7 a)
          :else 25)                     16

    (+ 2 (if (> b a) b a))              6

    (* (cond (> a b) a
             (< a b) b
             :else -1)
       (+ a 1))                         16))

; Exercise 1.2.  Translate the following expression into prefix form:
; Numerator:   5 + 4 + (2 - (3 - (6 + 4/5)))
; Denominator: 3(6 - 2)(2 - 7)
(deftest prefix-form
  (is (= -37/150
    (/ (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5)))))
       (* 3 (- 6 2) (- 2 7))))))

; Exercise 1.3.  Define a procedure that takes three numbers as arguments and returns the sum
; of the squares of the two larger numbers.
(defn square [x]
  (* x x))

(defn sum-of-largest-squares [x y z]
    (cond
      (and (<= x y) (<= x z))
        (+ (square y) (square z))
      (and (<= y x) (<= y z))
        (+ (square x) (square z))
      :else
        (+ (square x) (square y))))

(deftest test-sum-of-largest-squares
  (are [expression expected] (= expression expected)
    ; permutations prove we handle ordering correctly
    (sum-of-largest-squares 1 2 3)   13
    (sum-of-largest-squares 1 3 2)   13
    (sum-of-largest-squares 2 1 3)   13
    (sum-of-largest-squares 2 3 1)   13
    (sum-of-largest-squares 3 2 1)   13
    (sum-of-largest-squares 3 1 2)   13))

; Exercise 1.4.  Observe that our model of evaluation allows for combinations
; whose operators are compound expressions. Use this observation to describe
; the behavior of the following procedure:
; (define (a-plus-abs-b a b)
;   ((if (> b 0) + -) a b))
; not sure if this works in clojure, but apparently in Scheme, 

; Exercise 1.5 - applicative vs. normal order
;(defn p []
;  (p))
;
;(defn test [x y]
;  (if (= x 0)
;      0
;      y))

; Then he evaluates the expression
;(test 0 (p))

; normal-order evaluation is "fully expand, and then reduce"
; applicative-order is "evaluate the arguments, and then apply"

; In clojure, this will cause a StackOverflowError since it uses applicative order.
; In normal order, the test expression would first evaluate to (if (= x 0) 0 y), which becomes
; (if (= 0 0) 0 y), which returns 0 since the predicate is true.
; (p) would never need to be evaluated, and so would never enter the infinite loop found in applicative order.

; Exercise 1.6 - Since new-if is a procedure, not a special form, all of its arguments will be evaluated by the
; interpreter. The else clause is a recursive call, so sqrt-iter will never return a value.

; Exercise 1.7 -
(defn average [x y]
  (/ (+ x y) 2))

(defn improve [guess x]
  (average guess (/ x guess)))

(defn close-to [x y]
  (< (. Math abs (- x y)) 0.001))

(defn good-enough? [guess x]
  (close-to (square guess) x))

(defn sqrt-iter [guess x]
  (if (good-enough? guess x)
      guess
    (sqrt-iter (improve guess x) x)))

(defn sqrt [x]
  (sqrt-iter 1.0 x))

(deftest test-square-root
  (are [expression expected] (= expression expected)
    (close-to 2 (sqrt 4))   true
    (close-to 3 (sqrt 9))   true))

; Exercise 1.8 - Newton's method for cube roots is based on the fact that if y is an approximation
; to the cube root of x, then a better approximation is given by the value ((x / y^2) + 2y) / 3
; Use this formula to implement a cube-root procedure analogous to the square-root procedure.
(defn cube [x]
  (* x x x))

(defn improve-cube [guess x]
  (/ (+ (/ x (square guess)) (* 2.0 guess)) 3.0))

(defn good-enough-cube? [guess x]
  (close-to (cube guess) x))

(defn cube-iter [guess x]
  (if (good-enough-cube? guess x)
    guess
    (cube-iter (improve-cube guess x) x)))

(defn cube-root [x]
  (cube-iter 1.0 x))

(deftest test-cube-root
  (are [expression expected] (= expression expected)
    (cube-root 1)   1.0
    (close-to (cube-root 8)   2.0)  true
    (close-to (cube-root 27)  3.0)  true))