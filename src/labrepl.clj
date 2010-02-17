(ns
    #^{:author "Stu Halloway"
       :doc "Compojure app that displays lab instructions."}
    labrepl
  (:use compojure clojure.contrib.logging)
  (:require [labrepl.lab :as lab]
            [solutions.mini-browser :as mini-browser]))

(defn with-logging [handler]
  (fn [request]
    (log :info (str (:uri request) " [" (:request-method request) "]"
                    "\n\tParameters " (:params request)
                    "\n\tSession " (:session request)))
    (handler request)))

(defn reloading [handler]
  (fn [request]
    (require :reload-all '[labrepl lab])
    (handler request)))

(defroutes lab-routes
  (GET "/"
       (html
        (lab/layout
         [:h2 {:class "logo"} "Clojure Labs"]
         [:ul
          (map
           (fn [lab] [:li (lab/url lab)])
           (lab/all))])))
  (GET "/labs/:name"
       (html
        (lab/layout
         [:h2 (params :name)]
         (lab/instructions (params :name))))))

(defroutes static-routes
  (GET "/*" (or (serve-file (params :*)) :next))
  (ANY "*" (page-not-found)))

(decorate lab-routes reloading with-logging)

(defroutes app
  (routes lab-routes static-routes))

(defn -main [& args]
  (mini-browser/-main)
  (run-server
   {:port 8080}
   "/*"
   (servlet app))
  (println "Welcome to the labrepl. Browse to localhost:8080 to get started!"))

