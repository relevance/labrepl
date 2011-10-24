(ns ^{:next "It's All Data" :next-url "/labs/its-all-data"
      :prev "Intro" :prev-url "/labs/intro"}
  labs.names-and-places
  (:use [labrepl.util :only (c code repl-code)]
        [solutions.dialect :only (canadianize)]
        [clojure.java.io :only (file)])
  (:require [clojure.java.io :as io])
  (:import [java.util Date Random]))

(defn overview []
  [[:h3 "Overview"]
   [:p "In this lab you will learn to manage Clojure namespaces and Java packages.
        In short:"
    [:ul
     [:li "use " [:code "require"] " to load clojure libraries"]
     [:li "use " [:code "refer"] " to refer to functions in the current namespace"]
     [:li "use " [:code "use"] " to load and refer all in one step"]
     [:li "use " [:code "import"] " to refer to Java classes in the current namespace"]]]])

(defn require-section []
  [[:h3 "Require"]
   [:ol
    [:li "You can load the code for any clojure library with " (c "(require libname)")
         ". Try it with " (c clojure.java.io) ":" (code (require 'clojure.java.io))]
    [:li "Then print the directory of names available in the namespace"
          (code (dir clojure.java.io))]
    [:li "Show using " [:code "file"] " to create a new file:"
          (repl-code (clojure.java.io/file "foo"))]
    [:li "Writing out the namespace prefix on every function call is a pain, so you
          can specify a shorter alias using " [:code "as"] ":"
          (code (require '[clojure.java.io :as io]))]
    [:li "Calling the shorter form is much easier:" (repl-code (io/file "foo"))]
    [:li "You can see all the loaded namespaces with" (code (all-ns))]]])

(defn refer-and-use []
  [[:h3 "Refer and Use"]
   [:ol
    [:li "It would be even easier to use a function with no namespace prefix at all. You
          can do this by " [:i "referring"] " to the name, which makes a
          reference to the name in the current namespace:"
          (code (refer 'clojure.java.io))]
    [:li "Now you can call " [:code "file"] " directly:" (repl-code (file "foo"))]
    [:li "If you want to load and refer all in one step, call " [:code "use"] ": "
          (code (use 'clojure.java.io))]
    [:li "Referring a library refers all of its names. This is often undesirable, because"
     [:ul
      [:li "it does not clearly document intent to readers"]
      [:li "it brings in more names than you need, which can lead to name collisions"]]
     "Instead, use the following style to specify only those names you want:"
     (code (use '[clojure.java.io :only (file)]))
     "The " [:code ":only"] " option is available on all the namespace
     management forms. (There is also an " [:code ":exclude"] " which
     works as you might expect.)"]
    [:li "The variable " [:code "*ns*"] " always contains the current namespace, and you
          can see what names your current namespace refers to by calling"
          (code (ns-refers *ns*))]]
   [:li "The refers map is often pretty big. If you are only interested in one symbol,
         pass that symbol to the result of calling " [:code "ns-refers"] ": "
         (code ((ns-refers 'labs.names-and-places) 'file))]])

(defn import-section []
  [[:h3 "Import"]
   [:ol
    [:li "Importing is like referring, but for Java classes instead of Clojure
          namespaces. Instead of " (code (java.io.File. "woozle")) " you can say "
          (code (import java.io.File) (File. "woozle"))]
    [:li "You can import multiple classes in a Java package with the form "
          (code (import [package Class Class])) "For example: "
          (code (import [java.util Date Random])
                (Date. (.nextInt (Random.))))]
    [:li "Programmers new to Lisp are often put off by the \"inside-out\" reading
          of forms like the date creation above. Starting from the inside, you "
     [:ul
      [:li "get a new " [:code "Random"]]
      [:li "get the next random integer"]
      [:li "pass the long to the " [:code "Date"] " constructor"]]
     "You don't have to write inside-out code in Clojure. The " [:code "->"] "
     macro takes its first form, and passes it as the first argument to its next form.
     The result then becomes the first argument of the next form, and so on. It is
     easier to read than to describe:"
     (repl-code (-> (Random.) (.nextInt) (Date.)))]]])

(defn load-and-reload []
  [[:h3 "Load and Reload"]
   [:p "The REPL isn't for everything. For work you plan to keep, you will want to
        place your source code in a separate file. Here are the rules of thumb to
        remember when creating your own Clojure namespaces."
    [:ol
     [:li "Clojure namespaces (a.k.a. libraries) are equivalent to Java packages."]
     [:li "Clojure respects Java naming conventions for directories and files, but
           Lisp naming conventions for namespace names. So a Clojure namespace "
           [:code "com.my-app.utils"] " would live in a path named "
           [:code "com/my_app/utils.clj"] ". Note especially the underscore/hyphen
           distinction."]
     [:li "Clojure files normally begin with a namespace declaration, e.g."
           (code (ns com.my-app.utils))]
     [:li "The syntax for import/use/refer/require presented in the previous sections
           is for REPL use.  Namespace declarations allow similar forms--similar
           enough to aid memory, but also different enough to confuse. The following
           forms at the REPL:"
           (code (use 'foo.bar)
                 (require 'baz.quux)
                 (import '[java.util Date Random]))
          " would look like this in a source code file:"
           (code (ns com.my-app.utils
                   (:use foo.bar)
                   (:require baz.quux)
                   (:import [java.util Date Random])))
          " Symbols become keywords, and quoting is no longer required."]
     [:li "At the time of this writing, the error messages for doing it wrong with
           namespaces are, well, opaque. Be careful."]]]
   [:p "Now let's try creating a source code file. We aren't going to bother with
        explicit compilation for now. Clojure will automatically (and quickly)
        compile source code files on the classpath.  Instead, we can just add Clojure
        (.clj) files to the " [:code "src"] " directory."
    [:ol
     [:li "Create a file named " [:code "student/dialect.clj"] " in the " [:code "src"] "
           directory, with the appropriate namespace declaration:"
           (code (ns student.dialect))]
     [:li "Now, implement a simple " [:code "canadianize"] " function that takes a
           string, and appends "
           [:code ", eh?"]
           (code (defn canadianize
                   [sentence]
                   (str sentence ", eh")))]
     [:li "From your REPL, use the new namespace:" (code (use 'student.dialect))]
     [:li "Now try it out."
           (let [canadianize #(str % ", eh")]
             (repl-code (canadianize "Hello, world.")))]
     [:li "Oops! We need to trim the period off the end of the input. Fortunately, "
           [:code "clojure.string."] " provides " [:code "replace"] ".
           Go back to " [:code "student/dialect.clj"] " and add require in "
           [:code "[clojure.string :as str]"] ": "
           (code (ns student.dialect
                   (:require [clojure.string :as str])))]
     [:li "Now, update " [:code "canadianize"] " to use " [:code "replace"] ": "
           (code (defn canadianize
                   [sentence]
                   (str/replace sentence #"\.$" ", eh?")))]
     [:li "If you simply retry calling " [:code "canadianize"] " from the repl,
           you will not see your new change, because the code was already loaded.
           However, you can use namespace forms with " [:code "reload"] "
           ( or " [:code "reload-all"] ") to reload a namespace (and its dependencies)."
           (code (use :reload 'student.dialect))]
     [:li "Now you should see the new version of " (c canadianize) ": "
           (repl-code (canadianize "Hello, world."))]]]])

(defn bonus []
  [[:h3 "Bonus"]
   [:ol
    [:li "Canadianize was too easy. Implement " [:code "pig-latinize"] "."]
    [:li "Pig latin was too easy. Implement " [:code "germanize"] "."]]])

(defn instructions []
  (concat (overview)
          (require-section)
          (refer-and-use)
          (import-section)
          (load-and-reload)
          (bonus)))