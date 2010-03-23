# labrepl for Clojure

Copyright (c) Relevance, Inc. All rights reserved.
The use and distribution terms for this software are covered by the
Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
which can be found in the file epl-v10.html at the root of this distribution.
By using this software in any fashion, you are agreeing to be bound by
the terms of this license.
You must not remove this notice, or any other, from this software.

# From the Command Line

You will need to work at the command line long enough to install the
dependent libs. Instructions below use leiningen. However, I have
checked in the maven pom.xml file, so if somebody will send me pure
maven instructions I can add a section for that as well.

## Getting Started (Mac, Linux)

* Make sure you have Java installed.
* Make sure you have leiningen installed (http://github.com/technomancy/leiningen).
* Run `lein deps` to install dependent libs.
* Run `script/repl` to launch the labrepl.
* Browse to localhost:8080 for instructions.
* Press Ctrl+D to exit the repl when you are done.

## Getting Started (Windows)

This is minimally tested. Let me know if you have problems.

* Make sure you have Java installed.
* Make sure you have leiningen installed (http://github.com/technomancy/leiningen). Do not use `lein self-install` as Windows installation is a manual process. You will need to:
* Download leinigen.jar and set `LEIN_JAR` to point to it.
* Download a Clojure 1.1 clojure.jar (from clojure.org) and set `CLOJURE_JAR` to point to it.
* Set your path to include `{your-lein-install}\bin`
* Run `lein.bat deps` to install dependent libs
* Run `script\repl` to launch the labrepl.
* Browse to localhost:8080 for instructions.
* Press Ctrl+D to exit the repl when you are done.

## Getting Started (Maven)

* Make sure you have Java installed.
* Make sure you have Maven (http://maven.apache.org/) installed.
* Run `mvn clojure:repl` to launch Clojure REPL (maven will download all necessary
  dependencies).
* Execute `(require 'labrepl) (set! *print-length* 100) (labrepl/-main)` to launch web
  server.
* Browse to localhost:8080 for instructions.
* Press Ctrl+D to exit the repl when you are done.

# IDE Support

## NetBeans Enclojure Integration

* Install NetBeans and  Enclojure (http://enclojure.org/). 
* From NetBeans, choose File|Open Project and open the labrepl project.
* Right-click on the project in the project pane and choose "Start Project REPL" to start your project REPL.

## Eclipse CounterClockwise Integration

* The repository includes Eclipse .project and .classpath files. If you are using the counterclockwise Eclipse plugin (http://code.google.com/p/counterclockwise/) you should be able to import the project into your workspace.

## IDEA Integration

* Help wanted here. You can create an IDEA project over existing sources, accept the defaults, and things will *partially* work. Completion will work against project code, but not against functions in clojure and clojure-contrib. If you know how to fix this, please send me a patch.

## Emacs Integration

If you don't know emacs you may want to pick one of the choices above. The learning curve is steep.

* Install Emacs, the Emacs Starter Kit, and 
* From within emacs, set the `inferior-lisp-program` variable to `script/swank`. 
* Making sure your emacs working directory is the labrepl, run `inferior-lisp`.
* If you want slime as well, run `slime-connect` and accept the defaults. You now have slime and inferior-lisp both working simultaneously.

# Thanks for contributions and reviews from

* Aaron Bedra
* Larry Karnowski
* Jess Martin
* Rich Hickey


 
