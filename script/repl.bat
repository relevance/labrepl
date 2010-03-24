@echo off
setLocal EnableDelayedExpansion
set CLASSPATH="
for /R ./lib %%a in (*.jar) do (
   set CLASSPATH=!CLASSPATH!;%%a
)
set CLASSPATH=!CLASSPATH!"
set CLASSPATH=%CLASSPATH%;src;test;config;data
echo CLASSPATH=%CLASSPATH%
java -Xmx1G -cp %CLASSPATH% jline.ConsoleRunner clojure.main -i script/run.clj -r
