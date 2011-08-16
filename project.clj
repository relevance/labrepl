(defproject labrepl "0.0.2-SNAPSHOT"
  :description "Clojure exercises, with integrated repl and webapp"
  :dependencies [[org.clojure/clojure "1.3.0-master-SNAPSHOT"]
                 [org.clojure/tools.logging "0.1.2"]
                 [org.clojure/data.json "0.1.0"]
                 [ring/ring-jetty-adapter "0.3.7" :exclusions [org.clojure/clojure
                                                               org.clojure/clojure-contrib]]
                 [compojure "0.6.3" :exclusions [org.clojure/clojure]]
                 [hiccup "0.3.5" :exclusions [org.clojure/clojure]]
                 [log4j "1.2.16" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [swank-clojure "1.3.0-SNAPSHOT" :exclusions [org.clojure/clojure]]
                 [jline "0.9.94"]]
  :dev-dependencies [[swank-clojure "1.3.0-SNAPSHOT" :exclusions [org.clojure/clojure]]]
  :repositories {"snapshots" {:url "http://build.clojure.org/snapshots"}
                 "releases" {:url "http://build.clojure.org/releases"}})
