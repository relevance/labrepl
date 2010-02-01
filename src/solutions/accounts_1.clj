(ns
    #^{:author "Stu Halloway"
       :doc "BROKEN version of accounts example. total-balance does not read consistently"}
  solutions.accounts-1)

(defn make-accounts
  "Create a map of account-num->:initial-balance for :count numbered
  accounts."
  [{:keys [count initial-balance]}]
  (zipmap (range count) (repeatedly (partial ref initial-balance))))

(defn total-balance
  [accounts]
  "Total balance of all accounts"
  (apply + (map deref (vals accounts))))

(defn random-transaction
  "Perform a random transaction between two accounts in accounts.
   Both accounts might be same account, we don't care."
  [accounts]
  (let [balances (vals accounts)
        no-of-accounts (count balances)
        a1 (nth balances (rand-int no-of-accounts))
        a2 (nth balances (rand-int no-of-accounts))
        amount (rand-int @a1)]
    (dosync
     (alter a1 - amount)
     (alter a2 + amount))))

(defn bunch-o-txes
  "Return n futures doing iterations random transactions
   against accounts, spread equally across n threads."
  [accounts n iterations]
  (take n (repeatedly
           (fn []
             (future
              (dotimes [_ (/ iterations n)]
                (random-transaction accounts)))))))

(defn trial
  "Creates :no-of-accounts accounts with :initial-balance.
   Bang on them from :threads different threads for
   :iterations. All the while, calling thread reads
   total-balance, asserting that it stays correct."
  ([] (trial {:accounts (make-accounts {:count 10 :initial-balance 100})
              :iterations 1000
              :threads 2}))
  ([{:keys [accounts iterations threads]}]
     (let [expected-balance (total-balance accounts)
           futures (bunch-o-txes accounts threads iterations)]
       (loop []
         (if (every? #(.isDone %) futures)
           {:accounts accounts :futures futures}
           (do
             (assert (= expected-balance (total-balance accounts)))
             (recur)))))))
