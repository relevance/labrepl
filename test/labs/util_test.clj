(ns labs.util-test
  (:use labs.util circumspec))

(testing-fn deindent
  (nil -> nil)
  ("foo" -> "foo")
  ("foo\nbar" -> "foo\nbar")
  ("  foo\n    bar" -> "foo\n  bar"))

(testing-fn format-code
  (nil -> "nil\n")
  ("(+ a b)" -> "(+ a b)\n")
  ('(+ c d) -> "(+ c d)\n"))

(testing "code"
  (should (= [:pre [:code "(new ClassName foo)\n"]]
             (code (new ClassName foo)))))

(testing "c"
  (should (= [:code "(new ClassName foo)"]
             (c (new ClassName foo)))))

(def foo [])
(testing "source"
  (should (= [:pre [:code "(def foo [])"]]
             (source foo))))

(testing "repl*"
  (should (= "(+ 1 2)\n-> 3"
             (repl* (+ 1 2))))
  (should (= "( + 3 4 )\n-> 7"
             (repl* "( + 3 4 )"))))
