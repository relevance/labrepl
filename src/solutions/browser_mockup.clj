(ns solutions.browser-mockup
  (:use compojure labrepl.web labrepl.util)
  (:use [solutions.mini-browser]))

(defn mockup-1 []
  (html
    [:head
     [:title "Mini-Browser"]]
    [:body {:id "browser"}]))

(defn mockup-2 []
  (html
    [:head
     [:title "Mini-Browser"]]
    [:body {:id "browser"}
     [:div {:id "header"}
      [:h2 "Mini-Browser"]]
     [:div {:id "content"}
      "Body"]
     [:div {:id "footer"}
      "Clojure Mini-Browser"]]))

(defn mockup-3 []
  (html
    [:head
     [:title "Mini-Browser"]
     (apply include-css default-stylesheets)
     (apply include-js default-javascripts)]
    [:body {:id "browser"}
     [:div {:id "header"}
      [:h2 "Mini-Browser"]]
     [:div {:id "content"}
      "Body TBD"]
     [:div {:id "footer"}
      "Clojure Mini-Browser"]]))

(defn mockup-4 []
  (html
    [:head
     [:title "Mini-Browser"]
     (apply include-css default-stylesheets)
     (apply include-js default-javascripts)]
    [:body {:id "browser"}
     [:div {:id "header"}
      [:h2 "Mini-Browser"]]
     [:div {:id "content"}
      (namespace-browser ["fake-ns1" "fake-ns2"])
      (var-browser "fake-ns1" ["some-var-1" "some-var-2"])]
     [:div {:id "footer"}
      "Clojure Mini-Browser"]]) )

(defroutes mockup-routes
  (GET "/m1" (mockup-1))
  (GET "/m2" (mockup-2))
  (GET "/m3" (mockup-3))
  (GET "/m4" (mockup-4))
  (GET "/*" (or (serve-file (params :*)) :next)))

(defn mockup-server []
  (run-server
   {:port 8999}
   "/*"
   (servlet mockup-routes)))
  

