(ns labrepl
  (:use compojure)
  (:require [labrepl.lab :as lab]))

(defn reloading [handler]
  (fn [request]
    (require :reload-all '[labrepl lab])
    (handler request)))

(defroutes lab-routes
  (GET "/"
       (html
        [:h2 "Labs"]
        [:ul
         (map
          (fn [lab] [:li (lab/url lab)])
          (lab/all))]))
  (GET "/labs/:name" (html (lab/instructions (params :name)))))

(decorate lab-routes reloading)

(defroutes app
  (routes lab-routes))

(defn -main [& args]
  (run-server
   {:port 8080}
   "/*"
   (servlet app))
  (println "Welcome to the labrepl. Browse to localhost:8080 to get started!"))

