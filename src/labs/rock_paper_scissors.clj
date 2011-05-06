(ns ^{:prev "defstrict" :prev-url "/labs/defstrict"}
  labs.rock-paper-scissors
  (:use [labrepl.util :only (c showme)]
        [solutions.rock-paper-scissors]))

(defn overview []
  [[:h3 "Overview"]
   [:p "Clojure's protocols and types are a more powerful and
        flexible alternative to interfaces and classes. They solve
        the expression problem, allowing you to extend old things
        that you don't control, and allowing old code to easily
        use new, all with performance consistent with the fastest
        forms of virtual invocation available on the host platform (e.g. Java)."]
   [:p "We won't explore all the power at once. In this lab, you will
        get your feet wet using protocols and types to simulate players
        in the classic rock-paper-scissors game. I got the idea for this
        exercise from an episode of the " [:a {:href "http://www.rubyquiz.com/quiz16.html"} "Ruby Quiz"] "."]])

(defn the-rules []
  [[:h3 "The Rules"]
   [:ol
    [:li "The rules are pretty simple. Rock beats scissors, scissors beats paper,
          and paper beats rock. Write an idiomatic Clojure function " (c dominates) "
          that returns the thing that dominates the argument passed in."
          (showme dominates)]
    [:li "Create a " (c choices) " set that contains the possible choices. Don't Repeat Yourself."
          (showme choices)]
    [:li "Create a " (c winner) " function that takes two players' choices, and returns the winner,
          or " (c nil) " for a tie:" (showme winner)]
    [:li "Create a " (c draw?) " predicate that takes two players' choices and returns true if they are a draw."
          (showme draw?)]
    [:li "Create an " (c iwon?) " predicate that takes two players' choices and returns true if the first player won."
          (showme iwon?)]]])

(defn the-players []
  [[:h3 "The Players"]
   [:ol
    [:li "All the players will conform to a " (c Player) " protocol. It should have two methods: "
     [:ol
      [:li (c choose) " takes a player and returns that player's choice"]
      [:li (c update-strategy) " takes a player, that player's last choice, and the other player's
           last choice, returning the " (c Player) " for the next round:"]]
           (showme Player)]
    [:li "Before we define our players we need to define our idea of random choice"
          (showme random-choice)]
    [:li "Use " (c defrecord) " to define a " (c Random) " player. " (c Random) " always picks at
          random, and never changes strategy based on what the other player is doing."
          (showme "(defrecord Random []
  Player
  (choose [_] (random-choice))
  (update-strategy [this me you] this))")]
    [:li "Create " (c Stubborn) ", who is initialized with a choice and sticks with it come hell or high water:"
          (showme "(defrecord Stubborn [choice]
  Player
  (choose [_] choice)
  (update-strategy [this me you] this))")]
    [:li "Create " (c Mean) ", who is a little more subtle. " (c Mean) "sticks with
          what worked last time, or plays at random following a loss:"
          (showme "(defrecord Mean [last-winner]
  Player
  (choose [_] (if last-winner last-winner (random-choice)))
  (update-strategy [_ me you] (Mean. (when (iwon? me you) me))))")]
    [:li "Now let's play the " (c game) ", with three arguments: two players and a number
          of rounds. " (c game) " reads nicely as a loop with five arguments:"
     [:ol
      [:li "player 1"]
      [:li "player 2"]
      [:li "player 1's current score"]
      [:li "player 2's current score"]
      [:li "the number of rounds remaining"]]
     "The game should return the two player's scores in a map."
     (showme game)]
    [:li "Try some games with the various players. Do the results match your intuition?"]]])

(defn bonus []
  [[:h3 "Bonus"]
   [:ol
    [:li "Create some other strategies."]
    [:li "Compare your solution with some of the "
          [:a {:href "http://www.rubyquiz.com/quiz16.html"}"Ruby Quiz solutions"] ". What are the salient differences?"]
    [:li "How would you parallelize the code to spread games out to all your cores?"]
    [:li "Extend the simulation to support "
          [:a {:href "http://www.samkass.com/theories/RPSSL.html"} "Rock Paper Scissors Spock Lizard"]  "."]]])

(defn instructions []
  (concat (overview)
          (the-rules)
          (the-players)
          (bonus)))
