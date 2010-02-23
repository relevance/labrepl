(ns solutions.ref-cache-test
  (:use circumspec)
  (:require [solutions.ref-cache :as cache]))

(testing "ref version"
  (let [cache (cache/create {1 2})]
    (should (nil? (cache/get cache :foo)))
    (should (= {:foo :bar 1 2} (cache/put cache :foo :bar)))
    (should (= {:foo :zap 1 2} (cache/put cache {:foo :zap} )))
    (should (= {:foo :zap 1 3} (cache/fast-put cache {1 3})))))
