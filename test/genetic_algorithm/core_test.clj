(ns genetic-algorithm.core-test
  (:require [clojure.test :refer :all]
            [genetic-algorithm.core :refer :all]))

(defn eval-function1 [solution]
  (apply + solution))

(defn eval-function2 [solution]
  (apply - solution))

(deftest ga-test
  (testing "A Genetic Algorithm can solve easy optimization problem"
    (is (= (genetic-algorithm.core/run 20 30 5 0.5 eval-function1 100 0.05) [0 0 0 0 0]))
    (is (= (genetic-algorithm.core/run 20 30 5 0.5 eval-function2 100 0.05) [0 1 1 1 1]))))

