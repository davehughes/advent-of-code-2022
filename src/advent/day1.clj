(ns advent.day1
  (:require [clojure.string :refer [split-lines]]))

(defn read-input []
  (-> "inputs/day1.txt" slurp clojure.string/split-lines))

(defn split-on-empty-lines [lines]
  (->> lines
    (partition-by #(= % ""))
    (keep-indexed #(if (even? %1) %2))
    ))

(defn bundle-size [items]
  (let [int-items (map #(Integer/parseInt %) items)]
    (apply + int-items)
    ))

(defn solution1 []
  (->> (read-input)
    (split-on-empty-lines)
    (map bundle-size)
    (apply max)))

(defn solution2 []
  (->> (read-input)
    (split-on-empty-lines)
    (map bundle-size)
    (sort #(compare %2 %1))
    (take 3)
    (apply +)
    ))
