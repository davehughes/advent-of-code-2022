(ns advent.day2
  (:require [clojure.string]))

(defn read-input []
  (->> "inputs/day2.txt"
    (slurp)
    (clojure.string/split-lines)
    (map #(clojure.string/split % #" " 2))
    ))


(defn translate-move [m]
  (case m
    "A" :rock
    "B" :paper
    "C" :scissors
    "X" :rock
    "Y" :paper
    "Z" :scissors
    ))

(defn translate-outcome [m]
  (case m
    "X" :loss
    "Y" :draw
    "Z" :win
    ))

(defn translate-frame [frame]
  [(translate-move (first frame)) (translate-move (second frame))]
  )

(defn translate-frame-2 [frame]
  [(translate-move (first frame)) (translate-outcome (second frame))]
  )

(defn score-move [m]
  (case m
    :rock 1
    :paper 2
    :scissors 3
    ))

(defn score-outcome [o]
  (case o
    :win 6
    :draw 3
    :loss 0
    ))

(defn frame-outcome [move-player move-opponent]
  (if (= move-player move-opponent)
    :draw
    (case [move-player move-opponent]
      [:rock :paper] :loss
      [:paper :rock] :win
      [:rock :scissors] :win
      [:scissors :rock] :loss
      [:paper :scissors] :loss
      [:scissors :paper] :win
      )))

(defn move-for-outcome [move-opponent outcome]
  (case outcome
    :draw move-opponent
    :loss (case move-opponent
            :rock :scissors
            :scissors :paper
            :paper :rock
            )
    :win (case move-opponent
           :rock :paper
           :paper :scissors
           :scissors :rock
           )
    ))

(defn score-frame [move-player move-opponent]
  (+ (score-move move-player) (score-outcome (frame-outcome move-player move-opponent)))
  )

(defn score-frame-2 [move-opponent outcome]
  (let [move-player (move-for-outcome move-opponent outcome)]
    (score-frame move-player move-opponent) 
    ))

(defn solution1 []
  (->> (read-input)
   (map translate-frame)
   (map #(score-frame (second %) (first %)))
   (apply +)
   ))

(defn solution2 []
  (->> (read-input)
   (map translate-frame-2)
   (map #(apply score-frame-2 %))
   (apply +)
   ) 
  )
