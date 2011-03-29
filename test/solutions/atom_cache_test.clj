(ns solutions.atom-cache-test
  (:use clojure.test)
  (:require [solutions.atom-cache :as cache]))

(deftest test-version
  (let [cache (cache/create-1)]
    (is (nil? (cache/get cache :foo)))
    (is (= {:foo :bar} (cache/put-1 cache :foo :bar)))))

(deftest test-improved-version
  (let [cache (cache/create {1 2})]
    (is (nil? (cache/get cache :foo)))
    (is (= {:foo :bar 1 2} (cache/put cache :foo :bar)))
    (is (= {:foo :zap 1 2} (cache/put cache {:foo :zap} )))))
