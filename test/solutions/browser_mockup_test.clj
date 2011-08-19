(ns solutions.browser-mockup-test
  (:use clojure.test
        solutions.browser-mockup))

(deftest test-mockups
    (doseq [url ["/m1" "/m2" "/m3" "/m4"]]
    (let [resp (mockup-routes {:request-method :get :uri url})]
      (is (= {:status 200 :headers  {"Content-Type" "text/html; charset=utf-8"}}
             (select-keys resp [:status :headers]))))))