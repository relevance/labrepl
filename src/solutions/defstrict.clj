(ns solutions.defstrict)

(defn shout-1
  [s]
  (.toUpperCase s))

(defn shout-2
  [^String s]
  (.toUpperCase s))

(defn shout-3
  [^String s]
  {:pre [(instance? String s)]}
  (.toUpperCase s))

(defn shout-4
  [^{:tag String} s]
  {:pre [(instance? String s)]}
  (.toUpperCase s))

(defn instance-check
  "Returns an instance check based on a symbol's type hint,
   or nil."
  [sym]
  (if-let [type (:tag (meta sym))]
    `(instance? ~type ~sym)))

(defn arg-type-preconditions
  "Returns preconditions with type checks based on the type
   hints in arglist."
  [arglist]
  {:pre
   (->> (map instance-check arglist)
        (remove nil?)
        (into []))})

(defmacro defstrict
  [name arglist & body]
  `(defn ~name
     ~arglist
     ~(arg-type-preconditions arglist)
     ~@body))

(defstrict shout
  [^String s]
  (.toUpperCase s))
