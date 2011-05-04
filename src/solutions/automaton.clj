(ns solutions.automaton
  (import [javax.swing JFrame JPanel]
          [java.awt Color Graphics]
          [java.awt.image BufferedImage]))

(def dim-board   [ 90   90])
(def dim-screen  [600  600])
(def dim-scale   (vec (map / dim-screen dim-board)))

(defn new-board
  "Create a new board with about half the cells set to :on."
  ([] (new-board dim-board))
  ([[dim-x dim-y]]
     (for [x (range dim-x)]
       (for [y (range dim-y)]
         (if (< 50 (rand-int 100)) :on :off)))))

(defn with-coords [board]
  (for [[row-idx row] (map-indexed vector board)]
    (for [[col-idx val] (map-indexed vector row)]
      [val row-idx col-idx])))

(defn without-coords [board]
  (for [row board]
    (for [[state] row] state)))

(defn active-neighbors
  "Count the active (:on) neighbors one cell away from me in
   any direction. Maximum of 8."
  [[[nw n ne]
    [w  _ e ]
    [sw s se]]]
  (count
   (filter
    #{:on}
    (concat [nw n ne w e sw s se]))))

(defn brians-brain-rules
  "Determine the cell's next state based on its current
   state and number of active neighbors."
  [above [_ cell _ :as row] below]
  (cond
   (= :on    cell)                              :dying
   (= :dying cell)                              :off
   (= 2 (active-neighbors [above row below]))   :on
   :else                                        :off  ))

(defn torus-window
  "The torus window is a cursor over the board, with each item
   containining a cell and all its immediate neighbors."
  [coll]
  (partition 3 1 (concat [(last coll)] coll [(first coll)])))

(defn step
  "Advance the automation by one step, updating all cells."
  [board]
  (doall
   (map (fn [window]
          (apply #(apply map brians-brain-rules %&)
                 (doall (map torus-window window))))
        (torus-window board))))

(defn update-board
  "Update the automaton"
  [board-ref]
  (swap! board-ref step))

(def state->color {:on java.awt.Color/WHITE
                   :off java.awt.Color/BLACK
                   :dying java.awt.Color/GRAY})

(defn render-cell [g cell]
  (let [[state x y] cell
        [x-scale y-scale] dim-scale
        x  (inc (* x x-scale))
        y  (inc (* y y-scale))]
    (doto g
      (.setColor (state->color state))
      (.fillRect x y (dec x-scale) (dec y-scale)))))

(defn render [graphics img board]
  (let [background-graphics (.getGraphics img)]
    (doto background-graphics
      (.setColor java.awt.Color/BLACK)
      (.fillRect 0 0 (dim-screen 0) (dim-screen 1)))
    (doseq [row (with-coords board)
            cell row]
      (when-not (#{:off} (cell 0))
        (render-cell background-graphics cell)))
    (.drawImage graphics img 0 0 nil)))

(defn activity-loop [panel board]
  (while
      @board
    (update-board board)
    (.repaint panel)))

(defn launch-1 []
  (let [[screen-x screen-y] dim-screen
        board (atom (new-board))
        frame (javax.swing.JFrame.)
        panel (proxy [javax.swing.JPanel] [])]
    (doto frame
      (.add panel)
      (.pack)
      (.setSize screen-x screen-y)
      (.show)
      (.setDefaultCloseOperation javax.swing.JFrame/DISPOSE_ON_CLOSE))
    board))

(defn launch-2 []
  (let [[screen-x screen-y] dim-screen
        board (atom (new-board))
        frame (javax.swing.JFrame.)
        img   (java.awt.image.BufferedImage. screen-x screen-y java.awt.image.BufferedImage/TYPE_INT_ARGB)
        panel (proxy [javax.swing.JPanel] []
                (paint [g] (render g img @board)))]
    (doto frame
      (.add panel)
      (.pack)
      (.setSize screen-x screen-y)
      (.show)
      (.setDefaultCloseOperation javax.swing.JFrame/DISPOSE_ON_CLOSE))
    board))

(defn launch []
  (let [[screen-x screen-y] dim-screen
        board (atom (new-board))
        frame (javax.swing.JFrame.)
        img   (java.awt.image.BufferedImage. screen-x screen-y java.awt.image.BufferedImage/TYPE_INT_ARGB)
        panel (proxy [javax.swing.JPanel] []
                (paint [g] (render g img @board)))]
    (doto frame
      (.add panel)
      (.pack)
      (.setSize screen-x screen-y)
      (.show)
      (.setDefaultCloseOperation javax.swing.JFrame/DISPOSE_ON_CLOSE))
    (future (activity-loop panel board))
    board))


