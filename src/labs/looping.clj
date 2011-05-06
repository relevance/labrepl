(ns ^{:next "Project Euler" :next-url "/labs/project-euler"
      :prev "It's All Data" :prev-url "/labs/its-all-data"}
  labs.looping
  (:use [labrepl.util :only (c code repl-code showme)]
        [solutions.looping])
  (:refer-clojure :exclude [min]))

(defn overview []
  [[:h3 "Overview"]
   [:p "When you are getting started with Clojure, you may (briefly!) miss imperative,
        mutable loops. In this lab, you will see how to loop in Clojure, and why you
        won't need to do it very often. You will implement three functions:"
    [:ol
     [:li [:code "min"] " returns the minimum of a variable-length sequence of args."]
     [:li [:code "zipm"] " builds a map by stepwise combination of a sequence of keys
          and a sequence of values."]
     [:li [:code "minmax"] " returns the minimum and maximum of a variable-length
          sequence of args."]]]])

(defn min []
  [[:h3 "min"]
   [:ol
    [:li "Clojure has exactly one primitive control flow form: " (c if) ". Try using
          it to return the minimum of two numbers:"
     (repl-code (if (< 3 4) 3 4))]
    [:li "Clojure also has exactly one primitive control flow construct:" (c loop) ".
          Alone, it would loop forever, so you typically combine it with " (c if) "
          to terminate the recursion. Implement a function "
          (c min-1) " that takes the minimum of a variable-length argument list. It
          should"
     [:ol
      [:li "Begin the loop by binding " (c min) " to the first arg, and " (c more) "
            to a " (c seq) " over the rest of the args."]
      [:li "Grab the next number " (c x) " from the front of " (c more) " in an "
            (c if-let) "form."]
      [:li "If " (c x) " then recur with the smaller of " (c x) " and " (c min) ",
            followed by the " (c next) " of the numbers."]
      [:li "In the else branch of the " (c if-let) ", simply return the result "
            (c min)]]
     (showme min-1)]
    [:li "As you probably guessed from the suffix " (c -1) ", we are going to
          implement " (c min) " again, and make it better. The first improvement
          will use destructuring. Consider the following two forms, which are
          roughly equivalent:"
          (repl-code (let [[x & more] [1 2 3]]
                       {:x x :more more}))
          (repl-code (let [coll [1 2 3]]
                       {:x (first coll) :more (next coll)}))
         "Notice how the destructuring gets rid of the intermediate binding to "
          (c coll) ", which we never really wanted anyway. It also gets rid of the calls
          to " (c first) " and " (c next) ". Create a " (c min-2) "
          that uses destructuring in a similar way to simplify " (c min-1) "."
          (showme min-2)]
    [:li "An alternative to explicit looping is to call " (c reduce) ", which takes a
          function and applies it stepwise, two arguments a time, down a collection, e.g."
          (repl-code (reduce + [1 2 3]))
         "Implement " (c min-3) " which reduces over a function that returns the min of
          two arguments:"
          (showme min-3)
         "Test that " (c min-3) " works as expected:"
          (repl-code (min-3 5 2 6 1 8))]]])

(defn zipm []
  [[:h3 "zipm"]
   [:ol
    [:li "The previous example, " (c min) ", looped over a collection to produce a
          single value. When your inputs and outputs are both collections, there are
          more implementation choices to consider. We will see this with " (c zipm) ",
          which takes sequences of keys and values to make a map:"
          (let [zipm zipmap]
            (repl-code (zipm [:a :b :c] [1 2 3])))]
    [:li "One option is to loop over three variables: the result map " (c m) "
          (which gets bigger), and the input sequences " (c ks) " and " (c vs) "
          (which get smaller). Create a " (c zipm-1) " that uses a loop and "
          (c assoc) " to build the result map." (showme zipm-1)]
    [:li "As with the " (c min) " example, the " (c zipm) " loop can be improved with
          destructuring. Create a " (c zipm-2) " that destructures into the key and value
          collections, avoiding all the calls to " (c first) " and " (c next) "."
          (showme zipm-2)]
    [:li "Create a " (c zipm-3) " that " (c reduce) "s over a function " (c assoc) "ing
          in a key/value pair."
          (showme zipm-3)]
    [:li "The " (c reduce) " version is simple already, but we can make it simpler by
          simply calling a collection constructor such as " (c hash-map) ":"
          (repl-code (hash-map :a 1 :b 2 :c 3))
         "To call " (c hash-map) " in this way we just need a function to interleave
          the key and value collections:"
          (repl-code (interleave [:a :b :c] [1 2 3]))
         "Create a " (c zipm-4) " using " (c hash-map) " and " (c interleave) "."
          (showme zipm-4)]
    [:li "A very useful constructor is " (c into) ", which \"pours\" one collection
          into another. If you had a sequence of key/value pairs, you could pour them "
          (c into) " a map. Here is one way to cconvert the two sequences into a
          sequence of pairs:"
          (repl-code (map vector [:a :b :c] [1 2 3]))
         "Create a " (c zipm-5) " that pours the key/values " (c into) " a map:"
          (showme zipm-5)]
    [:li "The best approach of all is to notice that what you are doing is common
          enough to be built into Clojure, as in this case with " (c zipmap) "."
          (repl-code (zipmap [:a :b :c] [1 2 3]))]]])

(defn minmax []
  [[:h3 "minmax"]
   [:p "In both the previous examples, the " (c reduce) " version looks simpler to
        my eye than the looping versions. But " (c reduce) " isn't always better.
        In this step, we will implement " (c minmax) ", which returns a map
        containing both the min and max from a collection."]
   [:ol
    [:li "Create a " (c minmax-1) " using a loop. This will look almost like "
          (c min-2) " from the earlier step, except that it will loop over three
          variables instead of two:"
     [:ol
      [:li (c min) " holds the current min"]
      [:li (c max) " holds the current max"]
      [:li (c more) " hold the remainder of the collection"]]
     "The return value should put both " (c min) " and " (c max) "into a map"
     (showme minmax-1)]
    [:li "For comparison, implement " (c minmax-2) " using reduce. Think carefully
          about the reduction function. It needs to hold two values at each iteration:
          one each for " (c min) " and " (c max) "."
          (showme minmax-2)]
    [:p "For functions that are updating more than one piece of information, I find the
         looping version is often more readable. But macros such as " (c ->) " make it
         simple to write multiple updates, so the " (c reduce) " isn't bad
         either. YMMV."]]])

(defn bonus []
  [[:h3 "Bonus"]
   [:ol
    [:li "Read the implementations of " (c min) " and " (c zipmap) " in Clojure. Why
          are they different from any of the examples above?"]
    [:li "Some collection builders start with an empty collection, while others can
          take an argument passed in. Review the various " (c zipmap) " approaches.
          Which ones could be written to take an existing collection and add to it?"]
    [:li "Implement " (c min) " and " (c zipmap) " in an imperative style using a
          mutable OO language. Compare and contrast with the Clojure implementations
          above."]
    [:li "All the Clojure functions above are threadsafe. Make your mutable "
          (c zipmap) " threadsafe."]]])

(defn instructions []
  (concat (overview)
          (min)
          (zipm)
          (minmax)
          (bonus)))

