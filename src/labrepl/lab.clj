(ns labrepl.lab
  (:use [clojure.contrib.java-utils :only (as-str)]))

(defn all
  []
  [:intro :names-and-places :its-all-data :zero-sum :macros])

(defn url
  [lab]
  (let [lab-name (as-str lab)]
    [:a {:href (str "/labs/" lab-name)} lab-name]))

(defn instructions
  [lab]
  (let [lab-ns (symbol (str "labs." (as-str lab)))]
    (require :reload-all lab-ns)
    ((ns-resolve lab-ns 'instructions))))
