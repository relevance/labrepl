;; build clojure and contrib from source for now
(defproject labrepl "0.0.1"
  :dependencies [[org.clojars.liebke/compojure
                  "0.3.1-master"]
                 [org.clojure/swank-clojure
                  "1.0"]
                 [postgresql
                  "8.4-701.jdbc4"]
                 [log4j
                  "1.2.14"]
                 [incanter
                  "1.0-SNAPSHOT"]
                 [antlr/stringtemplate
                  "2.2"]
                 [jline
                  "0.9.94"]
                 [circumspec
                  "0.0.4"]
                 [org.clojure/clojure
                  "1.1.0"]
                 [org.clojure/clojure-contrib
                  "1.1.0"]]
  :repositories {"clojure-releases" "http://build.clojure.org/releases"})