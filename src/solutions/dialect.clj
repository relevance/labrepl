(ns
    ^{:author "Stu Halloway"
       :doc "Trivial examples used by the names-and-places lab."}
    solutions.dialect
  (:require [clojure.string :as s]))

(defn canadianize
  [sentence]
  (str (subs sentence 0 (dec (count sentence))) ", eh?"))

(defn pig-latinize-word
  [word]
  (s/replace word #"(?i:^([^aeiou]*)(.*)$)" "$2$1ay"))

(defn pig-latinize-sentence
  [sentence]
  (->> (s/split sentence #"[^\w]")
       (map pig-latinize-word)
       (s/join " ")))
