(ns
    #^{:author "Stu Halloway"
       :doc "Utilities for creating lab instruction pages."}
    labs.util
  (:use clojure.contrib.pprint
        [clojure.contrib.repl-utils :only (get-source)]
        clojure.contrib.with-ns)
  (:require [clojure.contrib.str-utils2 :as s]))

(defn format-code
  [& codes]
  (apply str (map
              (fn [code]
                (if (string? code)
                  (str code "\n")
                  (with-out-str (pprint code))))
              codes)))

(defn code*
  "Show codes (literal strings or forms) in a pre/code block."
  [& codes]
  [:pre [:code (apply format-code codes)]])

(defmacro code
  "Show codes (literal strings or forms) in a pre/code block."
  [& codes]
  `(code* '~@codes))

(defmacro c
  "Show code (symbol or string) literally inline."
  [code]
  (let [code (if (string? code) (symbol code) code)]
    `[:code (s/chop (with-out-str (pprint '~code)))]))

(defmacro source
  "Show source code in a pre block."
  [sym]
  `[:pre [:code ~(get-source sym)]])

(defn showme*
  [code-string]
  [:div {:class "toggle"} 
   [:div [:a {:href "javascript:void(null)"} "Show Code"]]
   [:div {:style "display:none;"} [:a {:href "javascript:void(null)"} "Hide Code"] 
    (code* code-string)]])

(defmacro showme
  "Show code (literal string or source from a var) in a
   'Show Me' block students can click to reveal."
  [str-or-sym]
  (let [c (if (symbol? str-or-sym) (get-source str-or-sym) str-or-sym)]
    `(showme* '~c)))

(defn output-indent
  "Indent output lines with '-> /   ' (for showing REPL results)."
  [lines]
  (str
   "-> "
   (s/join "\n   "
           (s/split (with-out-str (pprint lines)) #"\n"))))

(defmacro returning-exceptions
  "Return exception info in string instead of blowing up."
  [& forms]
  `(try
    ~@forms
    (catch Exception e# (str (.getClass e#) " " (.getMessage e#)))))

(defn deindent
  "Deindent all lines of a string based on the indentation of the
   first line."
  [str]
  (when str
    (let [[line :as lines] (s/split str #"\n")]
      (if-let [whitespace-match (re-find #"^\s+" line)]
        (s/join "\n" (map #(s/drop % (.length whitespace-match)) lines))
        str))))

(defmacro repl*
  "Internal helper for repl."
  [form]
  (if (instance? String form)
    (let [result (returning-exceptions (load-string form))]
      `(str
        ~(deindent form)
        "\n"
        (output-indent '~result)))
    `(str
      (with-out-str (pprint '~form))
      (output-indent (returning-exceptions ~form)))))

(defmacro repl-code
  [form]
  `(code* (repl* ~form)))

(defmacro repl-showme
  [form]
  `(showme* (repl* ~form)))

