(ns ^{:author "Stu Halloway"
      :doc "Compojure app that displays lab instructions."}
  labrepl
  (:use [clojure.tools.logging :only (info)]
        [compojure.core :only (defroutes GET)]
        [ring.adapter.jetty :only (run-jetty)]
        [labrepl.util :only (make-url)])
  (:require [compojure.route :as route]
            [handlers :as handlers]
            [labrepl.layout :as layout]))

(def all [:intro
          :names-and-places
          :its-all-data
          :looping
          :project-euler
          :mini-browser
          :unified-update-model
          :zero-sum
          :cellular-automata
          :defstrict
          :rock-paper-scissors])

(defn home []
  (layout/page
   [:h2.logo "Clojure Labs"]
   [:ul
    (map
     (fn [lab] [:li (make-url lab)])
     all)]))

(defn instructions
  [lab]
  (let [lab-ns (symbol (str "labs." (name lab)))]
    (require lab-ns)
    ((ns-resolve lab-ns 'instructions))))

(defroutes lab-routes
  (GET "/" [] (home))
  (GET "/labs/:name" [name] (layout/page [:h2 name] (instructions name)))
  (route/files "/")
  (route/not-found "<h1>Not Found</h1>"))

(def application (-> lab-routes
                     handlers/with-logging))

(defn -main [& args]
  (run-jetty (var application) {:port 8080 :join? false})
  (println "Welcome to the labrepl. Browse to localhost:8080 to get started!"))
