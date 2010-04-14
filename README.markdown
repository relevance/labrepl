# labrepl for Clojure

Copyright (c) Relevance, Inc. All rights reserved.

The use and distribution terms for this software are covered by the
Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
which can be found in the file epl-v10.html at the root of this distribution.
By using this software in any fashion, you are agreeing to be bound by
the terms of this license.

You must not remove this notice, or any other, from this software.

# What is it?

Labrepl is an environment for exploring the Clojure language. It
includes:

* a web application that presents a set of lab exercises with
  step-by-step instructions
* an interactive repl for working with the lab exercises
* solutions with passing tests 
* up-to-date versions of Clojure, contrib, incanter, compojure and a bunch of other libraries to explore

See instructions below for getting started with NetBeans/Enclojure, Eclipse/Counterclockwise, Maven, Mac/Linux command line, Windows command line, IDEA/La Clojure, and Emacs.

## Getting Started (NetBeans with the Enclojure plugin)

This is a one-time setup that does not require the git command line, Maven, or leiningen.

* Install the Java SE version of [NetBeans](http://netbeans.org/downloads/index.html)
* Launch NetBeans
* If you've just installed NetBeans for the first time, make sure the Java SE plugin is
active
  * Click the "Install Plugins" link on the Start page, then select the "Installed" tab or...
  * Go to the Tools > Plugins menu, then select the "Installed" tab
* Download the [git plugin](http://nbgit.googlecode.com/files/nbgit-0.3.nbm)
* Download the [Enclojure plugin](http://github.com/downloads/EricThorsen/enclojure/enclojure-plugin-2010-23-mar.nbm)
* Install the git and Enclojure plugins
  * Go to the Tools > Plugins menu, then select the "Downloaded" tab
  * Click the "Add Plugins..." button and locate the downloaded .nbms files
  * Click the "Install" button to complete the installation
* Restart NetBeans with the git and Enclojure plugins installed
* Clone the labrepl project from GitHub
  * Go to the Team > Git > Clone Other menu
  * For the Repository URL, use git://github.com/relevance/labrepl
  * Choose an arbitrary Parent Directory location where NetBeans will clone laprepl   
* Open the labrepl project
  * Use the File > Open Project menu and select the directory where you cloned labrepl
* Right click on "Libraries" in the project pane and select "Download Missing Dependencies"
  * Wait for that to complete

To run the labrepl:

1. Right click on the project in the project pane and select "Start Project REPL" (near the bottom)
2. In the integrated REPL window, type:
  * `(require 'labrepl)`
  * `(labrepl/-main)`
3. Browse to http://localhost:8080
4. Enjoy the labs!

## Getting Started (Eclipse/Counterclockwise)

This is a one-time setup that does not require the git command line, Maven, or leiningen.

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
  * URI: git://github.com/relevance/labrepl.git ; Hit Next ; Hit Next ; Uncheck "Import Existing Projects"; Hit Finish
* Import the maven project into Eclipse
  * Menu File > Import ... > Maven > Existing Maven Projects ; Hit Next
  * Choose root directory (wherever you dropped the labrepl in the previous step); Hit Finish
* Enable Clojure Support
  * Right-click the "labrepl" project in Package Explorer and choose "Enable/disable Clojure language support"  

To run the labrepl

* Right click on project "labrepl" in the Package Explorer
  * Run as > Clojure REPL
* In the REPL Console:
  * `(require 'labrepl)`
  * `(labrepl/-main)`
* Browse to localhost:8080
* Enjoy the labs!

## Getting Started (IDEA/La Clojure)

One-time setup

* Install [IntelliJ IDEA](http://www.jetbrains.com/idea/). The free Community Edition works fine.
* Install the La Clojure plugin:
  * Prefereneces/Plugins/Available, select La Clojure, right click
and download the plugin.
* Restart IDEA.
* Clone the labrepl repository:
  * Choose Version Control|Checkout From Version Control|Git. Fill in the dialog with:
      * Repository URL: git://github.com/relevance/labrepl.git
      * Leave Origin Name blank
      * Choose whatever Parent Directory you want
      * Directory name: labrepl
  * Choose "Yes" for create IDEA project, and then:
      * Import Project from external model
      * Choose Maven if given choice of models
      * Accept all the defaults (click "Next" a bunch of times.)
  * Wait for maven to download dependencies.

To run the labrepl

* Choose Tools|Clojure REPL|Add new Clojure REPL
* In the REPL Console
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
* Daniel Solano Gómez
* Rich Hickey
* Shawn Hoover
* Larry Karnowski
* Michael Kohl
* Jess Martin
* Alex Ott
* Laurent Petit
* Seth Schroeder


 
