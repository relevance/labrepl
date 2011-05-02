(ns ^{:author "Stu Halloway"
      :doc "BROKEN version of accounts example. total-balance does not read consistently"}
  solutions.accounts-1)

(defn make-accounts
  "Create a map of account-num->:initial-balance for :count numbered
  accounts."
  [{:keys [count initial-balance]}]
  (zipmap (range count) (repeatedly (partial ref initial-balance))))

(defn total-balance
  "Total balance of all accounts"
  [accounts]
  (apply + (map deref (vals accounts))))

(defn transfer
  [{:keys [accounts from to amount]}]
  (dosync
   (alter (accounts from) - amount)
   (alter (accounts to) + amount)))

(defn balance
  [accounts account-id]
  @(accounts account-id))

(defn random-account-ids
  "Return a lazy seq of random account ids from accounts"
  [accounts]
  (let [ids (keys accounts)]
    (repeatedly (fn [] (rand-nth ids)))))

(defn random-transfer
  "Perform a random tranfer between two accounts in accounts.
   Both accounts might be same account, we don't care."
  [accounts]
  (let [[from-id to-id] (random-account-ids accounts)
        amount (rand-int (balance accounts from-id))]
    (transfer {:accounts accounts
               :from from-id
               :to to-id
               :amount amount})))

(defn bunch-o-txes
  "Return n futures doing iterations random transactions
   against accounts, spread equally across n threads."
  [accounts n iterations]
  (take n (repeatedly
           (fn []
             (future
               (dotimes [_ (/ iterations n)]
                 (random-transfer accounts)))))))

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
