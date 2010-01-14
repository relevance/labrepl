(ns labs.names-and-places
  (:use labs.util))

(defn overview
  []
  [[:h3 "Overview"]
   [:p "In this lab you will learn to manage Clojure namespaces and Java packages. In short:"
    [:ul
     [:li "use " [:code "require"] " to load clojure libraries"]
     [:li "use " [:code "refer"] " to refer to functions in the current namespace"]
     [:li "use " [:code "use"] " to load and refer all in one step"]
     [:li "use " [:code "import"] " to refer to Java classes in the current namespace"]]]])

(defn require-section
  []
  [[:h3 "Require"]
   [:ol
    [:li "You can load the code for any clojure library with "
     [:code "(require libname)"]
     ". Try it with clojure.contrib.math"
     (code (require 'clojure.contrib.math))]
    [:li "Then print the directory of names available in the namespace"
     (code (dir clojure.contrib.math))]
    [:li "Show using " [:code "lcm"] " to calculate the least common multiple:"
     (code (clojure.contrib.math/lcm 11 41))]
    [:li "Writing out the namespace prefix on every function call is a pain, so you can specify a shorter alias using " [:code "as"] ":"
     (code (require '[clojure.contrib.math :as m]))]
    [:li "Calling the shorter form is much easier:"
     (code (m/lcm 120 1000))]
    [:li "You can see all the loaded namespaces with"
     (code (all-ns))]]])

(defn refer-and-use
  []
  [[:h3 "Refer and Use"]
   [:ol
    [:li "It would be even easier to use a function with no namespace prefix at all. You can do this by " [:i "referring"] " to the name, which makes a reference to the name in the current namespace:"
     (code (refer 'clojure.contrib.math))]
    [:li "Now you can call " [:code "lcm"] " directly:"
     (code (lcm 16 30))]
    [:li "If you want to load and refer all in one step, call " [:code "use"] ": "
     (code (use 'clojure.contrib.math))]
    [:li "Referring a library refers all of its names. This is often undesirable, because"
     [:ul
      [:li "it does not clearly document intent to readers"]
      [:li "it brings in more names than you need, which can lead to name collisions"]]
     "Instead, use the following style to specify only those names you want:"
     (code (use '[clojure.contrib :only (lcm)]))
     "The " [:code ":only"] " option is available on all the namespace management forms. (There is also an " [:code ":exclude"] " which works as you might expect.)"]
    [:li "The variable " [:code "*ns*"] " always contains the current namespace, and you can see what names your current namespace refers to by calling"
     (code (ns-refers *ns*))]
    [:li "The refers map is often pretty big. If you are only interested in one symbol, pass that symbol to the result of calling " [:code "ns-refers"] ": "
     (code ((ns-refers *ns*) 'dir))]]])

(defn import-section
  []
  [[:h3 "Import"]
   [:ol
    [:li "Importing is like referring, but for Java classes instead of Clojure namespaces. Instead of "
     (code (java.io.File. "woozle"))
     " you can say "
     (code (import java.io.File)
           (File. "woozle"))]
    [:li "You can import multiple classes in a Java package with the form "
     (code (import [package Class Class]))
     "For example: "
     (code (import '[java.util Date Random]) (Date. (long (.nextInt (Random.)))))]
    [:li "Programmers new to Lisp are often put off by the \"inside-out\" reading of forms like the one above. Starting from the inside, you "
     [:ul
      [:li "get a new " [:code "Random"]]
      [:li "get the next random integer"]
      [:li "cast it to a long"]
      [:li "pass the long to the " [:code "Date"] " constructor"]]
     "You don't have to write inside-out code in Clojure. The " [:code "->"] " macro takes its first form, and passes it as the first argument to its next form. The result then becomes the first argument of the next form, and so on. It is easier to read than to describe:"
     (code (-> (Random.) (.nextInt) (long) (Date.)))]]])

(defn instructions
  []
  (concat (overview)
          (require-section)
          (refer-and-use)
          (import-section)))

