(ns labs.unified-update-model
  (:use labrepl.util)
  (:require [solutions.atom-cache :as ac]
            [solutions.ref-cache :as rc]))

(defn overview
  []
  [[:h3 "Overview"]
   [:p "The Unified Update Model works as follows:"
    [:ul
     [:li "Data is immutable."]
     [:li [:em "State"] " is manged through indirection, via " [:em "references"] " to immutable data."]
     [:li "There are a variety of different reference types, each of which provide reliable concurrency semantics."]
     [:li "The reference types share a Unified Update Model, which is the atomic application of a function to the existing value."]]
    [:p "The unified update model makes clojure concurrency easy to use, both in concept and at the API level. In this section you will explore the Unified Update Model in the context of different Clojure reference types"]]
   ])

(defn atoms
  []
  [[:h3 "Atoms"]
   [:ol
    [:li "An atom is an atomic reference to a single piece of data. You can create an atom like this:"
     (repl-code (def counter (atom 0)))]
    [:li "To update an atom, simply call " (c swap!) " with the atom and an update function. To increment the " (c counter) ":"
     (repl-code (swap! counter inc))]
    [:li "If the function as arguments, simply tack them on the end. Let's update the " (c counter) " a little faster:"
     (repl-code (swap! counter + 1000))]
    [:li "It is really that simple, and it is thread-safe. And the atomic values can be composites:"
     (repl-code (def language (atom {:name "Clojure" :age 2})))]
    [:li "The " (c xxx-in) " family of functions are great for working with composites:"
     (repl-code (swap! language update-in [:age] inc))]
    [:li "Let's use atoms to build a simple object cache. First, create a " (c create-1) " function that returns an atom wrapped around an empty map."
     (showme ac/create-1)]
    [:li "Next, create a " (c get) " function that returns an item from the cache. Note that you will need to use the " (c :refer-clojure) " option to " (c ns) " to prevent collision with clojure's own " (c get) "."
     (showme ac/get)]
    [:li "Create a " (c put-1) " function that puts an item into the cache, using " (c assoc) "."
     (showme ac/put-1)]
    [:li "Take your new cache out for a spin at the REPL:"
     (repl-showme (let [c (ac/create)]
                    (ac/put-1 c :greeting "Hello, World")
                    (ac/get c :greeting)))]
    [:li "Let's make the cache a little easier for callers. Define a " (c create) " function that lets callers specify an initial map, defaulting to an empty map:"
     (showme ac/create)]
    [:li "Next, define a " (c put) " that can take a key/value pair as before, or an entire map to be added."
     (showme ac/put)]
    [:li "Test your improved cache:"
     (repl-showme (let [grades (ac/create {:A 100 :B 90})]
                    (ac/put grades {:C 80 :D 70})
                    grades))]]])

(defn refs
  []
  [[:h3 "Transactions and Refs"]
   [:p "Refs differ from atoms in that they do not stand alone. Updates can be grouped together in atomic transactions. This is " [:em "not the same"] " as grouping updates in a lock-based system. Transactions never impede readers, cannot deadlock, and do not erode abstraction boundaries."]
   [:ol
    [:li "Use a " (c ref) " to create a counter with an initial value of zero:"
     (repl-showme (def counter (ref 0)))]
    [:li "As with an atom, you can read the value of a ref with " (c "deref/@") ":"
     (repl-showme @counter)]
    [:li "Updates are different, and require a call to " (c alter) ". But " (c alter) " does not work outside of a transaction:"
     (repl-code (alter counter inc))]
    [:li "All updates to a ref must be scoped inside a " (c dosync) ". Increment the counter:"
     (repl-showme (dosync (alter counter inc)))]
    [:li "Let's create a cache based on refs. As before, begin with a " (c create) " function."
     (showme rc/create)]
    [:li "Then get:"
     (showme rc/get)]
    [:li "And put:"
     (showme rc/put)]
    [:li "Test the ref-based cache:"
     (repl-showme (let [colors (rc/create {:hulk "green" :aquaman "orange"})]
                    (rc/put colors {:flash "red" :sinestro "yellow"})
                    colors))]
    [:li "Transactions can nest arbitrarily, and multiple calls to " (c dosync) " all join the same transaction. At the REPL, create two different caches, and update them in same transaction:"
     (repl-showme (let [[colors powers] (repeatedly rc/create)]
                    (dosync
                     (rc/put colors :hulk "green")
                     (rc/put powers :sinestro "fear-powered space flight"))
                    {:colors colors :powers powers}))]
    [:li "Some operations are commutative (you don't care what order they happen in, as long as they all happen). If you know that an operation is commutative, you can use " (c commute) " instead of " (c alter) ". This is a performance optimization, allowing the the STM to reduce the impedance between writers in some situations. Create a " (c fast-put) " that uses commute instead of " (c alter) "."
     (showme rc/fast-put)]
    [:li "Make sure that " (c fast-put) " works correctly:"
     ]
    [:li "The mechanics of " (c commute) " are simple, but the implication require some thought. Is " (c commute) " actually appropriate for a cache? For a counter?"]]])

(defn bonus
  []
  [[:h3 "Bonus"]
   [:ol
    [:li "What are the legal types for keys in the cache? How would this differ in a mutable language like Java?"]]])

(defn instructions
  []
  (concat
   (overview)
   (atoms)
   (refs)
   (bonus)))

