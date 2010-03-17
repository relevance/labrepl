(ns #^{:skip-wiki true} labs.defstrict
    (:use labrepl.util solutions.defstrict))

(defn overview-ins
  []
  [[:h3 "Overview"]
   [:p "Clojure macros make it easy to extend Clojure itself. Indeed, many of Clojure's own core features are implemented as macros. In this exercise, you will use macros to add a special " (c defn) " variant named " (c defstrict) " that unifies type tagging and preconditions."]])

(defn the-problem-ins
  []
  [[:h3 "Introducing defstrict"]
   [:p "Static typing provides (at least!) three benefits:"
    [:ul
     [:li "Invocation can be optimized, as no reflection against the object is necessary."]
     [:li "Argument types can be verified correct."]
     [:li "Static analysis of programs can identify bugs, perform optimizations, etc."]]]
   [:p "Clojure can provide some of these benefits, in a way consistent with its a la carte, dynamic nature:"
    [:ul
     [:li "Type tags allow the clojure compiler to inject class casts and avoid the overhead of reflective invocation."]
     [:li "Preconditions can verify the type of arguments at runtime. (In fact, they can do much more, testing any function against any combination of the argument list.)"]]]
   [:p "Functions can use type tags and preconditions in any combination, and many Clojure programs use neither. In this exercise, we will imagine an unusual project that wants both of these features on every argument to every function. We will define a " (c defstrict) " macro to capture this idiom."]]
  )

(defn shout-ins
  []
  [[:h3 "Shout!"]
   [:ol
    [:li "Let's create a very simple function that we can use to see type hints and preconditions in action. For our first iteration, create " (c shout-1) " that calls " (c .toUpperCase) " on its argument"
     (showme shout-1)]
    [:li "Try passing invalid arguments " (c nil) " and " (c :foo) " to " (c shout-1) ". Make sure you understand the way each fails."]
    [:li "Now, create " (c shout-2) " that tags its argument as a string."
     (showme shout-2)]
    [:li "Thanks to the type tag, " (c shout-2) " will no longer make a reflective invocation of " (c .toUpperCase) ". This is faster, and it also changes some failure modes. Try " (c nil) " and " (c :foo) " again."]
    [:li "Now create a " (c shout-3) " that uses both type tags and an " (c instance?) " precondition on its argument."
     (showme shout-3)]
    [:li "Test that " (c shout-3) " provides a more informative error message for various bad inputs."]]
   [:p "A la carte is nice until you want the same fixed meal every time. If you wanted to write a ton of functions that all did type tagging and instance checking, the style used in " (c shout-3) " would become tedious. Let's pretend that there is a Clojure feature called " (c defstrict) " that provides a simple syntax like this:"
    (source shout)
    "In a lisp, you don't have to pretend. Let's implement " (c defstrict) "."]])

(defn arg-type-preconditions-ins
  []
  [[:h3 "arg-type-preconditions"]
   [:p "To build " (c defstrict) " we will start with a helper function " (c arg-type-preconditions) " that converts " (c defstrict) " argument specifiers into precondition forms. For example:"
    (repl-code (arg-type-preconditions '[String s Integer i])) "We'll build it inside-out from the REPL."]
   [:ol
    [:li "First, use " (c partition) " to take the argument list two elements at a time"
     (repl-showme (partition 2 '[String s Integer i]))]
    [:li "Next, map the pairs over a function that returns the source code for the instance check:"
     (repl-showme
"(map
 (fn [[type name]]
   `(instance? ~type ~name))
 (partition 2 '[String s Integer i]))")
     ]
    [:li "Now, all that's left to do is wrap the result in a vector, and put the vector in a map keyed by " (c :pre) "."
     (showme arg-type-preconditions)]]])

(defn type-tagged-args-ins
  []
  [[:h3 "type-tagged-args"]
   [:p "To build " (c defstrict) " we will also need a bit of code that can convert an argument list like " (c [String s]) " into the form exepcted by " (c defn) ". We can't use the reader macro syntax " (c "[#^String s]")", because the reader macro will expand before the macro itself. So, we need to expand into the same form the reader macro would emit: " (c (with-meta s {:tag String})) ". It is a little tricky to verify that this works, because metadata is semi-invisible by design: it does not print at the REPL and it does not participate in equality comparisons. So, let's do some experimenting at the REPL."]
   [:ol
    [:li "Enter " (c "'[#^String s #^Integer i]") " to see how the metdata does not get printed on the symbols " (c s) " and " (c i) ":"
     (repl-showme "'[#^String s #^Integer i]")]
    [:li "You can access the metadata by mapping " (c meta) " over the symbols:"
     (repl-showme "(map meta '[#^String s #^Integer i])")]
    [:li "Now that we have a way to verify our outputs, let's turn our attention to the inputs. We will need a way to take " (c defstrict) " argument specs two at a time: "
     (repl-showme (partition 2 '[String s Integer i]))]
    [:li "Next, we need a function that takes a " (c [Classname var]) " pair, and emits " (c (with-meta var {:tag type})) "."
     (showme type-tagged-arg)]
    [:li "Almost done! Write a " (c type-tagged-args) " function that maps " (c type-tagged-arg) " over an arglist taken two at a time, and puts the result into a vector."
     (showme type-tagged-args)]
    [:li "Use the REPL to verify that the variable names come back correctly:"
     (repl-showme "(type-tagged-args '[String s Integer i])")]
    [:li "And check the metadata:"
     (repl-showme (map meta (type-tagged-args '[String s Integer i])))]]])

(defn defstrict-ins
  []
  [[:h3 "defstrict"]
   [:ol
    [:li "With the building blocks in place, " (c defstrict) " itself is simple. It takes a name, an arglist, and a variable number of body forms, and then it emits:"
     [:ol
      [:li "a " (c defn)]
      [:li "the name"]
      [:li "the (type-tagged) argument list, using " (c type-tagged-args)]
      [:li "the preconditions, using " (c arg-type-preconditions)]
      [:li "the body forms"]]
     (showme defstrict)]
    [:li "Test " (c defstrict) " using " (c macroexpand-1) "."]
    [:li "Test " (c defstrict) " by defining a strict function and calling it."
     (showme shout)]]])

(defn bonus
  []
  [[:h3 "Bonus"]
   [:ol
    [:li "What happens if you pass a function call instead of a class literal to " (c defstrict) "? How would you improve upon this?"]
    [:li "Is multiple evaluation a possible problem for any of the arguments to " (c defstrict) "? Why or why not?"]]])

(defn instructions
  []
  (concat
   (overview-ins)
   (the-problem-ins)
   (shout-ins)
   (arg-type-preconditions-ins)
   (type-tagged-args-ins)
   (defstrict-ins)
   (bonus)))


