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

(defn navigation [link-data]
  [:div {:id "breadcrumb"}
   [:div {:id "previous"} (if-let [prev (:prev link-data)]
                            (link-to (:prev-url link-data) (str "Previous Lab: " prev))
                            (link-to "/" "Home"))]
   [:div {:id "next"} (if-let [next (:next link-data)]
                        (link-to (:next-url link-data) (str "Next Lab: " next))
                        (link-to "/" "Home"))]])

(defn lab [title link-data & body]
  {:pre [(string? (last title))]}
  (html
    [:head
     [:title (last title)]
     (apply include-css default-stylesheets)
     (apply include-js default-javascripts)]
    [:body [:div {:id "header"} title]
     [:div {:id "content"}
      (navigation link-data)
      body]
     [:div {:id "footer"} "Clojure labrepl. Copyright Relevance Inc. All Rights Reserved."]]))
