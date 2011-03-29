(defproject labrepl "0.0.2-SNAPSHOT"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [compojure "0.6.2"]
                 [ring/ring-jetty-adapter "0.3.7"]
                 [hiccup "0.3.4"]
                 [postgresql "9.0-801.jdbc4"]
                 [log4j "1.2.16"]
                 [incanter "1.2.3"]
                 [antlr/stringtemplate "2.2"]
                 [clojure-http-client "1.0.0-SNAPSHOT"]
                 [swank-clojure "1.3.0-SNAPSHOT"]
                 [jline "0.9.94"]]
  :dev-dependencies [[swank-clojure "1.3.0-SNAPSHOT"]])
