(ns solutions.rock-paper-scissors)

(def dominates
{:rock :paper
 :scissors :rock
 :paper :scissors})

(def choices #{:rock :paper :scissors})

(defn random-choice []
 (nth (vec choices) (rand-int (count choices))))

(defn winner [p1-choice p2-choice]
 (cond
  (= p1-choice p2-choice) nil
  (= (dominates p1-choice) p2-choice) p2-choice
  :else p1-choice))

(defn draw? [me you] (= me you))

(defn iwon? [me you] (= (winner me you) me))

(defprotocol Player
  (choose [p])
  (update-strategy [p me you]))

(deftype Random []
  :as this
  Player
  (choose [] (random-choice))
  (update-strategy [me you] this))

(deftype Stubborn [choice]
  :as this
  Player
  (choose [] choice)
  (update-strategy [me you] this))

(deftype Mean [last-winner]
  :as this
  Player
  (choose [] (if last-winner last-winner (random-choice)))
  (update-strategy [me you] (Mean. (when (iwon? me you) me))))

(defn game
 [p1 p2 rounds]
 (loop [p1 p1
        p2 p2
        p1-score 0
        p2-score 0
        rounds rounds]
   (if (pos? rounds)
     (let [p1-choice (choose p1)
           p2-choice (choose p2)
           result (winner p1-choice p2-choice)]
       (recur
        (update-strategy p1 p1-choice p2-choice)
        (update-strategy p2 p2-choice p1-choice)
        (+ p1-score (if (= result p1-choice) 1 0))
        (+ p2-score (if (= result p2-choice) 1 0))
        (dec rounds)))
     {:p1 p1-score :p2 p2-score})))
