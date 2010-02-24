(ns solutions.fight
  (:use [clojure.http.client :only (request url-encode)]
        [clojure.contrib.json.read :only (read-json)]))

(def google-search-base
  "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=")

(defn estimated-hits-for
  [term]
  (let [http-response (request (str google-search-base (url-encode term)))
        json-response (read-json (apply str (:body-seq http-response)))]
    (Long/parseLong (get-in json-response ["responseData" "cursor" "estimatedResultCount"]))))

(defn fight
  [term1 term2]
  (let [r1 (future (estimated-hits-for term1))
        r2 (future (estimated-hits-for term2))]
    (future {term1 @r1 term2 @r2})))
