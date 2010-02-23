(ns solutions.ref-cache
  (:refer-clojure :exclude [get]))

(defn create
  ([] (create {}))
  ([initial-value] (ref initial-value)))

(defn get
  [cache key]
  (@cache key))

(defn put
  ([cache value-map]
     (dosync
      (alter cache merge value-map)))
  ([cache key value]
     (dosync
      (alter cache assoc key value))))

(defn fast-put
  ([cache value-map]
     (dosync
      (commute cache merge value-map)))
  ([cache key value]
     (dosync
      (commute cache assoc key value))))
