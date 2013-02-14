(ns solutions.mini-browser
  (:use [ring.adapter.jetty :only (run-jetty)]
        [compojure.core :only (defroutes GET)]
        [hiccup.core :only (html)]
        [hiccup.page-helpers :only (include-css include-js)]
        [labrepl.util :only (code*)]
        [labrepl.layout :only (default-stylesheets default-javascripts)]
        [compojure.route])
  (:require [clojure.string :as str]
            [clojure.repl :as repl]))

(defn namespace-names
  "Sorted list of namespace names (strings)."
  []
  (->> (all-ns)
       (map #(.name %))
       (sort)))

(defn var-names
  "Sorted list of var names in a namespace (symbols)."
  [ns]
  (when-let [ns (find-ns (symbol ns))]
    (sort (keys (ns-publics ns)))))

(defn namespace-link
  [ns-name]
  [:a {:href (str "/browse/" ns-name)} ns-name])

(defn namespace-browser
  [ns-names]
  [:div
   {:class "browse-list"}
   [:ul
    (map
     (fn [ns] [:li (namespace-link ns)])
     ns-names)]])

(defn var-link
  [ns-name var-name]
  [:a {:href (str "/browse/" ns-name "/" (java.net.URLEncoder/encode (str var-name)))} var-name])

(defn var-browser
  [ns vars]
  (html
   [:div
    {:class "browse-list variables"}
    [:ul
     (map
      (fn [var] [:li (var-link ns var)])
      vars)]]))

(defn layout [& body]
  (html
   [:head
    [:title "Mini-Browser"]
    (apply include-css default-stylesheets)
    (apply include-js default-javascripts)]
   [:body {:id "browser"}
    [:div {:id "header"}
     [:h2 "Mini-Browser"]]
    [:div {:id "content"}
     body]
    [:div {:id "footer"}
     "Clojure Mini-Browser"]]))

(defn view-function
  [func]
  (html
   [:h3 (find-var (symbol func))]))

(defn var-symbol
  "Create a var-symbol, given the ns and var names as strings."
  [ns var]
  (symbol (str ns "/" var)))

(defn var-detail
  [ns var]
  (when var
    (let [sym (var-symbol ns var)
          var (find-var sym)]
      (html [:h3 sym]
            [:h4 "Docstring"]
            [:pre [:code (:doc (meta var))]]
            [:h4 "Source"]
            (code* (repl/source-fn sym))))))

(defroutes application-routes
  (GET "/" [] (html
               (layout
                (namespace-browser (namespace-names))
                [:div {:class "browse-list empty"}])))
  (GET "/browse/*" request (let [[ns var] (str/split (get-in request [:params :*]) #"/")]
                             (html
                              (layout
                               (namespace-browser (namespace-names))
                               (var-browser ns (var-names ns))
                               (var-detail ns var)))))
  (files "/")
  (not-found "<h1>Not Found</h1>"))

(defn main []
  (run-jetty (var application-routes) {:port 9000
                                :join? false}))
