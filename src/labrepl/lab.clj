(ns labrepl.lab
  (:use [clojure.contrib.java-utils :only (as-str)]
        labrepl.web
        compojure))

(defn all
  []
  [:intro :names-and-places :its-all-data :mini-browser
   :project-euler :zero-sum :defstrict])

(defn url
  [lab]
  (let [lab-name (as-str lab)]
    [:a {:href (str "/labs/" lab-name)} lab-name]))

(defn instructions
  [lab]
  (let [lab-ns (symbol (str "labs." (as-str lab)))]
    (require :reload-all lab-ns)
    ((ns-resolve lab-ns 'instructions))))

(defn layout [title & body]
  {:pre [(string? (last title))]}
  (html
    [:head
     [:title (last title)]
     (apply include-css default-stylesheets)
     (apply include-js default-javascripts)]
    [:body
     [:div {:id "header"}
      title]
     [:div {:id "content"}
      body]
     [:div {:id "footer"}
      "Clojure labrepl. Copyright 2010 Relevance Inc. All Rights Reserved."]]))

