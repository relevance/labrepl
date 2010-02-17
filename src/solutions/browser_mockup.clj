(ns solutions.browser-mockup
  (:use compojure labrepl.web labrepl.util)
  (:require [clojure.contrib.str-utils2 :as str]))

(defn mockup-1 []
  (html
    [:head
     [:title "Mini-Browser"]]
    [:body {:id "browser"}]))

(defroutes mockup-routes
  (GET "/m1" (mockup-1)))

(defn mockup-server []
  (run-server
   {:port 8999}
   "/*"
   (servlet mockup-routes)))
  

