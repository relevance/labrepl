(ns labrepl.apple-pie-bench
  (:require
   [solutions.apple-pie :as pie]
   [criterium.core :as crit]
   [clojure.core.reducers :as r]
   [clojure.pprint :as pprint]))

(defn filter-apples
  [apples]
  (->> (filter :edible? apples)
       dorun))

(defn map-apples
  [apples]
  (->> (map #(dissoc % :sticker?) apples)
       dorun))

(defn map-and-filter-apples
  [apples]
  (->> (filter :edible? apples)
       (map #(dissoc % :sticker?))
       dorun))

(defn pmap-apples
  [apples]
  (->> (pmap #(dissoc % :sticker?) apples)
       dorun))

(def prep (comp (r/filter :edible?)
                (r/map #(dissoc % :sticker))))

(defn reduce-apples
  [apples]
  (->> (prep apples)
       (into [])))

(defn fold-apples
  [apples]
  (->> (prep apples)
       r/foldcat))

(defn bench
  "Wrap criterium so that it does not use stdout"
  [f]
  (let [s (java.io.StringWriter.)]
    (binding [*out* s]
      (assoc (crit/quick-benchmark* f)
        :stdout (str s)))))

(defn -main
  [& _]
  (let [mean-msec #(long (* 1000 (first (:sample-mean %))))]
    (->> (for [napples [100000 1000000 10000000]
               :let [apples (pie/gen-apples {:n napples :type :golden :stickers 1 :edible 0.8})]
               sym '[filter-apples map-apples map-and-filter-apples pmap-apples reduce-apples fold-apples]]
           (do
             (print "Testing " sym " " napples ": ") (flush)
             (let [result (bench (partial @(resolve sym) apples))]
               (println (mean-msec result) " msec")
               {:op sym
                :napples napples
                :result result})))
         (map
          (fn [{:keys [op napples result]}]
            {"Operation" op
             "Apples" napples
             "Mean Time (msec)" (mean-msec result)}))
         (pprint/print-table))))

(comment
  (require :reload 'labrepl.apple-pie-bench)
  (-main)
  (set! *print-length* 100)
  (in-ns 'labrepl.apple-pie-bench)

  (crit/quick-benchmark* filter-apples)

  )

(comment

  ;; example criterium output

{:lower-q
 [0.057168000000000004 (0.057168000000000004 0.057197500000000005)],
 :sample-count 6,
 :tail-quantile 0.025,
 :mean
 [0.05752041666666667 (0.057238000000000004 0.05824208333333334)],
 :total-time 0.690245,
 :sample-variance [3.600937416666678E-7 (0.0 0.0)],
 :samples
 (114746000 114336000 114395000 117468000 114603000 114697000),
 :sample-mean
 [0.05752041666666667 (0.05572018232775409 0.05932065100557925)],
 :outlier-variance 0.13888888888888884,
 :os-details
 {:arch "x86_64",
  :available-processors 8,
  :name "Mac OS X",
  :version "10.7.5"},
 :outliers {:low-severe 0, :low-mild 0, :high-mild 0, :high-severe 1},
 :upper-q [0.058560812500000003 (0.057342625 0.058734)],
 :options
 {:max-gc-attempts 100,
  :samples 6,
  :target-execution-time 100000000,
  :warmup-jit-period 5000000000,
  :tail-quantile 0.025,
  :bootstrap-size 500},
 :results (0 0 0 0 0 0),
 :runtime-details
 {:java-version "1.6.0_37",
  :vm-version "20.12-b01-434",
  :clojure-version-string "1.5.1",
  :spec-version "1.0",
  :spec-name "Java Virtual Machine Specification",
  :input-arguments ["-Xmx6G"],
  :vm-name "Java HotSpot(TM) 64-Bit Server VM",
  :name "43482@Orolo.local",
  :clojure-version
  {:major 1, :minor 5, :incremental 1, :qualifier nil},
  :sun-arch-data-model "64",
  :vm-vendor "Apple Inc.",
  :java-runtime-version "1.6.0_37-b06-434-11M3909",
  :spec-vendor "Sun Microsystems Inc."},
 :variance
 [3.600937416666666E-7 (4.720141666666722E-9 7.266124416666656E-7)],
 :execution-count 2}

)
