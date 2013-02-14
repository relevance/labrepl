(ns ^{:next "Zero Sum" :next-url "/labs/zero-sum"
      :prev "Mini Browser" :prev-url "/labs/mini-browser"}
  labs.unified-update-model
  (:refer-clojure :exclude [get])
  (:use [labrepl.util :only (c repl-code showme repl-showme code source)]
        [solutions.fight :only (google-search-base
                                url-encode fight
                                estimated-hits-for
                                add-estimate)]
        [solutions.atom-cache])
  (:require
   [clojure.data.json :as json]
   [solutions.ref-cache :as rc]))

(defn overview []
  [[:h3 "Overview"]
   [:p "The Unified Update Model works as follows:"
    [:ul
     [:li "Data is immutable."]
     [:li [:em "State"] " is manged through indirection, via " [:em "references"] "
          to immutable data."]
     [:li "There are a variety of different reference types, each of which provides
           reliable concurrency semantics."]
     [:li "The reference types share a Unified Update Model, which is the atomic
           application of a function to the existing value."]]
    [:p "The Unified Update Model makes Clojure's concurrency easy to use, both in
         concept and at the API level.  In this section you will explore the Unified
         Update Model in the context of different Clojure reference types."]]])

(defn atoms []
  [[:h3 "Atoms"]
   [:ol
    [:li "An atom is an atomic reference to a single piece of data. You can create
          an atom like this:" (repl-code (def counter (atom 0)))]
    [:li "To update an atom, simply call " (c swap!) " with the atom and an update
          function. To increment the "
          (c counter) ":"
          (repl-code (swap! counter inc))]
    [:li "If the function has arguments, simply tack them on the end. Let's update
          the " (c counter) " a little faster:" (repl-code (swap! counter + 1000))]
    [:li "It is really that simple, and it is thread-safe. And the atomic values can
          be composites:"
          (repl-code (def language (atom {:name "Clojure" :age 2})))]
    [:li "The " (c xxx-in) " family of functions is great for working with composites:"
     (repl-code (swap! language update-in [:age] inc))]
    [:li "Let's use atoms to build a simple object cache. First, create a "
          (c create-1) " function that returns an atom wrapped around an empty map."
          (showme create-1)]
    [:li "Next, create a " (c get) " function that returns an item from the cache.
          Note that you will need to use the " (c :refer-clojure) " option to "
          (c ns) " to prevent collision with clojure's own " (c get) "."
          (showme get)]
    [:li "Create a " (c put-1) " function that puts an item into the cache,
          using " (c assoc) "." (showme put-1)]
    [:li "Take your new cache out for a spin at the REPL:"
     (repl-showme (let [c (create-1)]
                    (put-1 c :greeting "Hello, World")
                    (get c :greeting)))]
    [:li "Let's make the cache a little easier for callers. Define a " (c create) "
          function that lets callers specify an initial map, defaulting to an empty map:"
          (showme create)]
    [:li "Next, define a " (c put) " that can take a key/value pair as before, or
          an entire map to be added." (showme put)]
    [:li "Test your improved cache:"
     (repl-showme (let [grades (create {:A 100 :B 90})]
                    (put grades {:C 80 :D 70})
                    grades))]]])

(defn refs []
  [[:h3 "Transactions and Refs"]
   [:p "Refs differ from atoms in that they do not stand alone. Updates can be grouped
        together in atomic transactions. This is " [:em "not the same"] " as grouping
        updates in a lock-based system. Transactions never impede readers, cannot
        deadlock, and do not erode abstraction boundaries."]
   [:ol
    [:li "Use a " (c ref) " to create a counter with an initial value of zero:"
     (repl-showme (def counter (ref 0)))]
    [:li "As with an atom, you can read the value of a ref with " (c "deref/@") ":"
     (repl-showme @counter)]
    [:li "Updates are different, and require a call to " (c alter) ". But " (c alter) "
          does not work outside of a transaction:"
          (repl-code (alter counter inc))]
    [:li "All updates to a ref must be scoped inside a " (c dosync) ". Increment the
          counter:" (repl-showme (dosync (alter counter inc)))]
    [:li "Let's create a cache based on refs. As before, begin with a " (c create) "
          function." (showme rc/create)]
    [:li "Then get:" (showme rc/get)]
    [:li "And put:"  (showme rc/put)]
    [:li "Test the ref-based cache:"
     (repl-showme (let [colors (rc/create {:hulk "green" :aquaman "orange"})]
                    (rc/put colors {:flash "red" :sinestro "yellow"})
                    colors))]
    [:li "Transactions can nest arbitrarily, and multiple calls to " (c dosync) "
          all join the same transaction.  At the REPL, create two different caches,
          and update them in same transaction:"
          (repl-showme (let [[colors powers] (repeatedly rc/create)]
                         (dosync
                          (rc/put colors :hulk "green")
                          (rc/put powers :sinestro "fear-powered space flight"))
                         {:colors colors :powers powers}))]
    [:li "Some operations are commutative (you don't care what order they happen in,
          as long as they all happen).  If you know that an operation is commutative,
          you can use " (c commute) " instead of " (c alter) ".  This is a performance
          optimization, allowing the STM to reduce the impedance between writers
          in some situations. Create a " (c fast-put) " that uses commute instead
          of " (c alter) "." (showme rc/fast-put)]
    [:li "Make sure that " (c fast-put) " works correctly:"
     (repl-showme (let [colors (rc/create {:hulk "green" :aquaman "orange"})]
                    (rc/fast-put colors {:flash "red" :sinestro "yellow"})
                    colors))]
    [:li "The mechanics of " (c commute) " are simple, but the implications require
          some thought. Is " (c commute) " actually appropriate for a cache? For
          a counter?"]]])

(defn futures []
  [[:h3 "Futures"]
   [:p "A future represents work to be done off the current thread. To see futures
        in action, let's create something slow: a program that compares the estimated
        google results for two search terms. You will need to include the following
        namespaces:"
    (code "(require '[clojure.data.json :as json])")
    (code "(use '[solutions.fight])")]
   [:ol
    [:li "The " (c slurp) " function takes a URL or filename string and returns a String of
          response data. Try it a the REPL to see how it works."]
    [:li "To get google search results, you will need the following URL prefix:"
     (source google-search-base)]
    [:li "Since some interesting searches are multiword, you will want
          to " (c url-encode) " them: "
     (repl-code (url-encode "two words"))]
    [:li "The " (c url-encode) " function is a thin wrapper around Java's " (c URLEncoder)
     (showme url-encode)]
    [:li "The search results are returned as JSON. The" (c json/read-str) " function
          converts JSON into Clojure data. Test it at the REPL:"
          (repl-showme (json/read-str "{\"foo\" : [1, 2]}" :key-fn keyword))]
    [:li "Using the functions " (c slurp) ", " (c url-encode) ", and "
          (c json/read-str) ", you can write an " (c estimated-hits-for) " function
          that returns the estimated hits for a search term:"
          (showme estimated-hits-for)]
    [:li "Try calling " (c estimated-hits-for) ". Note the (hopefully brief) latency
          as the request goes out to Google and back."
          (repl-showme (estimated-hits-for "clojure"))]
    [:li "At the REPL, wrap the call to " (c estimated-hits-for) " in a future so
          that control returns immediately and the work proceeds on a background thread:"
          (repl-showme (def clojure-result (future (estimated-hits-for "clojure"))))]
    [:li "Whenver you are ready, you can block waiting for a result by dereferencing
          the future:" (repl-showme @clojure-result)]
    [:li "Write a " (c fight) " function that takes two search terms. It should start
          one future to search for each term, and then a third future that waits on
          the first two:" (showme fight)]
    [:li "Use " (c fight) " to prove your deeply held biases. Thanks, Internet!"]]])

(defn agents []
  [[:h3 "Agents"]
   [:p "Although futures allow " (c deref) ", they do not implement the unified
        update model. There is no ongoing state in a future, just a one-shot
        off-thread result. In this section, you will see agents, which implement
        the unified update model off-thread, allowing multiple background updates to
        a piece of state. We will continue the " (c fight) " example, using an agent
        to track all of the results over time."]
   [:ol
    [:li "You can create an agent by calling " (c agent) " with an initial state. At
          the REPL, define " (c fight-results) " as an agent with an initially empty map:"
          (repl-showme (def fight-results (agent {})))]
    [:li "Since agents use the unified update model, you already know how to use
          them, except for the function name to call (it's " (c send-off) "). We
          will store terms as keys, and their results counts as values in the map. "
          (c send-off) " a function to " (c fight-results) " that will update it
          with the hit count for \"clojure\":"
          (repl-showme
           (send-off fight-results
                     (fn [state]
                       (assoc state "clojure" (estimated-hits-for "clojure")))))]
    [:li "Next, capture the update function in named function " (c add-estimate) ".
          Think carefully about the arguments you will need, and the order in which
          they need to appear:" (showme add-estimate)]
    [:li "Now you are free to call " (c add-estimate) " as many times as you want:"
     (repl-showme (doseq [term ["derek wildstar" "mark venture"]]
                    (send-off fight-results add-estimate term)))]
    [:li "Having sent a bunch of functions to an agent, you will often want to wait
          for them all to be done.  You can await completion of all actions launched
          from the current thread with " (c await) " (no timeout) or "
          (c await-for) ". Use the safer of these two to make sure the work you
          requested is complete:"
          (repl-showme (await-for 1000 fight-results))]
    [:li "Dereference " (c fight-results) " to see what you got:"
     (repl-showme @fight-results)]]])

(defn bonus []
  [[:h3 "Bonus"]
   [:ol
    [:li "In the section on atoms above, what are the legal types for keys in the
          cache? How would this differ in a mutable language like Java?"]
    [:li "Same question, but for the reference cache. What types are legal keys?"]
    [:li "In the fight examples, there is no error handling or recovery. What would
          you do to address this?"]
    [:li "There are actually two functions for updating agents: " (c send) "
          and " (c send-off) ". Read their docstrings. Does the agent-based fight
          use the correct function?"]
    [:li "Which functions in Clojure have names ending with bang (!), and why?"]
    [:li "Why do the various reference types share a read function " (c deref) ",
          but have different update functions?"]]])

(defn instructions []
  (concat (overview)
          (atoms)
          (refs)
          (futures)
          (agents)
          (bonus)))

