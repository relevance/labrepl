(defproject labrepl "0.0.1"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [[compojure
                  "0.3.2"]
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
                  "0.0.6"]
                 [org.clojure/clojure
                  "1.1.0"]
                 [org.clojure/clojure-contrib
                  "1.1.0"]]
  :dev-dependencies [[autodoc "0.7.0"]]
  :repositories {"clojure-releases" "http://build.clojure.org/releases"})
