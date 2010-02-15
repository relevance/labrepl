(ns solutions.defstrict-test
  (:use circumspec solutions.defstrict))

(describe "shouts"
  (testing shout-1
    (should (= "FOO" (shout-1 "foo")))
    (should (throws? NullPointerException (shout-1 nil)))
    (should (throws? IllegalArgumentException (shout-1 :foo))))
  (testing "shout-2 casts to String for performance"
    (should (= "FOO" (shout-2 "foo")))
    (should (throws? NullPointerException (shout-2 nil)))
    (should (throws? ClassCastException (shout-2 :foo))))
  (testing "shout-3 uses precondition to guarantee string input"
    (should (= "FOO" (shout-3 "foo")))
    (should (throws? AssertionError (shout-3 nil)))
    (should (throws? AssertionError (shout-3 :foo))))
  (testing "shout-4 works like shout-3"
    (should (= "FOO" (shout-4 "foo")))
    (should (throws? AssertionError (shout-4 :bar)))
    (should (throws? AssertionError (shout-4 :foo)))))

(testing type-tagged-args
  (let [tagged (type-tagged-args '[String a Integer/TYPE b])]
    (should (= '[a b] tagged))
    (should (= '[{:tag String} {:tag Integer/TYPE}] (map meta tagged)))))

(testing arg-type-preconditions
  (should (= '{:pre
               [(clojure.core/instance? String a)
                (clojure.core/instance? Integer/TYPE b)]}
           (arg-type-preconditions '[String a Integer/TYPE b]))))

(defn argument-metadata
  "Return the argument metadata for a var that points
   to a function. List of lists for each arity."
  [var]
  (map (partial map meta) (:arglists (meta var))))

(testing "defstrict"
  (should (= '(clojure.core/defn shout
                [s]
                {:pre [(clojure.core/instance? String s)]}
                (.toUpperCase s))
             (macroexpand-1
              '(solutions.defstrict/defstrict shout [String s]
                 (.toUpperCase s)))))
  (should (= '(({:tag String}))
             (argument-metadata #'shout))))
