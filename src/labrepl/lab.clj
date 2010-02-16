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
  (html
    [:head
     [:title title]
     (include-css "/stylesheets/application.css")
     (include-js "/javascripts/jquery.js"
                 "/javascripts/application.js")]
    [:body
     [:div {:id "header"}
      [:h2 title]]
     [:div {:id "content"}
      body]
     [:div {:id "footer"}
      "Clojure labs are licensed under an Attribution-Share Alike 3.0 License"]]))

