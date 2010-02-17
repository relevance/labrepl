(ns #^{:skip-wiki true} labs.mini-browser
    (:use labrepl.util solutions.browser-mockup solutions.mini-browser compojure))

(defn overview
  []
  [[:h3 "Overview"]
   [:p "In this lab you will implement a simple Clojure object browser using the web framework Compojure."]])

(defn static-mockup
  []
  [[:h3 "Static Mockup"]
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
     (showme mockup-routes)]
    [:li "Create a " (c mockup-server) " function that calls " (c run-server) " with three arguments:"
     [:ol
      [:li "a map with the port to use (8999)"]
      [:li "the routes to serve (\"/*\", i.e. everything)"]
      [:li "a " (c servlet) " that serves the " (c mockup-routes)]]
     (showme mockup-server)]]])

(defn bonus
  [])

(defn instructions
  []
  (concat
   (overview)
   (static-mockup)
   (bonus)))
