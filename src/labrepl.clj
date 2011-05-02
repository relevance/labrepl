(ns ^{:author "Stu Halloway"
      :doc "Compojure app that displays lab instructions."}
  labrepl
  (:use [clojure.tools.logging :only (info)]
        [compojure.core :only (defroutes GET)]
        [ring.adapter.jetty :only (run-jetty)])
  (:require [compojure.route :as route]
            [handlers :as handlers]
            [labrepl.lab :as lab]
            [solutions.mini-browser :as mini-browser]))

(defn labrepl-home []
  (lab/layout
   [:h2.logo "Clojure Labs"]
   [:ul
    (map
     (fn [lab] [:li (lab/make-url lab)])
     (lab/all))]))

(defroutes lab-routes
  (GET "/" [] (labrepl-home))
  (GET "/labs/:name" [name] (lab/layout [:h2 name] (lab/instructions name)))
  (route/files "/")
  (route/not-found "<h1>Not Found</h1>"))

(def application (-> lab-routes
                     handlers/with-logging))

(defn -main [& args]
  (mini-browser/main)
  (run-jetty (var application) {:port 8080
                                :join? false})
  (println "Welcome to the labrepl. Browse to localhost:8080 to get started!"))
