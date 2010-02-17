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

(testing-fn divides
  (2 2 -> true)
  (2 3 -> false)
  (3 2 -> false))

(testing-fn unique-factors
  (10 -> [10 9 8 7 6])
  (6 -> [6 5 4])
  (5 -> [5 4 3]))

(testing-fn problem-5
  (2 -> 2)
  (3 -> 6)
  (4 -> 12)
  (10 -> 2520)
  (20 -> 232792560))


