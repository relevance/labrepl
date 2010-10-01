(ns solutions.looping)

(defn zipm-1
  [keys vals]
  (loop [m {}
         ks (seq keys)
         vs (seq vals)]
    (if (and ks vs)
      (recur (assoc m (first ks) (first vs))
             (next ks)
             (next vs))
      m)))

(defn zipm-2
  [keys vals]
  (loop [m {} 
       [k & ks :as keys] (seq keys) 
       [v & vs :as vals] (seq vals)]
  (if (and keys vals)
    (recur (assoc m k v) ks vs)
    m)))

(defn zipm-3
  [keys vals]
  (reduce (fn [m [k v]] (assoc m k v)) 
          {} (map vector keys vals)))

(defn zipm-4
  [keys vals]
  (apply hash-map (interleave keys vals)))

(defn zipm-5
  [keys vals]
  (into {} (map vector keys vals)))

(defn min-1
  [x & more]
  (loop [min x
         more (seq more)]
    (if-let [i (first more)]
      (recur (if (< i min) i min) (next more))
      min)))

(defn min-2
  [x & more]
  (loop [min x
         [i & more] (seq more)]
    (if i
      (recur (if (< i min) i min) more)
      min)))

(defn min-3
  [& coll]
  (reduce
   (fn [x y] (if (< x y) x y))
   coll))

(defn minmax-1
  [x & more]
  (loop [min x
         max x
         [x & more] (seq more)]
    (if x
      (recur
       (if (< x min) x min)
       (if (> x max) x max)
       more)
      {:min min :max max})))

(defn minmax-2
  [x & more]
  (reduce
   (fn [result x]
     (->> result
         (merge-with min {:min x})
         (merge-with max {:max x})))
   {:min x :max x}
   more))
