(ns solutions.atom-cache-test
  (:use circumspec)
  (:require [solutions.atom-cache :as cache]))

(testing "initial version"
  (let [cache (cache/create-1)]
    (should (nil? (cache/get cache :foo)))
    (should (= {:foo :bar} (cache/put-1 cache :foo :bar)))))

(testing "improved version"
  (let [cache (cache/create {1 2})]
    (should (nil? (cache/get cache :foo)))
    (should (= {:foo :bar 1 2} (cache/put cache :foo :bar)))
    (should (= {:foo :zap 1 2} (cache/put cache {:foo :zap} )))))
