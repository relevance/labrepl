(ns solutions.mini-browser
  (:use compojure labrepl.web labrepl.util)
  (:require [clojure.contrib.str-utils2 :as str]
            [clojure.contrib.repl-utils :as repl]))

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
  [:a {:href (str "/browse/" ns-name "/" var-name)} var-name])

(defn var-browser
  [ns vars]
  (html
   [:div
    {:class "browse-list variables"}
    [:ul
     (map
      (fn [var] [:li (var-link ns var)])
      vars)]]))

(defn reloading [handler]
  (fn [request]
    (require :reload-all '[solutions.mini-browser])
    (handler request)))

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
     (namespace-browser (namespace-names))
     [:div {:class "browse-list empty"}])))
  (GET
   "/browse/*"
   (let [[ns var] (str/split (params :*) #"/")]
     (html
      (layout
       (namespace-browser (namespace-names))
       (var-browser ns (var-names))
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
