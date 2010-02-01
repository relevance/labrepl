(ns labs.util
  (:use clojure.contrib.pprint
        [clojure.contrib.repl-utils :only (get-source)])
  (:require [clojure.contrib.str-utils2 :as s]))

(defn format-code
  [codes]
  (apply str (map (fn [c]
                    (if (string? c)
                      (str c "\n")
                      (with-out-str (pprint c)))) codes)))

(defmacro code
  [& codes]
  `[:pre [:code (format-code '~codes)]])

(defmacro c
  [code]
  `[:code ~(s/chop (with-out-str (pprint code)))])

(defmacro cite
  [sym]
  `[:div {:class "toggle"} 
    [:div [:a {:href "javascript:void(null)"} "Show Code"]]
    [:div {:style "display:none;"} [:a {:href "javascript:void(null)"} "Hide Code"] 
     [:pre [:code ~(get-source sym)]]]])