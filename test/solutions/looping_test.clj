(ns solutions.looping-test
  (:use circumspec solutions.looping))

(testing "zipm"
  (for-these [result keys vals]
    (should (= result
               (zipm-1 keys vals)
               (zipm-2 keys vals)
               (zipm-3 keys vals)
               (zipm-4 keys vals)
               (zipm-5 keys vals)
               (zipmap keys vals)))
    {} nil nil
    {:a 1} [:a] [1]
    {:a 2} [:a :a] [1 2]))

(testing "min"
  (for-these [result vals]
    (should (= result
               (apply min-1 vals)
               (apply min-2 vals)
               (apply min-3 vals)
               (apply min vals)))
    2 [6 2 4]
    2 [6 3 2 4]
    0 [0]))

(testing "minmax"
  (for-these [result vals]
    (should (= result
               (apply minmax-1 vals)
               (apply minmax-2 vals)))
    {:min 2 :max 7} [7 2 4]
    {:min 0 :max 0} [0]))
