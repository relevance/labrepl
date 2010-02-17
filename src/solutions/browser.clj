(ns solutions.browser
  (:use compojure labs.util)
  (:require [clojure.contrib.str-utils2 :as str]
            [clojure.contrib.repl-utils :as repl]))

(defn reloading [handler]
  (fn [request]
    (require :reload-all '[solutions.browser])
    (handler request)))

(defn layout [& body]
  (html
    [:head
     [:title "Mini-Browser"]
     (include-css "/stylesheets/shCore.css"
                  "/stylesheets/shThemeDefault.css"
                  "/stylesheets/application.css")
     (include-js "/javascripts/jquery.js"
                 "/javascripts/application.js"
                 "/javascripts/shCore.js"
                 "/javascripts/shBrushClojure.js")]
    [:body
     {:id "browser"}
     [:div {:id "header"}
      [:h2 "Mini-Browser"]]
     [:div {:id "content"}
      body
      #_[:a {:href "#" :class "top-link"} "^ Top"]]
     [:div {:id "footer"}
      "Clojure labs are licensed under an Attribution-Share Alike 3.0 License"]]))

(defn namespace-names
  "Sorted list of namespace names (strings)."
  []
  (->> (all-ns)
       (map #(.name %))
       (sort)))

(defn namespace-vars
  "Sorted list of var names in a namespace (symbols)."
  [ns]
  (when-let [ns (find-ns (symbol ns))]
    (sort (keys (ns-publics ns)))))

(defn namespace-browser
  []
  [:div
   {:class "browse-list"}
   [:ul
    (map
     (fn [ns] [:li [:a {:href (str "/ns/" ns)} ns]])
     (namespace-names))]])

(defn var-browser
  [ns]
  (html
   [:div
    {:class "browse-list variables"}
    [:ul
     (map
      (fn [var] [:li [:a {:href (str "/ns/" ns "/" var)} var]])
      (namespace-vars ns))]]))

(defn view-function
  [func]
  (html
   [:h3 (find-var (symbol func))]))

(defn var-detail
  [ns var]
  (when var
    (let [fqn-str (str ns "/" var)
          fqn-sym (symbol fqn-str)
          var (find-var fqn-sym)]
      (html [:h3 fqn-str]
            [:h4 "Docstring"]
            [:pre [:code
                   (with-out-str (print-doc var))]]
            [:h4 "Source"]
            (code* (repl/get-source fqn-sym))))))

(defroutes browser-routes
  (GET
   "/"
   (html
    (layout
     (namespace-browser)
     [:div {:class "browse-list empty"}])))
  (GET
   "/ns/*"
   (let [[ns var] (str/split (params :*) #"/")]
     (html
      (layout
       (namespace-browser)
       (var-browser ns)
       (var-detail ns var))))))

(defroutes static-routes
  (GET "/*" (or (serve-file (params :*)) :next)))

(decorate browser-routes reloading)

(defroutes app
  (routes browser-routes static-routes))

(defn -main [& args]
  (run-server
   {:port 9000}
   "/*"
   (servlet app)))
