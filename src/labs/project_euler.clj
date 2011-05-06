(ns ^{:next "Mini Browser" :next-url "/labs/mini-browser"
      :prev "Looping" :prev-url "/labs/looping"}
  labs.project-euler
  (:use [labrepl.util :only (c showme repl-showme)]
        [solutions.project-euler]))

(defn overview []
  [[:h3 "Overview"]
   [:p [:a {:href "http://projecteuler.net"} "Project Euler"] " is a set of mathematical
       and computer programming puzzles. You can create an account on the site, read
       the puzzle descriptions, and track the puzzles you have completed correctly.
       The puzzles are excellent for learning how to use laziness, recursion, and the
       sequence library in Clojure."]
   [:p "This lab will walk you through implementing the first Project Euler problem:"]
   [:blockquote
    [:p "If we list all the natural numbers below 10 that are multiples of 3 or 5, we
         get 3, 5, 6 and 9. The sum of these multiples is 23."]
    [:p "Find the sum of all the multiples of 3 or 5 below 1000."]]])

(defn filtering []
  [[:h3 "Filtering"]
   [:p "When decomposing a problem into functions, a good place to start is
        identifying pure functions that will be used to filter the data. These
        functions are easy to define and test."]
   [:ol
    [:li "One way to attack this problem is finding numbers that are evenly divisible
          by 3 or 5. Create a " (c divides?) " predicate that takes a dividend and a
          divisor, and returns true if divisor evenly divides the dividend:"
          (showme divides?)]
    [:li "We will eventually want to filter on divisibility by more than one number,
          so create a " (c divides-any) " function that takes a variable list of
          numbers, and returns a predicate that tests whether its arg can be evenly
          divided by any of the numbers. (Hint: work inside-out, using " (c divides?) ",
          " (c some) ", and " (c boolean)"). " (showme divides-any)]]])

(defn recursion-solution []
  [[:h3 "A Recursive Solution"]
   [:p "With the filtering primitives in place, we can focus on the recursion needed to
        calculate the result. Create a function of one argument, the exclusive "
        (c upper) " bound to sum. Inside this function, you will need to:"]
   [:ol
    [:li (c let) " a local function " (c divisible?) " that tests for divisibility by
         3 or 5."]
    [:li "Loop starting with a " (c sum) " of zero and an " (c n) " of one."]
    [:li "The loop should terminate if " (c n) " is greater than or equal to "
          (c upper) "."]
    [:li "The recur should always increment " (c n) ". If n is " (c divisible?) ", "
          (c sum) " should increment by " (c n) ", otherwise, " (c sum) " should
          remain unchanged."]]
   (showme problem-1-recur)
   [:p "Create an account on " [:a {:href "http://projecteuler.net"} "Project Euler"] "
        and verify your solution."]])

(defn reduction-solution []
  [[:h3 "A Solution by Reduction"]
   [:p "Another approach is to create a sequence of all the values that should
        contribute to the sum, and then simply add them. Let's work this inside out
        at the REPL, using just the numbers up to twenty."]
   [:ol
    [:li "Create the range bounded by 20." (repl-showme (range 20))]
    [:li "Filter the range to only the numbers divisible by 3 and 5."
          (repl-showme (filter
                        (divides-any 3 5)
                        (range 20)))]
    [:li "Add them together:"
          (repl-showme (apply +
                              (filter
                               (divides-any 3 5)
                               (range 20))))]
    [:li "And we're done. Put that all into a function of " (c upper) ":"
          (showme problem-1)]]])

(defn left-to-right-solution []
  [[:h3 "A Left-To-Right Solution"]
   [:p "The reductive solution is simple and elegant, but many people like to read
        code left-to-right, not inside out. The Clojure macros " (c ->) " and "
        (c ->>) " take their first argument, and insert it into the second form.
        The result of the second form is inserted into the third form, and so on. "
        (c ->) " inserts arguments at the first position, and " (c ->>) " inserts at
        the last position. Again this is simple enough to quickly implement at the REPL:"]
   [:ol
    [:li "Grab the range up to 20:" (repl-showme (range 20))]
    [:li "Insert the range into a filter:"
          (repl-showme (->> (range 20) (filter (divides-any 3 5))))]
    [:li "Insert the filtered range into +:"
          (repl-showme (->> (range 20)
                            (filter (divides-any 3 5))
                            (apply +)))]
    [:li "And we're done. Put that all into a function:"
          (showme problem-1-left-to-right)]]])

(defn bonus []
  [[:h3 "Bonus"]
   [:ol
    [:li "Implement the approaches above without using the helper functions "
          (c divides?) " and " (c divides-any?) ". What difference does this make?"]
    [:li "All the solutions above take all the numbers in range, and then exclude
          the ones we don't want. Go the other way and implement a solution that
          builds up the sequence of numbers we " [:em "do"] " want."]
    [:li "Implement Project Euler problems 2-5. The file "
          (c solutions/project_euler.clj) " has some correct solutions."]]])

(defn instructions []
  (concat (overview)
          (filtering)
          (recursion-solution)
          (reduction-solution)
          (left-to-right-solution)
          (bonus)))
