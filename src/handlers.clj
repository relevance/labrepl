(ns handlers
  (:use [clojure.tools.logging :only (info)]))

(defn with-logging [handler]
  (fn [request]
    (let [start (System/nanoTime)
          response (handler request)
          elapsed (/ (double (- (System/nanoTime) start)) 1000000.0)]
      (when response
        (info (str (:uri request) " [" (:request-method request) "] " elapsed " msec"
                   "\n\tParameters " (:params request)
                   "\n\tSession " (:session request)))
        response))))