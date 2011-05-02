(defn load-common-libs
  []
  (use '[clojure.java.io :only (reader writer)]
       '[clojure.contrib.io :only (read-lines)]
       '[clojure.contrib def]
       '[clojure.contrib.repl-utils :only (show)]
       'clojure.pprint)
  (require '[clojure.contrib.str-utils2 :as s]))

(when-let [run-swank (System/getenv "LABREPL_SWANK")]
  (println "Starting swank...")
  ;; Drop the enclosing double quotes from the environment variable and eval it.
  (load-string (if-let [found (re-find #"^\"(.*)\"$" run-swank)]
                 (second found)
                 run-swank)))

(require 'labrepl)
(set! *print-length* 100)
(load-common-libs)
(labrepl/-main)
