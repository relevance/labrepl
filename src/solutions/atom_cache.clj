(ns solutions.atom-cache
  (:refer-clojure :exclude [get]))

(defn create-1
  []
  (atom {}))

(defn get
  [cache key]
  (@cache key))

(defn put-1
  [cache key value]
  (swap! cache assoc key value))

(defn create
  ([] (create {}))
  ([initial-value] (atom initial-value)))

(defn put
  ([cache value-map]
     (swap! cache merge value-map))
  ([cache key value]
     (swap! cache assoc key value)))
