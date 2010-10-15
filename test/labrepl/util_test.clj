(ns labrepl.util-test
  (:use clojure.test
        labrepl.util))

(def foo [])

(deftest test-utils
  (testing "format-code"
    (is (= "nil\n" (format-code nil)))
    (is (= "(+ a b)\n" (format-code "(+ a b)")))
    (is (= "(+ c d)\n" (format-code '(+ c d)))))

  (testing "one-liner?"
    (is (true? (one-liner? nil)))
    (is (true? (one-liner? "foo")))
    (is (true? (one-liner? "foo\n")))
    (is (true? (one-liner? "foo\n  ")))
    (is (false? (one-liner? "foo\nbar\n"))))

  (testing "deindent"
    (is (nil? (deindent nil)))
    (is (= "foo" (deindent "foo")))
    (is (= "foo\nbar") (deindent "foo\nbar"))
    (is (= "foo\n  bar") (deindent "  foo\n    bar")))

  (testing "code"
    (is (= [:script
            {:type "syntaxhighlighter", :class "brush: clojure; toolbar: false; gutter: false; light: true;"}
            "<![CDATA[(new ClassName foo)\n]]>"]
             (code (new ClassName foo)))))

  (testing "c"
    (is (= [:code {:class "inline-syntax"} "(new ClassName foo)"]
             (c (new ClassName foo)))))

  (testing "repl*"
    (is (= "(+ 1 2)\n-> 3"
           (repl* (+ 1 2))))
    (is (= "( + 3 4 )\n-> 7"
           (repl* "( + 3 4 )"))))

  (testing "source"
    (is (= [:script
            {:type "syntaxhighlighter", :class "brush: clojure; toolbar: false; gutter: false; light: true;"}
            "<![CDATA[(def foo [])\n]]>"]
             (source foo)))))
