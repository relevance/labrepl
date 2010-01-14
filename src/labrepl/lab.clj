(ns labrepl.lab
  (:use [clojure.contrib.java-utils :only (as-str)]))

(defn all
  []
  [:intro])

(defn url
  [lab]
  (let [lab-name (as-str lab)]
    [:a {:href (str "/labs/" lab-name)} lab-name]))

(defn instructions
  [lab]
  (let [lab-ns (symbol (str "labs." (as-str lab)))]
    (require :reload lab-ns)
    ((ns-resolve lab-ns 'instructions))))
