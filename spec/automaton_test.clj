(ns solutions.automaton-test
  (:use circumspec solutions.automaton))

(testing-fn active-neighbors
  ((repeat (repeat :on)) -> 8)
  ((repeat (repeat :off)) -> 0))

(testing-fn step
  ([[:off :off :off :off :off]
    [:off :off :on  :off :on ]
    [:off :off :off :off :off]
    [:off :off :off :off :off]] ->
      [[:off :off :off :on :off]
       [:off :off :dying  :on :dying ]
       [:off :off :off :on :off]
       [:off :off :off :off :off]]))

