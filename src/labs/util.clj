(ns
    #^{:author "Stu Halloway"
       :doc "Utilities for creating lab instruction pages."}
    labs.util
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
  (let [code (if (string? code) (symbol code) code)]
    `[:code ~(s/chop (with-out-str (pprint code)))]))

(defmacro demo
  [sym]
  `[:pre [:code ~(get-source sym)]])

(defmacro cite
  [sym]
  `[:div {:class "toggle"} 
    [:div [:a {:href "javascript:void(null)"} "Show Code"]]
    [:div {:style "display:none;"} [:a {:href "javascript:void(null)"} "Hide Code"] 
     (demo ~sym)]])
