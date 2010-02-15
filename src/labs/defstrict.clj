(ns #^{:skip-wiki true} labs.defstrict
    (:use labs.util)
    (:require [solutions.defstrict :as defstrict]))

(defn overview
  []
  [[:h3 "Overview"]
   [:p "Static typing provides (at least!) three benefits:"
    [:ul
     [:li "Invocation can be optimized, as no reflection against the object is necessary."]
     [:li "Argument types can be verified correct."]
     [:li "Static analysis of programs can identify bugs, perform optimizations, etc."]]]
   [:p "Clojure can provide some of these benefits, in a way consistent with its a la carte, dynamic nature:"
    [:ul
     [:li "Type tags allow the clojure compiler to inject class casts and avoid the overhead of reflective invocation."]
     [:li "Preconditions can verify the type of arguments at runtime. (In fact, they can do much more, testing any function against any combination of the argument list.)"]]]
   [:p "Neither type tags nor preconditions are idiomatic in general Clojure code. In this exercise, we will postulate that we are in a domain where we want to use both of these features on every argument to every function, and use macros to make our domain idiom easier to use."]]
  )

(defn shout
  []
  [[:h3 "Shout!"]
   [:ol
    [:li "Let's create a very simple function that we can use to see type hints and preconditions in action. For our first iteration, create " (c shout-1) " that calls " (c .toUpperCase) " on its argument"
     (cite defstrict/shout-1)]
    [:li "Try passing invalid arguments " (c nil) " and " (c :foo) " to " (c shout-1) ". Make sure you understand the way each fails."]
    [:li "Now, create " (c shout-2) " that tags its argument as a string."
     (cite defstrict/shout-2)]
    [:li "Thanks to the type tag, " (c shout-2) " will no longer make a reflective invocation of " (c .toUpperCase) ". This is faster, and it also changes some failure modes. Try " (c nil) " and " (c :foo) " again."]
    [:li "Now create a " (c shout-3) " that uses both type tags and an " (c instance?) " precondition on its argument."
     (cite defstrict/shout-3)]
    [:li "Test that " (c shout-3) " provides a more informative error message for various bad inputs."]]
   [:p "A la carte is nice until you want the same fixed meal every time. If you wanted to write a ton of functions that all did type tagging and instance checking, the style used in " (c shout-3) " would become tedious. Let's pretend that there is a Clojure feature called " (c defstrict) " that provides a simple syntax like this:"
    (demo defstrict/shout)
    "In a lisp, you don't have to pretend. Let's implement " (c defstrict) "."]])

(defn arg-type-preconditions
  []
)


(defn type-tagged-args
  []
  [[:h3 "type-tagged-args"]
   [:p "To build " (c defstrict) " we will need a bit of code that can convert an argument list likle " (c [String s]) " into the form exepcted by " (c defn) ". We can't use the reader macro syntax " (c "[#^String s]")", because the reader macro will expand before the macro itself. So, we need to expand into the same form the reader macro would emit: " (c (with-meta s {:tag String})) ". It is a little tricky to verify that this works, because metadata is semi-invisible by design: it does not print at the REPL and it does not participate in equality comparisons. So, let's do some experimenting at the REPL."]
   [:ol
    [:li "Enter " (c "'[#^String s #^Integer/TYPE i]") " to see how the metdata does not get printed on the symbols " (c s) " and " (c i) ". "]
    [:li "You can see the metadata by mapping " (c meta) " over the symbols:" (c "(map meta '[#^String s #^Integer/TYPE i])") ". "]
    [:li "Now that we have a way to verify our outputs, let's turn our attention to the inputs. We will need a way to take " (c defstrict) " argument specs two at a time: " (c (partition 2 '[String s Integer/TYPE i])) ". "]
    [:li "Next, we need a function that takes a " (c [Classname var]) " pair, and emits " (c (with-meta var {:tag type})) "."
     (cite defstrict/type-tagged-arg)]
    [:li "Almost done! Write a " (c type-tagged-args) " function that maps " (c type-tagged-arg) " over an arglist taken two at a time, and puts the result into a vector."
     (cite defstrict/type-tagged-args)]]])

(defn defstrict
  []
  )
(defn instructions
  []
  (concat
   (overview)
   (shout)
   (type-tagged-args)
   (arg-type-preconditions)
   (defstrict)))


