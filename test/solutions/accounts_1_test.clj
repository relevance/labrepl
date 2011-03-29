(ns solutions.accounts-1-test
  (:use clojure.test
        solutions.accounts-1))

(deftest test-transfers
  (let [accounts (make-accounts {:initial-balance 100
                                 :count 2})]
    (transfer {:accounts accounts :from 0 :to 1 :amount 25})
    (is (= 75 (balance accounts 0)))
    (is (= 125 (balance accounts 1)))))

(deftest test-balance
  (testing "total balance is maintined when running in isolation"
    (let [accounts (make-accounts {:initial-balance 100
                                   :count 10})]
      (is (= 1000 (total-balance accounts)) "balance before")
      (dotimes [_ 10] (random-transfer accounts))
      (is (= 1000 (total-balance accounts)) "balance after")))
  (testing "total balance is *not* maintained across threads"
    (let [accounts (make-accounts {:initial-balance 100
                                   :count 10})]
      (is (thrown?
           Throwable
           #"Assert failed: \(= expected-balance \(total-balance accounts\)\)"
           (trial))))))
