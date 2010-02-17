(ns labrepl-test
  (:use labrepl labrepl.lab circumspec))

(testing "rendering the labs"
  (doseq [lab (all)]
    (let [url (lab-url lab)
          resp (app {:request-method :get :uri url})]
      (should (= {:status 200 :headers  {"Content-Type" "text/html"}}
                 (select-keys resp [:status :headers]))))))
