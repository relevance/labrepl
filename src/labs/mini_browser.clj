(ns ^{:skip-wiki true} labs.mini-browser
    (:use labrepl.util labrepl.web solutions.browser-mockup solutions.mini-browser compojure))

(defn overview
  []
  [[:h3 "Overview"]
   [:p "In this lab you will implement a simple Clojure object browser using the web framework Compojure. The browser will provide access to Clojure namespaces, vars, data, docstrings, and source code. "]])

(defn ll
  "literal link"
  [s]
  [:a {:href s} s])

(defn defining-layout
  []
  [[:h3 "Layout"]
   [:p "Clojure data can easily be viewed as isomorphic to HTML. A Clojure vector that begins with a keyword is an HTML element, a Clojure map is HTML attributes, and strings are strings. Compojure exposes the isomorphism through the " (c html) " function, which we will use to build a mockup of the mini-browser."]
   [:ol
    [:li "From a REPL, use the compojure namespace."]
    [:li "Create a simple paragraph element with some text:"
     (repl-showme (html [:p "hello world"]))]
    [:li "Use a map to add a css class of " (c awesome) ":"
     (repl-showme (html [:p {:class "awesome"} "hello world"]))]
    [:li "How about an html " (c head) " with title 'Mini-Browser, and a " (c body) " with id 'browser'?"
     (repl-showme (html [:head [:title "Mini-Browser"]] [:body {:id "browser"}]))]
    [:li "Ok, that is about enough HTML to view as a Clojure string. Let's make a server. Put the html from the previous step into a " (c mockup-1) " function:"
     (showme mockup-1)]
    [:li "Use " (c defroutes) " to create a " (c mockup-routes) " table that routes a GET of /m1 to" (c mockup-1)". "
     (showme* '(defroutes mockup-routes
  (GET "/m1" (mockup-1))))]
    [:li "Create a " (c mockup-server) " function that calls " (c run-server) " with three arguments:"
     [:ol
      [:li "a map with the port to use (8999)"]
      [:li "the routes to serve (\"/*\", i.e. everything)"]
      [:li "a " (c servlet) " that serves the " (c mockup-routes)]]
     (showme mockup-server)]
    [:li "Run the " (c mockup-server) " and browse to " (ll "http://localhost:8999/m1") " to see the (empty) page."]
    [:li "Let's create a more complete " (c mockup-2) " with some layout divs."
     (source mockup-2)]
    [:li "Add " (c mockup-2) " to a route /m2 and browse to " (ll "http://localhost:8999/m2") " to see the second mockup."]
    [:li "Now let's add some standard styling and JavaScript. The " (c include-css) " function generates markup to pull in CSS files. Try it at the REPL:"
     (repl-showme (include-css "/stylesheets/application.css"))]
    [:li (c include-js) " does the same thing for JavaScript:"
     (repl-showme (include-js "/javascripts/application.js"))]
    [:li "The labs include a set of premade JavaScripts and CSS files. Use the namespace " (c labrepl.web) ", and you can access these files via the vars " (c default-stylesheets) " and " (c default-javascripts)": "
     (repl-showme [default-stylesheets, default-javascripts])]
    [:li "Create a " (c mockup-3) " that adds the default CSS and JavaScripts:"
     (showme mockup-3)]
    [:li "Add an m3 route to " (c mockup-3) ", and a catch-all route that serves files (The " (c serve-file) " function will serve files from the " (c public) " directory by default.)"
     (showme* '(defroutes mockup-routes
                 (GET "/m1" (mockup-1))
                 (GET "/m2" (mockup-2))
                 (GET "/m3" (mockup-3))
                 (GET "/*" (or (serve-file (params :*)) :next))))]]])

(defn static-mockup
  []
  [[:h3 "Static Mockup"]
   [:p "The mini-browser will show a list of namespaces, a list of vars in the currently-selected namespace, and the docstring and source code for the currently-selected var. In this section we will get the UI working with fake data. As you write each function, test its output."]
   [:ol
    [:li "The browser will use the url /browse/foo for browsing the namespace foo. Create a " (c namespace-link) " function that returns an anchor tag linking to the passed in namespace name."
     (showme namespace-link)]
    [:li "Create a " (c namespace-browser) " function that takes a collection of namespace names, and creates a " (c div.browse-list) " around an unordered list of links from" (c namespace-link) "."
     (showme namespace-browser)]
    [:li "We will use the url " (c "/browse/com.foo/bar") " for browsing the var " (c bar) " in the " (c com.foo) " namespace. Create a " (c var-link) " function that takes a namespace name and a var name and builds a link."
     (showme var-link)]
    [:li "Create a " (c var-browser) " function that puts a " (c div.browse-list) (c variables) " around an unorderd list of var links:"
     (showme var-browser)]
    [:li "Create a " (c mockup-4) " that is like " (c mockup-3) ", but add a " (c namespace-browser) " and a " (c var-browser) " to the content div. (Make up some fake namespace and var names to populate the data."
     (showme mockup-4)]
    [:li "Create a route to " (c mockup-4) " and test it in the browser at " (ll "http://localhost:8999/m4") "."]
    ]])

(defn making-it-live
  []
  [[:h3 "Making It Live"]
   [:ol
    [:li "Let's leave the mockup behind, and create new routes and a servlet for live data. First, create a " (c layout) " function that looks like the mockups, but lets you pass in the body forms to fill the content div:"
     (showme layout)]
    [:li "We need a function to get the real list of namespace names. " (c all-ns) " returns the ns objects, from there you can get their " (c .name) "s and " (c sort) " them. Create a " (c namespace-names) " function that does this."
     (showme namespace-names)]
    [:li "Test " (c namespace-names) "."
     (repl-showme (namespace-names))]
    [:li "Next, create " (c browser-routes) " so that a GET of / returns the " (c (namespace-names)) " wrapped in a " (c namespace-browser) " in a " (c layout) "."
     (showme* '(defroutes browser-routes
                 (GET
                  "/"
                  (html
                   (layout
                    (namespace-browser (namespace-names)))))))]
    [:li "Create a separate " (c static-routes) " for static content."
     (showme static-routes)]
    [:li "Create " (c app-routes) " to combine browser routes and static routes."]
    [:li "Create a " (c main) " function to launch the browser on port 9000."
     (showme main)]
    [:li "Run " (c main) " and browse to " (ll "http://localhost:9000") " to see the live namespaces."]
   
    [:li "Next we need a function " (c var-names) " that returns the var names given a namespace name. To build it you will need to call" (c symbol) ", " (c find-ns) ", " (c ns-publics) ", and " (c keys) "."
     (showme var-names)]
    [:li "Test " (c var-names) "."
     (repl-showme (var-names "clojure.xml"))]
    [:li "Now for the var details. First, we need a simple " (c var-symbol) "helper that manufactures a fully-qualified var symbol, given the namespace and var as strings:"
     (showme var-symbol)]
    [:li "The " (c var-detail) " function should return markup:"
     [:ul
      [:li "The var symbol in an h3 tag"]
      [:li "The docstring in a " (c "pre code") " block. You can combine " (c print-doc) " and " (c with-out-str) " to get the docstring"]
      [:li "The source code itself. You will need to use" (c find-var) ", " (c clojure.repl/source-fn) ", and " (c labrepl.util/code*) ", which will wrap the code in html tags for you."]]
     (showme var-detail)]
    [:li "Test " (c var-detail) " from the repl."
     (repl-showme (var-detail "clojure.core" "and"))]
    [:li "Update the " (c browser-routes) " to include a " (c "/browse/*") " route. It should extract the namespace and var names from the params, and then call " (c namespace-browser) ", " (c var-browser) ", and " (c var-detail) " to produce the html response."
     (showme browser-routes)]
    [:li "Run the browser with the new routes, and try it out at " (ll "http://localhost:9000") "."]]])

(defn bonus
  []
  [[:h3 "Bonus"]
   [:ol
    [:li "The 'everything is a function' approach makes testing easy. Routes are just functions--try calling one of your routes directly:"
     (repl-showme (mockup-routes {:uri "/m1"}))]
    [:li "The syntax highlighter isn't perfect--it sometimes highlights parts of words. What is the issue, and what is the fix?"]
    [:li "The lab you are reading is itself a Compojure app. Use your browser to explore the namespaces starting with " (c labrepl) "."]]])

(defn instructions
  []
  (concat
   (overview)
   (defining-layout)
   (static-mockup)
   (making-it-live)
   (bonus)))
