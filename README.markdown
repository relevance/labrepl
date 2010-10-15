# labrepl for Clojure

Copyright (c) Relevance, Inc. All rights reserved.

The use and distribution terms for this software are covered by the
[Eclipse Public License 1.0](http://opensource.org/licenses/eclipse-1.0.php)
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

## Getting Started (NetBeans with Enclojure)

* Install the [Netbeans 6.9.1 Java SE bundle](http://netbeans.org/downloads/)
* Download the [nbgit](http://code.google.com/p/nbgit/downloads/detail?name=nbgit-0.4-netbeans-6.9.nbm&can=2&q=) plugin, storing the .nbm file.
* In Netbeans install the nbgit plugin: Tools->Plugins, Downloaded.  Select "AddPlugins" then "Install" pointing it to the .nbm file from the previous step.
* Follow the [Enclosure install](http://www.enclojure.org/) directions starting at "One time setup for Enclojure Plugin and Updates".
* In a terminal or 3rd party tool, clone the labrepl project.
* In Netbeans, File->Open Project and point it to the cloned labrepl project from the previous step.
* Right-click the labrepl project and select *Start Project Repl*
* In the REPL console, type:
  * `(require 'labrepl)`
  * `(labrepl/-main)`
* Browse to [http://localhost:8080](http://localhost:8080)
* Enjoy the labs!

## Getting Started (Eclipse with Counterclockwise)

This is a one-time setup that does not require the git command line, Maven, or leiningen.

* Install [Eclipse](http://www.eclipse.org/downloads/):
  * Download a recent version of "Eclipse IDE for Java Developers"
  * Unzip it in a directory of your choosing. 
  * The executable is in the unzipped directory: eclipse (Linux), Eclipse (Mac), or eclipse.exe (Windows)
* Launch Eclipse
  * The first time it is run, Eclipse will ask you for the location where
    Eclipse will put its metadata and will create new projects by default.
* Install the Eclipse Git (EGit) plugin:
  * Go to the Help > Install New Software menu
  * For the "Work with:" site, use: http://download.eclipse.org/egit/updates
  * Hit Enter
  * Expand the resulting "Eclipse Git Team Provider" set of features
  * Select the "Eclipse EGit Feature"
  * Verify the "Contact all update sites during ..." checkbox is checked
  * Click "Next" and accept the license when prompted
  * Click "Finish" to start the installation process
  * When EGit has been installed, restart Eclipse
* Install the Maven (m2e) plugin:
  * Go to the Help > Install New Software menu
  * For the "Work with:" site, use: http://m2eclipse.sonatype.org/sites/m2e
  * Hit Enter
  * Select "Maven Integration for Eclipse"
  * Verify the "Contact all update sites during ..." checkbox is checked
  * Click "Next" and accept the license when prompted
  * Click "Finish" to start the installation process
  * When Maven has been installed, restart Eclipse
* Install the Counterclockwise plugin:
  * Go to the Help > Install New Software menu
  * For the "Work with:" site, use: http://updatesite.counterclockwise.googlecode.com/hg/
  * Hit Enter
  * Select "Clojure Programming"
  * Verify the "Contact all update sites during ..." checkbox is checked
  * Click "Next" and accept the license when prompted
  * Click "Finish" to start the installation process
  * When Counterclockwise has been installed, restart Eclipse
* Clone the labrepl project from GitHub:
  * Go to File > Import ... > Git > Git Repository
  * Hit Next
  * For the "URI:", use: git://github.com/relevance/labrepl.git
  * Hit Next twice
  * Uncheck "Import Existing Projects" if prompted
  * Hit "Select All" and hit "Finish"
* Import the labrepl Maven project into Eclipse:
  * Go to File > Import ... > Maven > Existing Maven Projects
  * Hit Next
  * Choose the root directory where you cloned the labrepl project in the previous step
  * Verify that the pom.xml file is selected 
  * Hit Finish
* Enable Clojure support
  * Right-click the labrepl project in Package Explorer and choose "Enable/disable
    clojure language support"

To run the labrepl:

* Right click on the project in the Package Explorer and select Run As > Clojure REPL
* In the REPL console, type:
  * `(require 'labrepl)`
  * `(labrepl/-main)`
* Browse to [http://localhost:8080](http://localhost:8080)
* Enjoy the labs!

## Getting Started (IntelliJ IDEA with La Clojure)

This is a one-time setup that does not require the git command line, Maven, or leiningen.

* Install [IntelliJ IDEA](http://www.jetbrains.com/idea/). (The free Community Edition
  works fine.)
* Install the La Clojure plugin:
  * Go to File > Preferences
  * Open the Plugins preference and select the Available tab
  * Select La Clojure, right-click it, and choose "Download and Install"
* Restart IDEA.
* Clone the labrepl project from GitHub:
  * Go to Version Control > Checkout From Version Control > Git. 
  * Fill in the dialog with:
      * Git Repository URL: git://github.com/relevance/labrepl.git
      * Origin Name: (leave blank)
      * Parent Directory: (choose an arbitrary directory)
      * Directory name: labrepl
  * Hit the "Clone" button
  * Choose "Yes" when prompted to create an IDEA project, and then select:
      * Import Project from external model
      * Choose Maven if given choice of models
      * Accept all the defaults.  If this is your first time running IntelliJ you will need to choose a JDK. When prompted locate the JDK you wish to use by clicking the plus icon and clicking through the dialog.
  * Wait for Maven to download all the dependent libraries.

To run the labrepl:

* Choose Tools > Clojure REPL > Add New Clojure REPL
* In the REPL Console
  * `(require 'labrepl)`
  * `(labrepl/-main)`
* Browse to [http://localhost:8080](http://localhost:8080)
* Enjoy the labs!

## Getting Started (Maven)

* Make sure you have Java installed.
* Make sure you have [Maven](http://maven.apache.org/) installed.
* Clone the labrepl project and change directory into it.
* Run `mvn clojure:repl` to launch Clojure REPL (Maven will download all necessary
  dependencies).
* Browse to [http://localhost:8080](http://localhost:8080) for the labs.
* Press Ctrl+D to exit the repl when you are done.

## Getting Started (Mac/Linux command line) 

* Make sure you have Java installed.
* Make sure you have [leiningen](http://github.com/technomancy/leiningen) installed.
* Clone the labrepl project and change directory into it.
* Run `lein deps` to install all the dependent libraries.
* Run `script/repl` to launch the labrepl.
* Browse to [http://localhost:8080](http://localhost:8080) for the labs.
* Press Ctrl+D to exit the repl when you are done.

## Getting Started (Windows command line)

This is minimally tested. You are probably better off using Maven or one of the IDEs above. Let me know if you have problems.

* Make sure you have Java installed.
* Make sure you have [leiningen](http://github.com/technomancy/leiningen) installed. Do not use `lein self-install` as Windows installation is a manual process. You will need to:
* Download the leinigen.jar and set `LEIN_JAR` to point to it.
* Download [Clojure 1.3-alpha1](http://github.com/clojure/clojure/downloads)
* Set `CLOJURE_JAR` to point to the clojure.jar file.
* Set your path to include `{your-lein-install}\bin`
* Run `lein.bat deps` to install all the dependent libraries.
* Run `script\repl` to launch the labrepl.
* Browse to [http://localhost:8080](http://localhost:8080) for the labs.
* Press Ctrl+D to exit the repl when you are done.

## Getting Started (Emacs, Mac/Linux command line)

If you don't know Emacs, you may want to pick one of the choices above. The learning curve here is steep.

* Make sure you have Java installed.
* Make sure you have [leiningen](http://github.com/technomancy/leiningen) installed.
* Clone the labrepl project and change directory into it.
* Run `lein deps` to install all the dependent libraries.
* If they exist, move your `.emacs` and `.emacs.d` out of the way.
* Create the `.emacs.d` directory and download the Emacs Starter Kit:
  * `mkdir ~/.emacs.d`
  * `cd ~/.emacs.d`
  * `git clone http://github.com/technomancy/emacs-starter-kit.git`
* Launch Emacs with the starter kit `emacs -l ~/.emacs.d/emacs-starter-kit/init.el`
* Within Emacs run ELPA `M-x package-list-packages`, go to the following packages and press `i` to select:
  * clojure-mode
  * slime
  * swank-clojure 
* Once the three packages are selected press `x` to install them.  Close Emacs when the installation completes.
* From a terminal in the labrepl directory run the swank process: `script/swank`
* Run Emacs again `emacs -l ~/.emacs.d/emacs-starter-kit/init.el`
* Connect to the running clojure image `M-x slime-connect` accepting the defaults.


## Running the Tests

* Maven: `mvn clojure:test`
* Shell: `script/test`

## Thanks for contributions and reviews from

* Aaron Bedra
* Mike Clark
* Daniel Solano Gómez
* Rich Hickey
* Shawn Hoover
* Larry Karnowski
* Michael Kohl
* Jess Martin
* Alex Ott
* Laurent Petit
* Seth Schroeder
* Matthew Wizeman
