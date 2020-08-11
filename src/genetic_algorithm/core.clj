(ns genetic-algorithm.core)

(defn window-function [x bound]
  "xがbound以上なら1に，bound以下なら0にする"
  (if (> x bound) 1 0))

(defn initialize [population-length solution-length p]
  (let [initialize-one-solution (fn [] (mapv #(window-function % (- 1 p)) (repeatedly solution-length rand)))]
    (repeatedly population-length initialize-one-solution)))

(defn crossover [sol1 sol2]
  (let [crossover-one-gene (fn [int1 int2] (if (> (rand) 0.5) int1 int2))]
    (mapv crossover-one-gene sol1 sol2)))

(defn mutation [sol1]
  (let [target-index (rand-int (count sol1))
        reverse-value (if (= (nth sol1 target-index) 1) 0 1)]
    (assoc sol1 target-index reverse-value)))

(defn select-parents [population]
  "ToDo popが完全に収束した後は，無限ループしてしまう．"
  (let [parent1 (rand-nth population)]
    (loop [parent2 (rand-nth population)]
      (if (not= parent1 parent2) (list parent1 parent2)
                                 (recur (rand-nth population))))))

(defn generate-offspring [population lambda]
  (repeatedly lambda #(mutation (apply crossover (select-parents population)))))

(defn select-next-generation-population [now-population generate-offspring eval-function]
  (take (count now-population) (sort-by eval-function (concat now-population generate-offspring))))

(defn ga [mu lambda solution-length p eval-function max-generation]
  "ToDo 途中経過を出力するようにする．"
  (loop [population (initialize mu solution-length p)
         g 1]
    (if (= g max-generation) population
                             (recur (select-next-generation-population population (generate-offspring population lambda) eval-function)
                                    (inc g)))))
