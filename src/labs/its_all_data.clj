(ns #^{:skip-wiki true} labs.its-all-data
  (:use labrepl.util))

(defn some-data-types
  []
  [[:h3 "Some Data Types"]
   [:ol
    [:li "Let's start at the very beginning, with " (c nil) ". " (c nil) " evaluates to false in a boolean context, as you can see by calling"
     (code (if nil "nil is true" "nil is false"))]
    [:li "You can probably guess how " (c true) " and " (c false) " will behave in a boolean context:"
     (code (if true "logical true" "logical false"))
     (code (if false "logical true" "logical false"))]
    [:li "Integer literals come in decimal, octal and hex varieties:"
     (code "10")
     (code "010")
     (code "0x10")]
    [:li "Because math operators come first in an expression, you can pass as many arguments as you want"
     (code (+ 1 2 3))
     (code (+ 10 10 10))]
    [:li "Clojure has a ratio type, so division may surprise you:"
     (code (/ 22 7))]
    [:li "If you want a decimal result, pass a float literal:"
     (code (/ 22.0 7))]
    [:li "For integer division and remainder, use " (c quot) " and " (c rem) ": "
     (code (quot 22 7))
     (code (rem 22 7))]
    [:li "Clojure integer literals are not limited to the range of Java's primtive types. They can be any size, and Clojure will choose the correct underlying representation."
     (code (class (* 1000 1000)))
     (code (class (* 1000 1000 1000 1000)))
     (code (class (* 1000 1000 1000 1000 1000 1000 1000 1000)))
     "There are various ways to use Java primitive types directly; these techniques will be covered elsewhere."]
    [:li "Numeric literals suffixed with " (c M) " are " (c BigDecimal) "s, and provide the same guarantees for correctness as the underlying platform:"
     (code "(+ 100000M 0.00000000001M)")]
    [:li "Strings are simply Java Strings. They are immutable, and have access to all the underlying Java methods:"
     (code "hello")
     (code (.toUpperCase "hello"))]]])

(defn name-types
  []
  [[:h3 "Types that Name Things"]
   [:ol
    [:li "Symbols name things in code-space, e.g. functions and Java classes. You can call  a function through a symbol such as " (c println) ": "
     (code (println "hello"))]
    [:li "Rather than calling a function through a symbol, you might choose just to retrieve the function itself:"
     (code println)]
    [:li "The literal representation of a function at the REPL is just a mangled name. Instead of just looking at the function, you might create another name pointing to the same function:"
     (code (def my-println println))]
    [:li "Now you can call " (c my-println) " just like you would " (c println) ": "
     (code (my-println "hello"))]
    [:li "Sometimes you want to refer to the symbol itself, without retrieving whatever it refers to. To do this, you can " [:i "quote"] " the symbol:"
     (code (quote println))]
    [:li "Quoting is so common that that there is a sugared form: simply put a single quote in front of any symbol to prevent that form from being evaluated:"
     (code 'foo)]
    [:li "In fact, any Clojure form can be quoted. Look at the difference between"
     (code '(+ 1 2 3))
     " and "
     (code (+ 1 2 3))]
    [:li "Keywords name things in domain-space, e.g. the attributes of domain entities. Because they are not used to name code entities, they simply evaluate to themselves and do not have to be quoted. Keywords are preceded by a colon:"
     (code :first-name)
     (code :last-name)]
    [:li "Keywords are often used to cretae map literals:"
     (code (def me {:first-name "Stu" :last-name "Halloway"}))]
    [:li "You can extract values from a map with " (c get) ": "
     (code (get me :first-name))]
    [:li "Maps are themselves functions, taking a key argument, so you could also write:"
     (code (me :first-name))]
    [:li "Keywords are also functions, taking a map argument, so yet another approach is:"
     (code  (:first-name me))]
    [:li "Keywords are also used to name options. So, you can use only the " (c primes) " function from the lazy-seqs lib:"
     (code (use '[clojure.contrib.lazy-seqs :only (primes)]))]
    [:li "Now that we have the primes loaded, we might as well look at a few of them. We will cover " (c for) " in detail later, but for now here is how to use the " (c :while) " keyword option to retrieve only those primes less than a thousand:"
     (code (for [p primes :while (< p 1000)] p))]]])

(defn collections
  []
  [[:h3 "Collections"]
   [:ol
    [:li "Lists are the traditional lisp linked list. You can created them with " (c list) " or with a quoted literal:"
     (code (list 1 2 3))
     (code '(1 2 3))]
    [:li "List support efficient insertion at the front via " (c cons) ", which is short for \"construct\"."
     (code (cons 0 '(1 2 3)))]
    [:li "Vectors are indexed by integer, and can be created with a literal form in sqaure brackets:"
     (code ["hello" "world"])
     (code (get ["hello" "world"] 1))]
    [:li "Map literals are enclosed in " (c {}), "."
     (code "{:country \"US\" :capitol \"Washington, D.C.\"}")]
    [:li "Commas are whitespace. You may prefer using them to delimit pairs in map literals:"
     (code {:country "US", :capitol "Washington, D.C."})]
    [:li "Character literals look like " (c \Z) ", and sets are delimited by " (code #{}), ", so the set of English vowels is "
     (code #{\a \e \i \o \u})]
    [:li "Vectors, maps, and sets are all associative collections. You can use them as functions to lookup values:"
     (code (["hello" "world"] 1))
     (code ({:country "US", :capitol "Washington, D.C."} :country))
     (code (#{\a \e \i \o \u} \z))]
    [:li "Write a function " (c vowel) " that returns its input if it is a vowel, or " (c nil) " otherwise."
     (code (defn vowel [ch] (#{\a \e \i \o \u} ch)))]
    [:li "Since the set of vowels is already a function, you could write " (c vowel) " more concisely as "
     (code (def vowel #{\a \e \i \o \u}))
     "Use the REPL to test that the two versions of " (c vowel) " are equivalent."]]])

(defn seqs
  []
  [[:h3 "Seqs"]
   [:p "A sequence (seq) is a logical list. All Clojure collections are seq-able, that is, they can be treated as seqs. Using the sequence library, you can program in a general way against any Clojure collection type. Here are just a few examples:"
    [:ol
     [:li "You can get the " (c first) " or " (c rest) " of any collection:"
      (code (first [1 2 3]))
      (code (rest [1 2 3]))
      "Try taking the " (c first) " and " (c rest) " of some maps and sets. What are the return types?"]
     [:li "You can " (c take) " or " (c drop) "n elements of a collection."
      (code (take 5 (range 100)))
      (code (drop 5 (range 100)))
      "Again, try these functions against some other collection types as well."]
     [:li "You can " (c map) " a function acorss the elements of a collection, applying the function to each:"
      (code (map (fn [x] (* x 2)) (range 50)))]
     [:li "You can " (c filter) " a collection, returning only those elements that match a predicate:"
      (code (filter odd? (range 50)))]
     [:li (c reduce) " walks a collection, applying a function to pair and carrying the result. The following are equivalent:"
      (code "(reduce + [1 2]) => (+ 1 2)")
      (code "(reduce + [1 2 3]) => (+ (+ 1 2) 3)")]
     [:li "Try reducing something a little bigger."
      (code (reduce + (range 101)))]
     [:li "Because all collections can be treated as sequences, it is very easy to extend Clojure's power. If you write a function that works with seqs, all data can use it. The hardest part of writing Clojure apps is often deciding if the function you want already exists. What if you wanted something like " (c reduce), ", but returning all ther intermediate steps. No problem, that is called " (c reductions) ": "
      (code (use '[clojure.contrib.seq-utils :only (reductions)])
            (reductions + (range 101)))]]]])

(defn a-little-real-data
  []
  [[:h3 "A Little Real Data"]
   [:p "The file " (c data/words) " contains a typical Unix words file. Let's see how easy it is to analyze data using Clojure."]
   [:ol
    [:li "Use " (c read-lines) " to load the words file into a variable named " (c words) ". "
     (code (def words (read-lines "data/words")))]
    [:li "Use " (c count) " to verify that you got the words."
     (code (count words))]
    [:li "What is your favorite English word containing the string \"root\"? Use filter to get the possible choices:"
     (code "(filter #(.contains % \"root\") words)")]
    [:li "How many words in the file start with A?"
     (code "(count (filter #(.startsWith % \"A\") words))")]
    [:li "Let's generalize that a bit. The " (c frequencies) " function returns a map of values to the number of times each value occurs. Build a frequencies table by initial letter"
     (code (use '[clojure.contrib.seq-utils :only (frequencies)])
           (frequencies (map first words)))
     "(Note that hear you see one advantage of working with all the data, instead of thinking up a few examples. The results demonstrate that we need to decide how to deal with upper vs. lower case letters."]
    [:li "How about a frequency distribution by the length of the word?"
     (code "(frequencies (map #(.length %) words))")]
    [:li "Frequencies are easier to visualize with a bar chart. Load the incanter charting library:"
     (code (use '[incanter core charts]))]
    [:li "The " (c bar-chart) " function expects a sequence of categories, followed by a sequene of values. Use " (c let) " to capture frequencies in a local variable, and then call " (c keys) " and " (c values) " to pass the data to " (c bar-chart) ". "
     (code (let [f (frequencies (map first words))]
             (view (bar-chart (keys f) (vals f)))))]]])

(defn bonus
  []
  [[:h3 "Bonus"]
   [:ol
    [:li (c bar-chart) " takes addional options for controlling the display of the chart. Use them to make the frequencies chart more appealing."]
    [:li "Our first " (c reduce) " example showed using reduce to perform repeated addition. But in Clojure, the " (c +) " function can itself perform repeated addition! What advantages does " (c reduce) " offer over implicit reduction within a funtion?"]
    [:li "Implement your own version of " (c frequencies) " using " (c reduce) "."]
    [:li "Pick a language that you know well. What feature of your chosen language is analogous to Clojure keywords? To Clojure symbols? What do the differences (if any) imply?"]
    [:li "The " (c reverse) " function returns the reverse of a collection."
     (code (reverse [1 2 3]))
     "Its behavior with strings may surprise you, but it is consistent:"
     (code (reverse "foobar"))
     "Why is " (c reverse) "'s behavior the right thing to do, even for strings, and what options can/should be provided for String->String functions?"]]])

(defn instructions
  []
  (concat (some-data-types)
          (name-types)
          (collections)
          (seqs)
          (a-little-real-data)
          (bonus)))
