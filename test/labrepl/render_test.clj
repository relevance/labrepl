(ns labrepl.render-test
  (:use clojure.test
        labrepl labrepl.util))

(deftest render-the-labs
  []
  (doseq [lab all]
    (let [url (lab-url lab)
          resp (application {:request-method :get
                             :uri url})]
      (is
       (= {:status 200
           :headers  
           {"Content-Type" "text/html; charset=utf-8"}}
          (select-keys resp 
                       [:status :headers]))))))
