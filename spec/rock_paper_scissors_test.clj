(ns solutions.rock-paper-scissors-test
  (use circumspec solutions.rock-paper-scissors)
  (import [solutions.rock-paper-scissors Stubborn Random Mean]))

(testing-fn winner
  (:rock :rock => nil)
  (:rock :paper => :paper)
  (:rock :scissors => :rock))

(testing-fn draw?
  (:rock :rock => true)
  (:rock :paper => false)
  (:rock :scissors => false))

(testing-fn iwon?
  (:rock :rock => false)
  (:rock :paper => false)
  (:rock :scissors => true))

(testing "Stubborn"
  (let [s (Stubborn. :rock)]
    (should (= :rock (choose s)))
    (should (= s (update-strategy s :ignored :ignored)))))

(testing "Random"
  (let [r (Random.)]
    (should (contains? choices (choose r)))
    (should (= r (update-strategy r :ignored :ignored)))))

(describe Mean
  (let [m (Mean. :rock)]
    (it "should replay last winning value"
      (should (= m (update-strategy m :rock :scissors))))
    (it "should reset if it did not win"
      (should (= (Mean. nil) (update-strategy m :rock :paper))))))

(testing "mean beats stubborn (life advice perhaps?)"
  (let [{:keys [p1 p2]} (game (Mean. :rock) (Stubborn. :rock) 100)]
    (should (<= (+ p1 p2 100)))
    (should (< p2 p1))))
