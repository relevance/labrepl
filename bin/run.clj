(defn load-common-libs []
  (use '[clojure.java.io :only (reader writer)]
       'clojure.pprint))

(when-let [run-swank (System/getenv "LABREPL_SWANK")]
  (println "Starting swank...")
  (load-string (if-let [found (re-find #"^\"(.*)\"$" run-swank)]
                 (second found)
                 run-swank)))

(require 'labrepl)
(set! *print-length* 100)
(load-common-libs)
(labrepl/-main)
