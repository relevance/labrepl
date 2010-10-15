(defproject labrepl "0.0.1"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.5.1"]
                 [ring/ring-jetty-adapter "0.3.0"]
                 [hiccup "0.3.0"]
                 [postgresql "8.4-701.jdbc4"]
                 [log4j "1.2.16"]
                 [incanter "1.2.3"]
                 [antlr/stringtemplate "2.2"]
                 [clojure-http-client "1.0.0-SNAPSHOT"]
                 [swank-clojure "1.3.0-SNAPSHOT"]
                 [jline "0.9.94"]
                 [mycroft "0.0.2"]
                 [circumspec "0.0.13"]]
  :dev-dependencies [[autodoc "0.7.0"]
                     [swank-clojure "1.3.0-SNAPSHOT"]]
  :repositories {"clojure-releases" "http://build.clojure.org/releases"})
