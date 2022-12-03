(ns advent.day3
  (:require [clojure.string]
            [clojure.set]))

(defn read-input []
  (->> "inputs/day3.txt"
    (slurp)
    (clojure.string/split-lines)
    ))

(def item-priorities
  (let [alphabet "abcdefghijklmnopqrstuvwxyz"
        items (str alphabet (clojure.string/upper-case alphabet))
        ]
    (->> items
        (map-indexed #(vector %2 (+ 1 %1)))
        (flatten)
        (apply hash-map)
        )))

(defn item-priority [item]
  (item-priorities item))

(defn split-compartments [s]
  (let [size (/ (count s) 2)]
    [(take size s) (drop size s)]
    ))

(defn find-common-item [cs]
  (->> cs (map set) (apply clojure.set/intersection) (first)))

(defn solution1 []
  (->> (read-input)
    (map split-compartments)
    (map find-common-item)
    (map item-priority)
    (apply +)
    ))

(defn solution2 []
  (->> (read-input)
    (partition 3)
    (map find-common-item)
    (map item-priority)
    (apply +)
    ))
