(ns labs.zero-sum
  (:use labs.util)
  (:require [solutions
             [accounts-1 :as accounts-1]
             [accounts-2 :as accounts-2]
             [accounts-3 :as accounts-3]]))

(defn overview
  []
  [[:h3 "Overview"]
   [:p "This lab will demonstrate using Clojure's Software Transactional Memory (STM), and get you started thinking about where identities occur in your code."]])

(defn requirements
  []
  [[:h3 "Requirements"]
   [:p "You will be tracking the allocation of a fixed resource shared by N named accounts. A working solution will have the following:"]
   [:ol
    [:li "A function to create an initial set of account names and balances."]
    [:li "A function that transfers from one account to another."]
    [:li "A function to get the current balance for an individual account."]
    [:li "A function to get the total balance across all accounts. This balance should never change, since the resource is fixed and transfers are zero-sum."]
    [:li "All functions should be threadsafe!"]]
   [:p "If you are already somewhat comfortable with Clojure's reference API, try implementing these requirements from scratch. Otherwise, or if you get stuck, you can refer to the step-by-step instructions that follow."]])

(defn simple-accounts
  []
  [[:h3 "Simple Accounts"]
   [:ol
    [:li "Create a function named " (c make-accounts) " that takes a map with " (c :count) " and " (c :initial-balance) ". It should create a map whose keys are names (using numbers from 0 to count-1) and whose values are refs to the initial balance."
     (cite accounts-1/make-accounts)]
    [:li "Create a function named " (c total-balance) " that sums account values for accounts created by " (c :make-accounts) ". You will need to dereference each value in the map."
     (cite accounts-1/total-balance)]
    [:li "Create a " (c transfer) " function that takes a map keyed with " (c [:accounts :from :to :amount]) " and does a transfer. You will need to use " (c dosync) " to scope a transaction, and you should " [:i "not"] " need to actually read any balances."
     (cite accounts-1/transfer)]
    [:li "Create a " (c balance) " function that takes " (c accounts) " and an " (c accound-id) " and returns the current balance for an account."
     (cite accounts-1/balance)]
    [:li "Using the REPL or a unit testing framework you already know to test these functions against the requirements listed at the start of the lab. Don't worry about the \"must be thread safe!\" requirement yet."]]])

(defn is-it-threadsafe
  []
  [[:h3 "Is Is Threadsafe?"]
   [:p "No."]
   [:p "The transaction guarantees that all updates are atomic, consistent, and isolated. But the reads are not atomic, so " (c total-balance) " could be wrong. Let's prove it in code, by generating a bunch of random transactions on multiple threads and then watching reads get out of sync."]
   [:ol
    [:li "First, we needs some random account ids: a " (c from-id) " and a " (c to-id) ". But in Clojure it rarely makes sense to deliver " [:i "two"] " of something, when you could deliver an lazy, infinite sequence instead. Write a " (c random-account-ids) " function that returns a lazy sequence of ids from accounts. You can use " (c clojure.contrib.seq-utils/rand-elts) " to get a random element from a collection, and you can use " (c repeatedly) " to return a lazy sequence of results from calling an impure function."
     (cite accounts-1/random-account-ids)]
    [:li "Now, create a " (c random-transfer) " function to do a transfer at random. Let " (c [from-id to-id]) " from " (c random-account-ids) ", then use " (c rand-int) " to let a random transfer amount, then make the " (c transfer) " :"
     (cite accounts-1/random-transfer)]
    [:li "Next we need to do a bunch of transfers, from multiple threads."
     [:ul "Create a " (c bunch-o-txes) " function that takes " (c [accounts n iterations]) "."]
     [:ul "Use " (c dotimes) " to do a fraction of the iterations on each thread."]
     [:ul "Use " (c future) " to package the work to run on another thread."]
     [:ul "Use " (c take) " and " (c repeatedly) " to get n threads worth."]
     (cite accounts-1/bunch-o-txes)]
    [:li "Finally, we need a " (c trial) " function that puts it all together."
     [:ul "Trial should take a map with keys " (c [:accounts :iterations :threads]) ". "]
     [:ul "Let a variable to track the" (c expected-balance) " (which is the initial balance of accounts)."]
     [:ul "Let a variable to hold some futures created by calling " (c bunch-o-txes) ". "]
     [:ul "Start a loop. If all the futures are " (c (.isDone f)) ", then return the accounts and futures in a map. (This is useful for poking at the results in the REPL)."]
     [:ul "If the futures are not done, assert that the " (c expected-balance) " is still right, and " (c recur) ". "]
     [:ul "Create a no-argument arity version that starts with 10 accounts, balance 100 each, 1000 iterations, and 2 threads."]
     (cite accounts-1/trial)]
    [:li "Try running the trial. In my tests on a dual-core machine, the assertion fails almost instantly"]
    [:li "A simple fix is to change " (c total-balance) " to read within a transaction, which guarantees that all reads are from the same point-in-time."
     (cite accounts-2/total-balance)
     "Now you should be able to run as long a " (c trial) " as you like without a problem."]]
   [:p "Reading from a transaction is fast, requires no locks, and never impedes writers. However, there is a way to solve this problem the avoids the read transaction, by changing the granularity we use to think about identity."]])

(defn whos-who
  []
  [[:h3 "Who's Who?"]
   [:p "In the previous section the unit of identity was the individual account. But what if said that our identity was the set of all accounts sharing a single resource? Instead of map with values that are references, we could use a single reference to map. Let's make this change, and see how it affects each of our functions:"]
   [:p "To Be Continued..."]])

(defn bonus
  []
  [[:h3 "Bonus"]
   [:ol
    [:li "Don't let the terminology of the example trap you into dollars-and-cents thinking. If you were building a program to play Monopoly, how many places might you use this code?"]
    [:li "What does the solution do to prevent accounts from going negative? How reliable is this? Sketch out at least two other approaches."]
    [:li "Could you use " (c commute) " instead of " (c alter) " to update the account balances? Read the requirements carefully."]]])

(defn instructions
  []
  (concat
   (overview)
   (requirements)
   (simple-accounts)
   (is-it-threadsafe)
   (whos-who)
   (bonus)))

