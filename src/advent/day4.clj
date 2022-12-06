(ns advent.day4
  (:require [clojure.string]))


(defn parse-ranges [s]
  (let [element-strings (clojure.string/split s #"[-,]")
        elements (map  #(Integer/parseInt %) element-strings) 
        ]
    [
     {:start (nth elements 0) :end (nth elements 1)}
     {:start (nth elements 2) :end (nth elements 3)}
    ]
    ))

(defn read-input []
  (let [lines (-> "inputs/day4.txt"
                (slurp)
                (clojure.string/split-lines))
        ranges (map parse-ranges lines)
        ]
    (map parse-ranges lines)
    ))

(defn range-fully-contains? [r1 r2]
  (and
    (<=  (r1 :start) (r2 :start))
    (>=  (r1 :end) (r2 :end))))

(defn ranges-overlap? [r1 r2]
  (if (> (r1 :start) (r2 :start))
    (ranges-overlap? r2 r1)
    (>= (r1 :end) (r2 :start))
    ))

(defn solution1 []
  (->> (read-input)
    (filter (fn [[r1 r2]]
              (or
                (range-fully-contains? r1 r2)
                (range-fully-contains? r2 r1))))

    (count)
    ))

(defn solution2 []
  (->> (read-input)
    (filter (fn [[r1 r2]] (ranges-overlap? r1 r2)))
    (count)
    ))
