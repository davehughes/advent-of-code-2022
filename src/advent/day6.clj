(ns advent.day6
  (:require [clojure.string])) 

(defn read-input []
  (clojure.string/trim (slurp "inputs/day6.txt")))

(defn generate-sequence-windows
  ([seq size] (generate-sequence-windows seq size size))
  ([seq size idx]
    (if (empty? seq)
      '()
      (let [window (take size seq)]
        ;; (println "window: " window)
        ;; (println "idx: " idx)
        (if (= (count window) size)
          (lazy-seq (cons {:window window :offset idx} (generate-sequence-windows (rest seq) size (inc idx))))
          (lazy-seq (generate-sequence-windows (rest seq) size (inc idx))))))))

(defn detect-marker [windows]
  (let [is-marker? (fn [{:keys [window]}]
                    (= (count (set window))
                       (count window)
                       ))
        ]
    (first (filter is-marker? windows))
    ))

(defn solution1 []
  (-> (read-input) 
    (generate-sequence-windows 4)
    (detect-marker)
    (:offset)
    ))

(defn solution2 []
  (-> (read-input) 
    (generate-sequence-windows 14)
    (detect-marker)
    (:offset)
    ))
