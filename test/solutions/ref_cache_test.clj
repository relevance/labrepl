(ns solutions.ref-cache-test
  (:use clojure.test)
  (:require [solutions.ref-cache :as cache]))

(deftest test-ref-version
  (let [cache (cache/create {1 2})]
    (is (nil? (cache/get cache :foo)))
    (is (= {:foo :bar 1 2} (cache/put cache :foo :bar)))
    (is (= {:foo :zap 1 2} (cache/put cache {:foo :zap} )))
    (is (= {:foo :zap 1 3} (cache/fast-put cache {1 3})))))
