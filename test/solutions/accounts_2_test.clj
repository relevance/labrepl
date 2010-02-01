(ns solutions.accounts-2-test
  (:use circumspec solutions.accounts-2))

(testing "total balance is maintined when running in isolation"
  (let [accounts (make-accounts {:initial-balance 100
                                 :count 10})]
    (should (= 1000 (total-balance accounts)) "balance before")
    (dotimes [_ 10] (random-transaction accounts))
    (should (= 1000 (total-balance accounts)) "balance after")))

(testing "total balance is maintained across threads"
  (let [accounts (make-accounts {:initial-balance 100
                                 :count 10})
        trial-results (trial)]
    (should (= 1000 (total-balance (:accounts trial-results))))))