(ns labs.zero-sum
  (:use labs.util)
  (:require [solutions
             [accounts-1 :as accounts-1]
             accounts-2
             accounts-3]))

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

(defn bonus
  []
  [[:h3 "Bonus"]
   [:ol
    [:li "Don't let the terminology of the example lock you into dollars-and-cents thinking. If you were building a program to play Monopoly, how many places might you use this code?"]
    [:li "What does the solution do to prevent accounts from going negative? How reliable is this? Sketch out at least two other approaches."]
    [:li "Could you use " (c commute) " instead of " (c alter) " to update the account balances? Read the requirements carefully."]]])

(defn instructions
  []
  (concat
   (overview)
   (requirements)
   (simple-accounts)
   (bonus)))

