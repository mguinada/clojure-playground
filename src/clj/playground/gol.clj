(ns playground.gol
  (:require [clojure.pprint :refer [pprint]]))

(comment
  "Study of Christophe Grand's implementation of the Conway's Game of Life
  http://clj-me.cgrand.net/2011/08/19/conways-game-of-life")

(def board #{[1 0] [1 1] [1 2]})

(defn neighbours
  "Calculates the neighboring coordinates to a given cell"
  [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1] :when (not= [0 0] [dx dy])]
    [(+ dx x) (+ dy y)]))

(defn step
  "Advance one step in life enforcing that:

   Any live cell with fewer than two live neighbours dies, as if caused by under-population.
   Any live cell with two or three live neighbours lives on to the next generation.
   Any live cell with more than three live neighbours dies, as if by over-population.
   Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
  "
  [cells]
  (set (for [[loc n] (frequencies (mapcat neighbours cells))
             :when (or (= n 3) (and (= n 2) (cells loc)))]
         loc)))

(take 5 (iterate step board))
