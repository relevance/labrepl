(defproject labrepl "0.0.2-SNAPSHOT"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.clojure/data.json "0.2.2"]
                 [org.clojure/test.generative "0.4.0"]
                 [org.clojure/math.combinatorics "0.0.4"]
                 [com.datomic/datomic-free "0.8.4020"]
                 [org.codehaus.jsr166-mirror/jsr166y "1.7.0"]
                 [ring/ring-jetty-adapter "1.1.8" :exclusions [org.clojure/clojure
                                                                   org.clojure/clojure-contrib]]
                 [compojure "1.1.5" :exclusions [org.clojure/clojure]]
                 [hiccup "1.0.3" :exclusions [org.clojure/clojure]]
                 [log4j "1.2.17" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [jline "2.11"]]
  :main labrepl
  :dev-dependencies [[swank-clojure "1.3.0" :exclusions [org.clojure/clojure]]
                     [criterium/criterium "0.3.1"]])
