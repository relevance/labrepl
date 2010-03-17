(ns solutions.defstrict)

(defn shout-1
  [s]
  (.toUpperCase s))

(defn shout-2
  [#^String s]
  (.toUpperCase s))

(defn shout-3
  [#^String s]
  {:pre [(instance? String s)]}
  (.toUpperCase s))

(defn shout-4
  [#^{:tag String} s]
  {:pre [(instance? String s)]}
  (.toUpperCase s))

(defn type-tagged-arg
  [[type name]]
  (with-meta name {:tag type}))

(defn type-tagged-args
  [arglist]
  (vec
   (map
    type-tagged-arg
    (partition 2 arglist))))

(defn arg-type-preconditions
  [arglist]
  {:pre
   (vec
    (map
     (fn [[type name]]
       `(instance? ~type ~name))
     (partition 2 arglist)))})

(defmacro defstrict
  [name arglist & body]
  `(defn ~name
     ~(type-tagged-args arglist)
     ~(arg-type-preconditions arglist)
     ~@body))

(defstrict shout
  [String s]
  (.toUpperCase s))
