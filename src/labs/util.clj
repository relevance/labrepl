(ns labs.util
  (:use clojure.contrib.pprint))

(defn format-code
  [codes]
  (println codes)
  (apply str (map (fn [c] (with-out-str (pprint c))) codes)))

(defmacro code
  [& codes]
  `[:pre [:code (format-code '~codes)]])

