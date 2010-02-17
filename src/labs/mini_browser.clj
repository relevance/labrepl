(ns #^{:skip-wiki true} labs.mini-browser
    (:use labrepl.util labrepl.web solutions.browser-mockup solutions.mini-browser compojure))

(defn overview
  []
  [[:h3 "Overview"]
   [:p "In this lab you will implement a simple Clojure object browser using the web framework Compojure. The browse will provide access to Clojure namespaces, vars, data, docstrings, and source code. "]])

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
     (repl-showme (html [:p {:class "awesome"}] "hello world"))]
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
    [:li "Run the " (c mockup-server) " and browse to " (ll "http://localhost:8099/m1") " to see the (empty) page."]
    [:li "Let's create a more complete " (c mockup-2) " with some layout divs."
     (source mockup-2)]
    [:li "Add " (c mockup-2) " to a route /m2 and browse to " (ll "http://localhost:8099/m2") " to see the second mockup."]
    [:li "Now let's add some standard styling and JavaScript. The " (c include-css) " function generates markup to pull in CSS files. Try it at the REPL:"
     (repl-showme (include-css "/stylesheets/application.css"))]
    [:li (c include-js) " does the same thing of for JavaScript:"
     (repl-showme (include-js "/javascripts/application.js"))]
    [:li "The labs include a set of premade JavaScripts and CSS files. Use the namespace " (c labrepl.web) ", and you can access these files via the vars " (c default-stylesheets) " and " (c default-javascripts)": "
     (repl-showme [default-stylesheets, default-javascripts])]
    [:li "Create a " (c mockup-3) " that adds the default CSS anad JavaScripts:"
     (showme mockup-3)]
    [:li "Add an m3 route to " (c mockup-3) ", and a catch-all route that serves files (The " (c serve-file) " function will server files from the " (c public) " directory by default.)"
     (showme* '(defroutes mockup-routes
                 (GET "/m1" (mockup-1))
                 (GET "/m2" (mockup-2))
                 (GET "/m3" (mockup-3))
                 (GET "/*" (or (serve-file (params :*)) :next))))]]])

(defn static-mockup
  []
  [[:h3 "Static Mockup"]
   [:p "The mini-browser will show a list of namespaces, a list of vars in the currently-selected namespace, and the docstring and source code for the currently-selected var. In this section we will get the UI working with fake data."]
   [:ol
    [:li "The browser will use the url /browse/foo for browsing the namespace foo. Create a " (c namespace-link) " function that returns an anchor tag linking to the passed in namespace name."
     (showme namespace-link)]]])
(defn bonus
  [])

(defn instructions
  []
  (concat
   (overview)
   (defining-layout)
   (static-mockup)
   (bonus)))
