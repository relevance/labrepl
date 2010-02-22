(ns solutions.automaton-test
  (:use circumspec solutions.automaton))

(testing-fn active-neighbors
  ((repeat (repeat :on)) -> 8)
  ((repeat (repeat :off)) -> 0))

