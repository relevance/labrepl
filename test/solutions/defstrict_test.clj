(ns solutions.defstrict-test
  (:use clojure.test
        solutions.defstrict))

(deftest test-shouts
  (testing "shout-1"
    (is (= "FOO" (shout-1 "foo")))
    (is (thrown? NullPointerException (shout-1 nil)))
    (is (thrown? IllegalArgumentException (shout-1 :foo))))
  (testing "shout-2 casts to String for performance"
    (is (= "FOO" (shout-2 "foo")))
    (is (thrown? NullPointerException (shout-2 nil)))
    (is (thrown? ClassCastException (shout-2 :foo))))
  (testing "shout-3 uses precondition to guarantee string input"
    (is (= "FOO" (shout-3 "foo")))
    (is (thrown? Throwable #"Assert failed" (shout-3 nil)))
    (is (thrown? Throwable #"Assert failed" (shout-3 :foo))))
  (testing "shout-4 works like shout-3"
    (is (= "FOO" (shout-4 "foo")))
    (is (thrown? Throwable #"Assert failed" (shout-4 :bar)))
    (is (thrown? Throwable #"Assert failed" (shout-4 :foo)))))

(deftest test-instance-check
  (is (nil? (instance-check 'foo)))
  (is (= '(clojure.core/instance? String foo) (instance-check '^String foo))))

(deftest test-arg-type-preconditions
  (is (= '{:pre
               [(clojure.core/instance? String a)
                (clojure.core/instance? Integer/TYPE b)]}
           (arg-type-preconditions '[^String a ^Integer/TYPE b]))))

(defn argument-metadata
  "Return the argument metadata for a var that points
   to a function. List of lists for each arity."
  [var]
  (map (partial map meta) (:arglists (meta var))))

(deftest test-defstrict
  (is (= '(clojure.core/defn shout
                [s]
                {:pre [(clojure.core/instance? String s)]}
                (.toUpperCase s))
             (macroexpand-1
              '(solutions.defstrict/defstrict shout [^String s]
                 (.toUpperCase s)))))
  (is (= '(({:tag String}))
             (argument-metadata #'shout))))
