(ns labrepl.lab
  (:use [clojure.contrib.java-utils :only (as-str)]
        compojure))

(defn all
  []
  [:intro :names-and-places :its-all-data :zero-sum :defstrict])

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
     (include-css "/stylesheets/shCore.css"
                  "/stylesheets/shThemeDefault.css"
                  "/stylesheets/application.css")
     (include-js "/javascripts/jquery.js"
                 "/javascripts/application.js"
                 "/javascripts/shCore.js"
                 "/javascripts/shBrushClojure.js")]
    [:body
     [:div {:id "header"}
      title]
     [:div {:id "content"}
      body]
     [:div {:id "footer"}
      "Clojure labs are licensed under an Attribution-Share Alike 3.0 License"]]))

