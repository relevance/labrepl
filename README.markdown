# labrepl for Clojure

Copyright (c) Relevance, Inc. All rights reserved.
The use and distribution terms for this software are covered by the
Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
which can be found in the file epl-v10.html at the root of this distribution.
By using this software in any fashion, you are agreeing to be bound by
the terms of this license.
You must not remove this notice, or any other, from this software.

# Breaking News

The first use of the labrepl will be the March 15, 2010 run of the
Clojure Studio: (http://pragmaticstudio.com/clojure). If you are looking
at labrepl before that date, then QA is not complete. Expect a few oddities.

# Getting Started (Mac, Linux)

* Make sure you have Java installed.
* Make sure you have leiningen installed (http://github.com/technomancy/leiningen).
* Run `lein deps` to install dependent libs.
* Run `script/repl` to launch the labrepl.
* Browse to localhost:8080 for instructions.
* Press Ctrl+D to exit the repl when you are done.

# Getting Started (Windows)

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
 
# Thanks for contributions and reviews from

* Aaron Bedra
* Larry Karnowski
* Jess Martin
* Rich Hickey


 
