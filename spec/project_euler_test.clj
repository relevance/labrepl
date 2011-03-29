(ns solutions.project-euler-test
  (:use circumspec solutions.project-euler))

(testing-fn divides?
  (10 2 -> true)
  (10 3 -> false)
  (10 4 -> false)
  (10 5 -> true))

(testing divides-any
  (let [pred (divides-any 3 5)]
    (for-these [result input] (should (= result (pred input)))
               false 2
               true 3
               false 4
               true 5
               true 6)))

(testing "various approaches to problem 1"
  (dotimes [n 10]
    (should (= (problem-1-recur n)
               (problem-1 n)
               (problem-1-left-to-right n)))))

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

(testing-fn unique-factors
  (10 -> [10 9 8 7 6])
  (6 -> [6 5 4])
  (5 -> [5 4 3]))

(testing-fn problem-5
  (2 -> 2)
  (3 -> 6)
  (4 -> 12)
  (10 -> 2520)
  #_(20 -> 232792560))

(testing-fn problem-6
  (10 -> 2640)
  (100 -> 25164150))

(testing-fn problem-7
  (6 -> 13)
  (10001 -> 104743))

