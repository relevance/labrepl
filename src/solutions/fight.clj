(ns solutions.fight
  (:require [clojure.data.json :as json])
  (:import (java.net URL URLEncoder)))

(def google-search-base
  "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=")

(defn url-encode [q]
  (URLEncoder/encode q "UTF-8"))

(defn estimated-hits-for
  [term]
  (let [http-response (slurp (str google-search-base (url-encode term)))
        json-response (json/read-str http-response :key-fn keyword)]
    (Long/parseLong (get-in json-response [:responseData :cursor :estimatedResultCount]))))

(defn fight
  [term1 term2]
  (let [r1 (future (estimated-hits-for term1))
        r2 (future (estimated-hits-for term2))]
    (future {term1 @r1 term2 @r2})))

(def fight-results
  (agent {}))

(defn add-estimate
  [estimates term]
  (assoc estimates term (estimated-hits-for term)))

