(ns solutions.rock-paper-scissors
  (:use [clojure.contrib.seq-utils :only (rand-elt)]))

(def dominates
  {:rock :paper
   :scissors :rock
   :paper :scissors})

(def choices
  [:rock :paper :scissors])

(defprotocol Player
  (choose [p])
  (update-strategy [p me you result]))

(deftype Random []
  :as this
  Player
  (choose [] (rand-elt choices))
  (update-strategy [me you result] this))

(deftype Stubborn [choice]
  :as this
  Player
  (choose [] choice)
  (update-strategy [me you result] this))

(deftype Mean [last-winner]
  :as this
  Player
  (choose [] (if last-winner last-winner (rand-elt choices)))
  (update-strategy [me you result] (Mean. (when (= result :win) me))))

(defn play
  [p1-choice p2-choice]
  (cond
   (= p1-choice p2-choice) {:p1 :draw :p2 :draw}
   (= (dominates p1-choice) p2-choice) {:p1 :lose :p2 :win}
   (= (dominates p2-choice) p1-choice) {:p1 :win :p2 :lose}
   :default (throw (RuntimeException. (str "Invalid play: " p1-choice ", " p2-choice)))))

(defn game
  [p1 p2 rounds]
  (let [p1 (atom p1)
        p2 (atom p2)]
    (loop [p1-score 0
           p2-score 0
           rounds rounds]
      (if (pos? rounds)
        (let [p1-choice (choose @p1)
              p2-choice (choose @p2)
              result (play p1-choice p2-choice)]
          (swap! p1 update-strategy p1-choice p2-choice (:p1 result))
          (swap! p2 update-strategy p2-choice p1-choice (:p2 result))
          (recur
           (if (= :win (:p1 result)) (inc p1-score) p1-score)
           (if (= :win (:p2 result)) (inc p2-score) p2-score)
           (dec rounds)))
        {:p1 p1-score :p2 p2-score}))))


