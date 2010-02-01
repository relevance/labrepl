(ns solutions.accounts-1-test
  (:use circumspec solutions.accounts-1))

(testing "transfers"
  (let [accounts (make-accounts {:initial-balance 100
                                 :count 2})]
    (transfer {:accounts accounts :from 0 :to 1 :amount 25})
    (should (= 75 (balance accounts 0)))
    (should (= 125 (balance accounts 1)))))

(testing "total balance is maintined when running in isolation"
  (let [accounts (make-accounts {:initial-balance 100
                                 :count 10})]
    (should (= 1000 (total-balance accounts)) "balance before")
    (dotimes [_ 10] (random-transfer accounts))
    (should (= 1000 (total-balance accounts)) "balance after")))

;; statistically this could fail
(testing "total balance is *not* maintained across threads"
  (let [accounts (make-accounts {:initial-balance 100
                                 :count 10})]
    (should (throws?
             AssertionError
             #"Assert failed: \(= expected-balance \(total-balance accounts\)\)"
             (trial)))))