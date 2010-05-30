(ns
    ^{:author "Stu Halloway"
       :doc "Compojure app that displays lab instructions."}
    labrepl
  (:use compojure clojure.contrib.logging)
  (:require [labrepl.lab :as lab]
            [solutions.mini-browser :as mini-browser]))

(defn with-logging [handler]
  (fn [request]
    (let [start (System/nanoTime)
          response (handler request)
          elapsed (/ (double (- (System/nanoTime) start)) 1000000.0)]
      (when response
        (log :info (str (:uri request) " [" (:request-method request) "] " elapsed " msec"
                        "\n\tParameters " (:params request)
                        "\n\tSession " (:session request)))
        response))))

(defroutes lab-routes
  (GET "/"
       (html
        (lab/layout
         [:h2.logo "Clojure Labs"]
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

(decorate lab-routes with-logging)

(defroutes app
  (routes lab-routes static-routes))

(defn load-common-libs
  []
  (use '[clojure.contrib.io :only (spit read-lines reader writer)]
       '[clojure.contrib def shell-out]
       '[clojure.contrib.repl-utils :only (show)]
       'clojure.pprint)
  (require '[clojure.contrib.str-utils2 :as s]))

(defn -main [& args]
  (load-common-libs)
  (mini-browser/main)
  (run-server
   {:port 8080}
   "/*"
   (servlet app))
  (println "Welcome to the labrepl. Browse to localhost:8080 to get started!"))

