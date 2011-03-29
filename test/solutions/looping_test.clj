(ns solutions.looping-test
  (:use clojure.test
        solutions.looping))

(deftest test-zipm
  (are [result keys vals] (= result (zipm-1 keys vals))
       {} nil nil
       {:a 1} [:a] [1])
  (are [result keys vals] (= result (zipm-2 keys vals))
       {} nil nil
       {:a 1} [:a] [1])
  (are [result keys vals] (= result (zipm-3 keys vals))
       {} nil nil
       {:a 1} [:a] [1])
  (are [result keys vals] (= result (zipm-4 keys vals))
       {} nil nil
       {:a 1} [:a] [1])
  (are [result keys vals] (= result (zipm-5 keys vals))
       {} nil nil
       {:a 1} [:a] [1]))

(deftest test-min
  (are [result vals] (= result (apply min-1 vals))
       2 [6 2 4]
       2 [6 3 2 4]
       0 [0])
  (are [result vals] (= result (apply min-2 vals))
       2 [6 2 4]
       2 [6 3 2 4]
       0 [0])
  (are [result vals] (= result (apply min-3 vals))
       2 [6 2 4]
       2 [6 3 2 4]
       0 [0])
  (are [result vals] (= result (apply min vals))
       2 [6 2 4]
       2 [6 3 2 4]
       0 [0]))

(deftest test-minmax
  (are [result vals] (= result (apply minmax-1 vals))
       {:min 2 :max 7} [7 2 4]
       {:min 0 :max 0} [0])
  (are [result vals] (= result (apply minmax-2 vals))
       {:min 2 :max 7} [7 2 4]
       {:min 0 :max 0} [0]))
