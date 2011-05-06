(ns ^{:next "Names and Places" :next-url "/labs/names-and-places"}
  labs.intro
  (:use [labrepl.util :only (code)]))

(defn overview []
  [[:h3 "Overview"]
   [:p "In this lab you will learn to
        call functions from the Clojure Read-Eval-Print Loop (REPL), and a
        set of tools that you can use from the REPL to explore Clojure. Note
        that some of these tools are not available with a minimal install of
        Clojure. Later in the course you will see how to include all of
        these tools in your own projects."]
   [:p "You need to be running the
        labrepl to perform the steps in this lab."]])

(defn functions []
  [[:h3 "Functions"]
   [:ol
    [:li "Find your labrepl window, which should have a prompt like " (code "user=> ")
         "This prompt tells you that you are currently working in the user
          namespace, which is a scratch namespace for experimenting. From the
          prompt, you can call functions with forms like this:"
          (code  "(funcname arg1 arg2 ...)")
     [:li "Go ahead and try \"hello world.\""
      (code "(println \"hello world\")")]
     [:li "Function names do not have to be words. Try some math functions:"
      (code (+ 1 2)
            (/ 22 7)
            (/ 22.0 7)
            (+ 1 2 3 4 5))]
     [:li "You can define your own functions with " [:code "defn"] ".
           Use the following form to define a function named triple that triples
           its argument."
           (code (defn triple
                   [arg]
                   (* 3 arg)))]
     [:li "Try defining and calling a few other functions of your choosing."]]]])

(defn documentation []
  [[:h3 "Documentation"]
   [:ol
    [:li "To learn about a function, you can examine its docstring. Print the
          docstring for " [:code "println"] " using the form"
          (code (doc println))]
    [:li "If you are not sure of the name of a function, you can search for docs with "
          [:code "find-doc"] ". This can sometimes return a huge amount of
          information! Try " (code (find-doc "append"))]
    [:li [:code "find-doc"] " can also handle regular expressions, which in Clojure
         look like strings prepended by an octothorpe. Use a regular expression to
         find all the " [:code "find-"] " functions." (code (find-doc #"find-\w+"))]
    [:li "There is no documentation like the source code. Use " [:code "source"]
         " to view the soure code of a function." (code (source println))]
    [:li "Notice how the source for " [:code "println"] " includes a string before
          the arguments. This is how you define docstrings. Try it with your own
          functions: "
          (code (defn triple
                  "Triples arg. Don't write redundant docstrings like this in real code."
                  [arg]
                  (* 3 arg)))
          (code (doc triple))]]])

(defn java []
  [[:h3 "Java"]
   [:ol
    [:li "Clojure provides direct, unproxied access to Java. To call a static
          function use a form like " (code "(ClassName/function & args)")
         "(The " [:code "& args"] " is shorthand for zero or more arguments.)
          Try calling" (code (System/getProperty "java.home"))]
    [:li "You can create Java instances with forms like " (code (new ClassName & args))
         " or the terser, more idiomatic " (code (ClassName. & args)) " Try making
          a date. " (code (java.util.Date.))]
    [:li "Once you have an instance you probably will want to call methods on it.
          For now, stuff your date into a global using " [:code "def"] ": "
          (code (def now (java.util.Date.)))]
    [:li "You can call instance methods like so:" (code (.getMonth now)
          (.toGMTString now))]
    [:li "If you can't remember all the date methods off the top of your
          head, you can pass either a class or an instance to " [:code "javadoc"]
          (code (javadoc now)
                (javadoc java.util.Date))]]])

(defn bonus []
  [[:h3 "Bonus"]
   "What do you think the following forms will do?"
   (code (doc doc))
   (code (find-doc "find-doc"))
   (code (source source))
   (code (javadoc javadoc))
   "Try them. Do you understand what you are seeing?"])

(defn instructions []
  (concat (overview)
          (functions)
          (documentation)
          (java)
          (bonus)))