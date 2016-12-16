(ns advent-of-code-2016.day1
  (:require [sablono.core :as sab]))

(defn abs [x]
  (cond (< x 0) (- x)
        :else x))

(defn make-path 
  "Takes a stargin position [int int], applies an operator on a component of the position [first, last] an amnt amount of times"
  [strt op cmp amnt]
  (let [cmp-val (cmp strt)]
    (map #() 
         (range amnt))))


(defn calc-end-position
  "Takes the directions 'L1, R5, R3...' and computes the end position in a cartesian system. Returns [xpos, ypos, dir] where dir is the direction we are facing"
  [directs]
  (reduce #(let [[dir & amnt]   %2
                 amnt           (int (apply str amnt))
                 [x y idr poss] %1]
             (def arr-pos (conj poss [x y])) 
             (cond (= idr \N) (if (= dir \L)
                                [(- x amnt) y \W]
                                [(+ x amnt) y \E])
                   (= idr \E) (if (= dir \L)
                                [x (+ y amnt) \N]
                                [x (- y amnt) \S])
                   (= idr \S) (if (= dir \L)
                                [(+ x amnt) y \E]
                                [(- x amnt) y \W])
                   (= idr \W) (if (= dir \L)
                                [x (- y amnt) \S]
                                [x (+ y amnt) \N])))
          [0 0 \N] ; [x y dir arr-of-positions
          directs))

(defn solution-1 [directs]
    "How many blocks away is Easter Bunny HQ"
  (->>  directs
        calc-end-position
        (take 2) ; We only care for the X and Y components of the vector
        (map abs) ; We want to compute the real distance from 0,0
        (apply +))) ; Return the sum of the components

(defn solution-2 [directs]
  (-> directs
      calc-end-position))

(defn get-input []
  "Gets input from input field and pre-process it"
  (clojure.string/split 
   (.-value (.getElementById js/document "inpt"))
   #", "))

(defn- set-result! [val]
  (set! (.-innerHTML (.getElementById js/document "result")) (str "Result => " val)))


(defn display [] 
  (sab/html [:div 
             [:h1 "Solution for day 1"]
             [:input {type "text" id "inpt"}]
             [:div {id "result"}]
             [:div [:a {:href "#"
                        :onClick #(set-result! (solution-1 (get-input)))}
                    "Solve Part 1"]]
             [:div [:a {:href "#"
                        :onClick #(set-result! (solution-2 (get-input)))}
                    "Solve Part 2"]]]))

