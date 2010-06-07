(ns labrepl.lab
  (:use labrepl.web compojure))

(defn all
  []
  [:intro :names-and-places :its-all-data :looping :project-euler
   :mini-browser :unified-update-model :zero-sum  :cellular-automata
   :defstrict :rock-paper-scissors])

(defn lab-url
  [lab-name]
  (str "/labs/" (name lab-name)))

(defn url
  [lab]
  [:a {:href (lab-url lab)} (name lab)])

(defn instructions
  [lab]
  (let [lab-ns (symbol (str "labs." (name lab)))]
    (require lab-ns)
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

