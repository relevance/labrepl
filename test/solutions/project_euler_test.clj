(ns solutions.project-euler-test
  (:use circumspec solutions.project-euler))

(testing-fn problem-1
  (-> 233168)
  (10 -> 23))

(testing-fn problem-2
  (-> 4613732)
  (40 -> 44))

(testing-fn prime-factors
  (0 -> ())
  (1 -> ())
  (2 -> [2])
  (3 -> [3])
  (4 -> [2 2])
  (12 -> [2 2 3])
  (600851475143 -> [71 839 1471 6857]))

(testing-fn problem-3
  (4 -> 2)
  (12 -> 3)
  (-> 6857))

(testing-fn problem-4
  (1 -> 0)
  (10 -> 9)
  (100 -> 9009)
  (1000 -> 906609))

