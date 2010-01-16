(ns labs.its-all-data
  (:use labs.util))

(defn some-data-types
  []
  [[:h3 "Some Data Types"]
   [:ol
    [:li "Let's start at the very beginning, with " (c nil) ". " (c nil) " evaluates to false in a boolean context, as you can see by calling"
     (code (if nil "nil is true" "nil is false"))]
    [:li "You can probably guess how " (c true) " and " (c false) " will behave in a boolean context:"
     (code (if true "logical true" "logical false"))
     (code (if false "logical true" "logical false"))]
    [:li "Integer literals come in decimal, octal and hex varieties:"
     (code "10")
     (code "010")
     (code "0x10")]
    [:li "Because math operators come first in an expression, you can pass as many arguments as you want"
     (code (+ 1 2 3))
     (code (+ 10 10 10))]
    [:li "Clojure has a ratio type, so division may surprise you:"
     (code (/ 22 7))]
    [:li "If you want a decimal result, pass a float literal:"
     (code (/ 22.0 7))]
    [:li "For integer division and remainder, use " (c quot) " and " (c rem) ": "
     (code (quot 22 7))
     (code (rem 22 7))]
    [:li "Clojure integer literals are not limited to the range of Java's primtive types. They can be any size, and Clojure will choose the correct underlying representation."
     (code (class (* 1000 1000)))
     (code (class (* 1000 1000 1000 1000)))
     (code (class (* 1000 1000 1000 1000 1000 1000 1000 1000)))
     "There are various ways to use Java primitive types directly; these techniques will be covered elsewhere."]
    [:li "Numeric literals suffixed with " (c M) " are " (c BigDecimal) "s, and provide the same guarantees for correctness as the underlying platform:"
     (code "(+ 100000M 0.00000000001M)")]
    [:li "Strings are simply Java Strings. They are immutable, and have access to all the underlying Java methods:"
     (code "hello")
     (code (.toUpperCase "hello"))]]])

(defn name-types
  []
  [[:h3 "Types that Name Things"]
   [:ol
    [:li "Symbols name things in code-space, e.g. functions and Java classes. You can call  a function through a symbol such as " (c println) ": "
     (code (println "hello"))]
    [:li "Rather than calling a function through a symbol, you might choose just to retrieve the function itself:"
     (code println)]
    [:li "The literal representation of a function at the REPL is just a mangled name. Instead of just looking at the function, you might create another name pointing to the same function:"
     (code (def my-println println))]
    [:li "Now you can call " (c my-println) " just like you would " (c println) ": "
     (code (my-println "hello"))]
    [:li "Sometimes you want to refer to the symbol itself, without retrieving whatever it refers to. To do this, you can " [:i "quote"] " the symbol:"
     (code (quote println))]
    [:li "Quoting is so common that that there is a sugared form: simply put a single quote in front of any symbol to prevent that form from being evaluated:"
     (code 'foo)]
    [:li "In fact, any Clojure form can be quoted. Look at the difference between"
     (code '(+ 1 2 3))
     " and "
     (code (+ 1 2 3))]
    [:li "Keywords name things in domain-space, e.g. the attributes of domain entities. Because they are not used to name code entities, they simply evaluate to themselves and do not have to be quoted. Keywords are preceded by a colon:"
     (code :first-name)
     (code :last-name)]
    [:li "Keywords are often used to cretae map literals:"
     (code (def me {:first-name "Stu" :last-name "Halloway"}))]
    [:li "You can extract values from a map with " (c get) ": "
     (code (get me :first-name))]
    [:li "Maps are themselves functions, taking a key argument, so you could also write:"
     (code (me :first-name))]
    [:li "Keywords are also functions, taking a map argument, so yet another approach is:"
     (code  (:first-name me))]
    [:li "Keywords are also used to name options. So, you can use only the " (c primes) " function from the lazy-seqs lib:"
     (code (use '[clojure.contrib.lazy-seqs :only (primes)]))]
    [:li "Now that we have the primes loaded, we might as well look at a few of them. We will cover " (c for) " in detail later, but for now use the " (c :while) " keywoard option to retrieve only those primes less than a thousand:"
     (code (for [p primes :while (< p 1000)] p))]]])

(defn instructions
  []
  (concat (some-data-types)
          (name-types)))
