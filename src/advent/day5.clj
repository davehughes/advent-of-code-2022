(ns advent.day5
  (:require [clojure.string]))

(defn create-stacks [labels]
  (apply hash-map (apply concat (for [label labels] [label (list)]))))

(defn stacks-push [stacks to item]
  (update stacks to #(conj % item)))

(defn stacks-pop [stacks from]
  (let [value (first (stacks from))
        stacks' (update stacks from #(rest %))
        ]
    [value stacks']
    ))

(defn stacks-move-one [stacks from to]
  (let [[value stacks'] (stacks-pop stacks from)
        stacks'' (stacks-push stacks' to value)
        ]
    stacks''
    ))

(defn stacks-move-n [stacks from to n]
  (if (<= n 0)
    stacks
    (stacks-move-n (stacks-move-one stacks from to) from to (dec n))
    ))

(defn stacks-move-n-stacked [stacks from to n]
  (loop [stacks' stacks
        n' n
        tempstack '()
        ]
    ;; (println "-----")
    ;; (println "stacks: " stacks')
    ;; (println "n: " n')
    ;; (println "tempstack: " tempstack)
    (if (<= n' 0)
      (if (empty? tempstack)
        stacks'
        (recur
          (stacks-push stacks' to (first tempstack))
          n'
          (rest tempstack)
          ))
      (let [[item stacks''] (stacks-pop stacks' from)]
         (recur stacks'' (dec n') (conj tempstack item))))
    ))

(defn stacks-apply-moves [stacks moves move-fn]
  (loop [stacks' stacks
         moves' moves]
    (println "---------")
    (println "stacks: " stacks')
    (println "move: " (first moves'))
    (if (empty? moves')
      stacks'
      (let [{:keys [count from to]} (first moves')]
        (recur (move-fn stacks' from to count) (rest moves')))
      )))

(defn read-move [line]
  (let [matcher (re-matcher #"move (\d+) from (\d+) to (\d+)" line)
        [_ count from to] (re-find matcher)
        ]
    {
      :count      (Integer/parseInt count)
      :from from
      :to   to
    }
    ))

(defn read-stacks [lines]
  (let [lines-inverted (reverse lines)
        header-line (first lines-inverted)
        header-labels (map #(clojure.string/trim (apply str %)) (partition-all 4 header-line))
        item-lines (rest lines-inverted)
        read-space #(if (= "" (clojure.string/trim %)) nil (second %))
        read-line-indexed (fn [line]
                            (->> line
                              (partition-all 4)
                              (map #(apply str %))
                              (map read-space)
                              (map vector header-labels)
                              ))
        items (->> item-lines 
                (map read-line-indexed)
                (apply concat)
                (filter #(not= nil (second %)))
                )
        empty-stacks (create-stacks header-labels)
        stacks (reduce (fn [stacks [to item]] (stacks-push stacks to item)) empty-stacks items)
        ]
    {
      :labels header-labels
      :item-lines item-lines
      :items items
      :stacks stacks
    }
    ))

(defn read-top-stack-elements [labels stacks]
  (for [label labels] (first (stacks label))))

(defn read-input []
  (let [lines (-> "inputs/day5.txt"
                (slurp)
                (clojure.string/split-lines))
        [stack-lines _ move-lines] (partition-by #(= "" %) lines)
        moves (map read-move move-lines)
        {:keys [stacks labels]} (read-stacks stack-lines)
        ]
    {
     :moves moves
     :stacks stacks
     :labels labels
     }
    ))


(defn solution1 []
  (let [{:keys [moves stacks labels]} (read-input)
        completed-stacks (stacks-apply-moves stacks moves stacks-move-n)
        ]
    (apply str (read-top-stack-elements labels completed-stacks))
    ))

(defn solution2 []
  (let [{:keys [moves stacks labels]} (read-input)
        completed-stacks (stacks-apply-moves stacks moves stacks-move-n-stacked)
        ]
    (apply str (read-top-stack-elements labels completed-stacks))
    ))
