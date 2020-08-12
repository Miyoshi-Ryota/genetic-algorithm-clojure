(ns genetic-algorithm.core
  (:require [clojure.spec.alpha :as s]))

(s/def ::binary (s/or :one (partial = 1)
                      :zero (partial = 0)))

(s/def ::solution (s/coll-of ::binary))

(s/def ::population (s/coll-of ::solution))

(s/def ::percent (s/and (partial <= 0.0)
                        (partial >= 1.0)))

(defn- window-function
  "If `x` is less than `bound`, return value is 0.
  If `x` is more then `bound`, return value is 1."
  [x bound]
  {:pre [(s/valid? number? x)
         (s/valid? number? bound)]
   :post [(s/valid? ::binary %)]}
  (if (> x bound) 1 0))

(defn- initialize [population-length solution-length p]
  {:pre [(s/valid? int? population-length)
         (s/valid? int? solution-length)
         (s/valid? ::percent p)]
   :post [(s/valid? ::population %)]}
  (let [initialize-one-solution (fn [] (mapv #(window-function % (- 1 p)) (repeatedly solution-length rand)))]
    (repeatedly population-length initialize-one-solution)))

(defn- uniform-crossover
  [sol1 sol2]
  {:pre [(s/valid? ::solution sol1)
         (s/valid? ::solution sol2)]
   :post [(s/valid? ::solution %)]}
  (let [crossover-one-gene (fn [int1 int2] (if (> (rand) 0.5) int1 int2))]
    (mapv crossover-one-gene sol1 sol2)))

(defn- mutate [sol1 mutate-rate]
  {:pre  [(s/valid? ::solution sol1)
          (s/valid? ::percent mutate-rate)]
   :post [(s/valid? ::solution %)]}
  (if (>= mutate-rate (rand))
    (let [target-index (rand-int (count sol1))
          reverse-value (if (= (nth sol1 target-index) 1) 0 1)]
      (assoc sol1 target-index reverse-value))
    sol1))

(defn- select-parents [population]
  {:pre [(s/valid? ::population population)]
   :post [(s/valid? (s/coll-of ::solution) %)]}
  (let [parent1 (rand-nth population)
        parent2 (rand-nth population)]
    (list parent1 parent2)))

(defn- generate-offspring [population lambda mutate-rate]
  {:pre [(s/valid? ::population population)
         (s/valid? int? lambda)
         (s/valid? ::percent mutate-rate)]
   :post [(s/valid? ::population %)]}
  (let [generate-child #(mutate
                          (apply uniform-crossover (select-parents population))
                          mutate-rate)]
    (repeatedly lambda generate-child)))

(defn- select-next-generation-population
  "ToDo: Add spec to evaluate-function"
  [now-population offspring evaluate-function]
  {:pre [(s/valid? ::population now-population)
         (s/valid? ::population offspring)]
   :post [s/valid? ::population %]}
  (->> (concat now-population offspring)
       (sort-by evaluate-function)
       (take (count now-population))))

(defn- select-best-solution
  [population evaluate-function]
  {:pre [(s/valid? ::population population)]
   :post [s/valid? ::solution %]}
  (->> population
       (sort-by evaluate-function)
       (first)))

(defn run
  "Run simple-GA. The implementation adapts uniform-crossover and normal mutation.
  `mu` is number of solutions in population.
  `lambda` is number of children generated in one generation.
  `solution-length` is dimension of solution.
  `evaluate-function` is objective function which take a solution which is binary like a [1 0 1 0 0 0 1].
  `max-generation` is number of generation.
  `mutate-rate` is mutate-rate"
  [mu lambda solution-length p evaluate-function max-generation mutate-rate]
  (loop [population (initialize mu solution-length p)
         g 1]
    (if (= g max-generation)
      (select-best-solution population evaluate-function)
      (recur
        (select-next-generation-population
          population
          (generate-offspring population lambda mutate-rate)
          evaluate-function)
        (inc g)))))
