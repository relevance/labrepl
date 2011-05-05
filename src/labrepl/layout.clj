(ns labrepl.layout
  (:use [hiccup.core :only (html)]
        [hiccup.page-helpers :only (include-css include-js link-to)]))

(def default-stylesheets
  ["/stylesheets/shCore.css"
   "/stylesheets/shThemeDefault.css"
   "/stylesheets/application.css"])

(def default-javascripts
  ["/javascripts/jquery.js"
   "/javascripts/application.js"
   "/javascripts/shCore.js"
   "/javascripts/shBrushClojure.js"])

(defn home [body]
  (html
    [:head
     [:title "Clojure Labs"]
     (apply include-css default-stylesheets)
     (apply include-js default-javascripts)]
    [:body [:div {:id "header"} [:h2.logo "Clojure Labs"]]
     [:div {:id "content"} body]
     [:div {:id "footer"} "Clojure labrepl. Copyright Relevance Inc. All Rights Reserved."]]))

(defn lab [title & body]
  {:pre [(string? (last title))]}
  (html
    [:head
     [:title (last title)]
     (apply include-css default-stylesheets)
     (apply include-js default-javascripts)]
    [:body [:div {:id "header"} title]
     [:div {:id "content"}
      [:div {:id "breadcrumb"} (link-to "/" "Home")]
      body]
     [:div {:id "footer"} "Clojure labrepl. Copyright Relevance Inc. All Rights Reserved."]]))
