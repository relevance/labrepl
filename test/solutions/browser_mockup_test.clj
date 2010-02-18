(ns solutions.browser-mockup-test
  (:use solutions.browser-mockup circumspec))

(testing "rendering the mockups"
  (doseq [url ["/m1" "/m2" "/m3" "/m4"]]
    (let [resp (mockup-routes {:request-method :get :uri url})]
      (should (= {:status 200 :headers  {"Content-Type" "text/html"}}
                 (select-keys resp [:status :headers]))))))

