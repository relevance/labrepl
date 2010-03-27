# labrepl for Clojure

Copyright (c) Relevance, Inc. All rights reserved.

The use and distribution terms for this software are covered by the
Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
which can be found in the file epl-v10.html at the root of this distribution.
By using this software in any fashion, you are agreeing to be bound by
the terms of this license.

You must not remove this notice, or any other, from this software.

Instructions below for getting started with NetBeans/Enclojure, Eclipse/Counterclockwise, Maven, Mac/Linux command line, Windows command line, IDEA/La Clojure, and Emacs.

## Getting Started (NetBeans/Enclojure)

One-time setup

* Grab labrepl from git
  * you do not need leiningen or to do the lein or any other steps above
* If you've just installed Netbeans, activate feature Java SE
  * Activate features is on the Start page
  * or from Tools, Plugins, Installed
* Install enclojure
  * Make sure you use [the latest .nbm from 2010-march-23](http://github.com/downloads/EricThorsen/enclojure/enclojure-plugin-2010-23-mar.nbm)	
  * Tools/Plugins/Downloaded, locate downloaded .nbm
* Restart Netbeans w/enclojure plugin installed
* Open project labrepl
  * File, Open project, select directory where you pulled labrepl
* Right click on Libraries in the project pane
  * Download missing dependencies
  *	wait for that to complete

To run the labrepl

* Right click on project in the project pane
  * Start Project REPL
* In the integrated REPL:
  * `(require 'labrepl)`
  * `(labrepl/-main)`
* Browse to localhost:8080
* Enjoy the labs!

## Getting Started (Eclipse/Counterclockwise)

* The repository includes Eclipse .project and .classpath files. If you are using the counterclockwise Eclipse plugin (http://code.google.com/p/counterclockwise/) you should be able to import the project into your workspace.

One-time setup

* If you do not have Eclipse installed
  * Grab a recent version of eclipse, e.g. the "Eclipse IDE for java developers" from the download page: http://www.eclipse.org/downloads/
  * Unzip it somewhere. The executable is eclipse (linux) eclipse.exe (windows) located in the eclipse/ directory created by the unzip operation
  * The first time it is run, it will ask you for a location on your disk where eclipse will put its metadata and will create new projects by default.
* If you do not have Eclipse Git (EGit) installed
  * You install it via the "software update center", that is:
  * Menu Help > Install new software...
  * Paste the following EGit url in the "Work with:" textbox: http://download.eclipse.org/egit/updates
  * Hit Enter
  * Select "Eclipse EGit feature", verify the "Contact all update sites during ..." chekbox is checked, click next, accept licence, etc., restart Eclipse
* If you do not have maven support in Eclipse (plugin m2e)
  * You install it via the "software update center", that is:
  * Menu Help > Install new software...
  * Paste the following maven2eclipse (m2e) url in the "Work with:" textbox: http://m2eclipse.sonatype.org/sites/m2e
  * Hit Enter
  * Select Maven Integration for Eclipse, verify the "Contact all update sites during ..." chekbox is checked, click next, accept licence, etc., restart Eclipse
* If you do not have Counterclockwise installed
  * You install it via the "software update center", that is:
  * Menu Help > Install new software...
  * Paste the following Counterclockwise url in the "Work with:" textbox: http://updatesite.counterclockwise.googlecode.com/hg/
  * Hit Enter
  * Select counterclockwise, verify the "Contact all update sites during ..." chekbox is checked, click next, accept licence, etc., restart Eclipse
* Grab labrepl from git via EGit
  * Menu File > Import ... > Git > Git Repository ; Hit Next
  * URI: git://github.com/relevance/labrepl.git ; Hit Next ; Hit Next ; Hit Next ; Hit Finish
  * You now have a project named labrepl in eclipse
* Download project labrepl project's dependencies
  * In the Package Explorer, right click on project labrepl > Maven > Enable Dependency Management
  * Wait while the project is updated (takes time because it is downloading all the missing dependencies automatically)

To run the labrepl

* Right click on project "labrepl" in the Package Explorer
  * Run as > Clojure REPL
* In the REPL Console:
  * `(require 'labrepl)`
  * `(labrepl/-main)`
* Browse to localhost:8080
* Enjoy the labs!

## Getting Started (Maven)

* Make sure you have Java installed.
* Make sure you have Maven (http://maven.apache.org/) installed.
* Run `mvn clojure:repl` to launch Clojure REPL (maven will download all necessary
  dependencies).
* Browse to localhost:8080 for instructions.
* Press Ctrl+D to exit the repl when you are done.

## Getting Started (Mac/Linux command line) 

* Make sure you have Java installed.
* Make sure you have leiningen installed (http://github.com/technomancy/leiningen).
* Run `lein deps` to install dependent libs.
* Run `script/repl` to launch the labrepl.
* Browse to localhost:8080 for instructions.
* Press Ctrl+D to exit the repl when you are done.

## Getting Started (Windows command line)

This is minimally tested. You are probably better off using Maven or one of the IDEs above. Let me know if you have problems.

* Make sure you have Java installed.
* Make sure you have leiningen installed (http://github.com/technomancy/leiningen). Do not use `lein self-install` as Windows installation is a manual process. You will need to:
* Download leinigen.jar and set `LEIN_JAR` to point to it.
* Download a Clojure 1.1 clojure.jar (from clojure.org) and set `CLOJURE_JAR` to point to it.
* Set your path to include `{your-lein-install}\bin`
* Run `lein.bat deps` to install dependent libs
* Run `script\repl` to launch the labrepl.
* Browse to localhost:8080 for instructions.
* Press Ctrl+D to exit the repl when you are done.

## Getting Started (IDEA/La Clojure)

Help wanted here. You can use the maven install instructions, then create an IDEA project over existing sources, accept the defaults, and things will *partially* work. Completion will work against project code, but not against functions in clojure and clojure-contrib. If you know how to fix this, please send me a patch.

## Getting Started (Emacs)

If you don't know emacs you may want to pick one of the choices above. The learning curve is steep.

* Install Emacs, the Emacs Starter Kit, Clojure mode, slime, and swank.
* From within emacs, set the `inferior-lisp-program` variable to `script/swank`. 
* Making sure your emacs working directory is the labrepl, run `inferior-lisp`.
* If you want slime as well, run `slime-connect` and accept the defaults. You now have slime and inferior-lisp both working simultaneously.

## Running the Tests

* Maven: `mvn clojure:test`
* Shell: `script/test`

## Thanks for contributions and reviews from

* Aaron Bedra
* Rich Hickey
* Shawn Hoover
* Larry Karnowski
* Michael Kohl
* Jess Martin
* Alex Ott
* Laurent Petit



 
