@echo off
setLocal EnableDelayedExpansion
set CLASSPATH="
for /R ./lib %%a in (*.jar) do (
   set CLASSPATH=!CLASSPATH!;%%a
)
set CLASSPATH=!CLASSPATH!"
set CLASSPATH=%CLASSPATH%;src;test;config;data
echo CLASSPATH=%CLASSPATH%
java -Xmx1G -cp %CLASSPATH% jline.ConsoleRunner clojure.main -e "(use '[clojure.contrib.duck-streams :only (spit read-lines reader writer)] '[clojure.contrib def ns-utils pprint repl-utils shell-out]) (require '[clojure.contrib.str-utils2 :as s])(require 'labrepl)(labrepl/-main)" -r
