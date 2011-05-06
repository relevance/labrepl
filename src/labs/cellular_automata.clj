(ns ^{:next "defstrict" :next-url "/labs/defstrict"
      :prev "Zero Sum" :prev-url "/labs/zero-sum"}
  labs.cellular-automata
  (:use [labrepl.util :only (c showme repl-showme repl-code)]
        [solutions.automaton]))

(defn overview []
  [[:h3 "Overview"]
   [:p "In this exercise, you will build a Swing GUI for displaying cellular automata such
        as " [:a {:href "http://en.wikipedia.org/wiki/Brian%27s_Brain"} "Brian's Brain"] "
        and " [:a {:href "http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life"} "Conway's
        Game of Life"] ". The code is inspired by blog examples by "
        [:a {:href "http://www.bestinclass.dk/index.php/2009/10/brians-functional-brain/"} "Lau Jensen"] "
        and " [:a {:href "http://blog.thinkrelevance.com/2009/10/7/brians-functional-brain-take-1-5"} "Stu Halloway" ] ".
        If you have never created an automaton before, read through the links above
        before you begin."]])
  
(defn groundwork []
  [[:h3 "Groundwork"]
   [:ol
    [:li "First, let's define some constants: " (c dim-board) " sets the board to
          90x90, " (c dim-screen) " sets the screen to 600x600, and " (c dim-scale) "
          expresses the ratio of screen to board."
          (showme dim-board)
          (showme dim-screen)
          (showme dim-scale)]
    [:li "A board is a list of lists, with each value either " (c :on) " or " (c :off) ".
          At the REPL, try using a " (c for) " expression to create a single small row,
          with about half of the values randomly chosen to be initially " (c :on) ":"
          (repl-showme (for [y (range 10)] (if (< (rand) 0.5) :on :off)))]
    [:li "Using two nested for expressions, create a " (c new-board) " function that
          expects a pair of " (c [x y]) " sizes:"
          (showme new-board)]
    [:li "Test " (c new-board) " at the REPL:"
     (repl-showme (new-board [10 10]))]
    [:li "Now let's create an empty Swing GUI to work with. I can't bear to step through
          the tedium, so just take a look at " (c launch-1) ":"
          (showme launch-1)]
    [:li "Run " (c launch-1) " and verify that it creates an empty window and returns a
          board."]]])

(defn swing-gui []
  [[:h3 "Swing GUI"]
   [:ol
    [:li "Time to bring the GUI to life. Create " (c launch-2) ", which is the
          fleshed-out version of the trivial " (c launch-1) ". It should add lets
          for a " (c "BufferedImage img") " to do the drawing work offscreen, and
          for a " (c panel) " that proxies " (c JPanel) ". The proxy should implement "
          (c paint) " by calling a (yet-to-be-written) " (c render) " function."
          (showme launch-2)]
    [:li "Render is going to iterate over the board, coloring cells white, gray, or
          black based on their on/dying/off status. Rather than have explicit loop
          variables, we are going to transform the board so that each cell knows its
          coordinates. Create a " (c with-coords) " function that takes a board and
          replaces each cell with a " (c [status x y]) " tuple:"
          (showme with-coords)]
    [:li "Test " (c with-coords) " at the REPL:"
          (repl-showme (with-coords (new-board [3 3])))]
    [:li "Now define " (c render) ", which takes a graphics object, an image, and the
          board. It should:"
     [:ol
      [:li "Let a " (c background-graphics) " to hold " (c "(.getGraphics img)") "."]
      [:li "Fill the background-graphics with black."]
      [:li "Loop over all the cells in all the rows of " (c (with-coords board)) ",
            calling out to a (to-be-written) "
            (c (render-cell background-graphics cell)) " function."]
      [:li "Draw the image back into the real graphics object."]]
     (showme render)]
    [:li "Next, define " (c render-cell) ". It should scale the cell up to screen
          units using " (c dim-scale) ", set the color using the (to-be-written) "
          (c state->color) " function, and fill the cell on screen."
          (showme render-cell)]
    [:li "The " (c state->color) " function is actually just a map:"
          (showme state->color)]
    [:li "Make sure your functions are defined in the correct order, or declared
          before use."]
    [:li "Call " (c launch-2) ", and you should see one still frame from a cellular
          automaton."]]])

(defn let-er-rip []
  [[:h3 "Let 'Er Rip"]
   [:ol
    [:li "Each cell's state in the next step depends on the current
          state of its eight immediate neighbors. Clojure destructuring
          makes it easy to extract these neighbors from a list of lists.
          Write an " (c active-neighbors) " function that counts all the
          neighbors in the " (c :on) " state:"
          (showme active-neighbors)]
    [:li "Test " (c active-neighbors) " against a board that is all on:"
     (repl-showme (active-neighbors (repeat (repeat :on))))]
    [:li "The updates rules for a cell in Brian's Brain are:"
     [:ol
      [:li "An " (c :on) " cell goes to " (c :dying)]
      [:li "A " (c :dying) " cell goes to " (c :off)]
      [:li "An " (c :off) " cell with two active (" (c :on) ") neighbors goes to "
            (c :on)]
      [:li "All other cells go to " (c off)]]
     "Implement " (c brians-brain-rules) " as a function of three rows."
     (showme brians-brain-rules)]
    [:li "Test the rules at the REPL."
     (repl-showme (brians-brain-rules nil nil nil))
     (repl-showme (brians-brain-rules [:on :on] nil nil))]
    [:li "In order to apply the rules, we need to take the entire board in 3x3 units.
          This is easy to visualize in one dimension, using Clojure's partition function:"
          (repl-code (partition 3 1 [:a :b :c :d :e]))
         "Using this idiom, implement a function " (c torus-window) ", so called because
          it 'wraps around' by prepending the last row and appending the first row:"
          (showme torus-window)]
    [:li "The most interesting function to write is " (c step) ", which
          calls " (c brians-brain-rules) " once for each cell, via a combination
          of " (c torus-window) ", " (c map) ", and " (c apply) ":"
          (showme step)]
    [:li "The stateful part of the application is simple. We will use an atom to reference
          the board. Write an " (c update-board) " function that takes an atom and updates
          it via " (c step) ":"
          (showme update-board)]
    [:li "Almost there. Create an " (c activity-loop) " function of " (c panel) "
          and " (c board) " that loops as long as the " (c board) " does not refer to
          nil, calling " (c update-board) " and then " (c .repaint) ":"
          (showme activity-loop)]
    [:li "Finally, we need the completed " (c launch) " function. " (c launch) " builds
          on the previous iteratations, adding a call to " (c future) " that kicks off
          the " (c activity-loop) " before returning the board."
          (showme launch)]
    [:li "You are ready to go! " (c launch) " your automaton and watch it go. When you
          (or your CPU) grow tired, you can stop it by setting the returned atom to "
          (c nil) ", or by simply killing the JVM."]]])

(defn bonus []
  [[:h3 "Bonus"]
   [:li "Load your code with " (c *warn-on-reflection*) " set to true, and then add type
         hints until your code is non-reflective."]
   [:li "What other things might make this code faster. Think about it a bit, and then
         read about "
    [:a {:href "http://www.bestinclass.dk/index.php/2009/10/brians-transient-brain/"}
               "Lau's experiences optimizing Brian's Brain."]]
   [:li "Add support for Conway's Game of Life. Hint: only one function needs to change."]
   [:li "Add a menu to switch between Conway and Brian mid-simulation."]])

(defn instructions []
  (concat (overview)
          (groundwork)
          (swing-gui)
          (let-er-rip)
          (bonus)))
