(ns labs.cellular-automata
  (:use solutions.automaton labrepl.util))

(defn groundwork
  []
  [[:h3 "Groundwork"]
   [:ol
    [:li "First, let's define some constants: " (c dim-board) " sets the board to 90x90, " (c dim-screen) " sets the screen to 600x600, and " (c dim-scale) " expresses the ratio of screen to board."
     (showme dim-board)
     (showme dim-screen)
     (showme dim-scale)]
    [:li "A board is a list of lists, with each value either " (c :on) " or " (c :off) ". At the REPL, try using a " (c for) " expression to create a single small row, with about half of the values randomly chosen to be initially " (c :on) ":"
     (repl-showme (for [y (range 10)] (if (< (rand) 0.5) :on :off)))]
    [:li "Using two nested for expressions, create a " (c new-board) " function that expects a pair of " (c [x y]) " sizes:"
     (showme new-board)]
    [:li "Test " (c new-board) " at the REPL:"
     (repl-showme (new-board 10 10))]
    [:li "Now let's create an empty Swing GUI to work with. I can't bear to step through the tedium, so just take a look at " (c launch-1) ":"
     (showme launch-1)]
    [:li "Run " (c launch-1) " and verify that it creates an empty window and returns a board."]]])

(defn swing-gui
  []
  [[:h3 "Swing GUI"]
   [:ol
    [:li "Time to bring the GUI to life. Create " (c launch-2) ", which is the fleshed-out version of the trivial " (c launch-1) ". It should add lets for a " (c "BufferedImage img") " to do the drawing work offscreen, and for a " (c panel) " that proxies " (c JPanel) ". The proxy should implement " (c paint) " by calling a (yet-to-be-written) " (c render) " function."
     (showme launch-2)]
    [:li "Render is going to iterate over the board, coloring cells white, gray, or black based on their on/dying/off status. Rather than have explicit loop variables, we are going to transform the board so that each cell knows its coordinates. Create a " (c with-coords) " function that takes a board and replace each cell with a " (c [status x y]) " tuple:"
     (showme with-coords)]
    [:li "Test " (c with-coords) " at the REPL:"
     (repl-showme (with-coords (new-board [3 3])))]
    [:li "Now define " (c render) ", which takes a graphics object, an image, and the board. It should:"
     [:ol
      [:li "Let a " (c background-graphics) " to hold " (c "(.getGraphics img)") "."]
      [:li "Fill the background-graphics with black."]
      [:li "Loop over all the cells in all the rows of " (c (with-coords board)) ", calling out to a (to-be-written) " (c (render-cell background-graphics cell)) " function."]
      [:li "Draw the image back into the real graphics object."]]
     (showme render)]
    [:li "Next, define " (c render-cell) ". It should scale the cell up to screen units using " (c dim-scale) ", set the color using the (to-be-written) " (c state->color) " function, and fill the cell on screen."
     (showme render-cell)]
    [:li "The " (c state->color) " function is actually just a map:"
     (showme state->color)]
    [:li "Make sure your functions are defined in the correct order, or declared before use."]
    [:li "Call " (c launch-2) ", and you should see one still frame from a cellular automaton."]]])

(defn let-er-rip
  []
  [[:h3 "Let 'Er Rip"]
   [:ol
    [:li "Each cell's state in the next step depends on the current state of its eight immediate neighbors. Clojure destructuring makes it easy to extract these neighbors from a list of lists. Write an " (c active-neighbors) " function that counts all the neighbors in the on state:"
     (showme active-neighbors)]
    [:li "Test " (c active-neighbors) " against a board that is all on:"
     (repl-showme (active-neighbors (repeat (repeat :on))))]
    [:li "The updates rules for a cell in Brian's Brain are:"
     [:ol
      [:li "An " (c :on) " cell goes to " (c :dying)]
      [:li "A " (c :dying) " cell goes to " (c :off)]
      [:li "An " (c :off) " cell with two active (" (c :on) ") neighbors goes to " (c :on)]
      [:li "All other cells go to off"]]
     "Implement " (c brians-brain-rules) "."
     (showme brians-brain-rules)]
    [:li "Test the rules at the REPL. The examples below are gratuitously cute in that they take advante of the assumption that unspecified==off."
     (repl-showme (brians-brain-rules nil))
     (repl-showme (brians-brain-rules (repeat (repeat :on))))
     (repl-showme (brians-brain-rules [[:on :on]]))]]])

(defn instructions
  []
  (concat
   (groundwork)
   (swing-gui)
   (let-er-rip)))
