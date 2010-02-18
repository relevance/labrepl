(ns solutions.automaton-test
  (:use circumspec solutions.automaton))

(testing-fn immediate-neighbors
  ([[1 2 3] [4 5 6] [7 8 9]]
     -> {:above [1 2 3] :left 4 :right 6 :below [7 8 9]}))

(use 'clojure.contrib.pprint)
(pprint (new-board [4 4]))

(use 'clojure.contrib.seq-utils)
(pprint (with-coords (new-board [2 2])))
