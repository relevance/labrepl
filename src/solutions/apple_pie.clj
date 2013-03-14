(ns ^{:author "Stu Halloway"}
  solutions.apple-pie
  (:require
   [clojure.data.generators :as gen]
   [clojure.core.reducers :as r]))

(defn gen-apples
  "Eagerly generate n apples of type type, where stickers is
   the proporition of apples that have stickers, and edible
   is the proportion of apples that are edible"
  [{:keys [n type stickers edible]}]
  (->> (repeatedly n (fn [] {:type type
                            :sticker? (< (gen/double) stickers)
                            :edible? (< (gen/double) edible)}))
       (into [])))




