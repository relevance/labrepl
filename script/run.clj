(when-let [run-swank (System/getenv "LABREPL_SWANK")]
  (println "Starting swank...")
  ;; Drop the enclosing double quotes from the environment variable and eval it.
  (load-string (if (re-find #"^\".*\"$" run-swank)
                 (->> run-swank (drop 1) (butlast) (apply str))
                 run-swank)))

(require 'labrepl)
(set! *print-length* 100)
(labrepl/-main)
