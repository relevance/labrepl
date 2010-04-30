(defproject labrepl "0.0.1"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [
                 [org.clojure/clojure
                  "1.2.0-master-20100430.130229-58"]
                 [org.clojure/clojure-contrib
                  "1.2.0-20100430.130503-89"]
                 [compojure
                  "0.3.2"]
                 [postgresql
                  "8.4-701.jdbc4"]
                 [log4j
                  "1.2.14"]
                 [org.incanter/incanter-full
                  "1.2.0-SNAPSHOT"]
                 [antlr/stringtemplate
                  "2.2"]
                 [clojure-http-client
                  "1.0.0-SNAPSHOT"]
                 [jline
                  "0.9.94"]
                 [circumspec
                  "0.0.11"]]
  :dev-dependencies [[autodoc "0.7.0"]
                     [swank-clojure "1.1.0"]]
  :repositories {"clojure-releases" "http://build.clojure.org/releases"
                 "incanter" "http://repo.incanter.org"})
