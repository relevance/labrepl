(defproject labrepl "0.0.2-SNAPSHOT"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.3"]
                 [org.clojure/data.json "0.2.1"]
                 [org.clojure/test.generative "0.3.0"]
                 [com.datomic/datomic-free "0.8.3848"]
                 [org.codehaus.jsr166-mirror/jsr166y "1.7.0"]
                 [ring/ring-jetty-adapter "1.0.0-RC1" :exclusions [org.clojure/clojure
                                                                   org.clojure/clojure-contrib]]
                 [compojure "0.6.5" :exclusions [org.clojure/clojure]]
                 [hiccup "0.3.7" :exclusions [org.clojure/clojure]]
                 [log4j "1.2.16" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [jline "0.9.94"]]
  :dev-dependencies [[swank-clojure "1.3.0" :exclusions [org.clojure/clojure]]
                     [criterium/criterium "0.3.1"]])
