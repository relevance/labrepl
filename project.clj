(defproject labrepl "0.0.1"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.4.0"]
                 [ring/ring-jetty-adapter "0.2.3"]
                 [hiccup "0.2.6"]
                 [postgresql "8.4-701.jdbc4"]
                 [log4j "1.2.14"]
                 [incanter "1.2.3-SNAPSHOT"]
                 [antlr/stringtemplate "2.2"]
                 [clojure-http-client "1.0.0-SNAPSHOT"]
                 [swank-clojure "1.3.0-SNAPSHOT"]
                 [jline "0.9.94"]
                 [mycroft "0.0.2"]
                 [circumspec "0.0.13"]]
  :dev-dependencies [[autodoc "0.7.0"]]
  :repositories {"clojure-releases" "http://build.clojure.org/releases"})
