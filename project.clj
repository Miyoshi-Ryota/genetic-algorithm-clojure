(defproject genetic-algorithm "0.1.1"
  :description "Clojure's implementation of genetic algorithm.
  see also https://github.com/Miyoshi-Ryota/genetic-algorithm-clojure"
  :url "https://github.com/Miyoshi-Ryota/genetic-algorithm-clojure"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/mit-license.php"}
  :repositories [["clojars" {:url "https://clojars.org/repo" :sign-releases false}]]
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :repl-options {:init-ns genetic-algorithm.core})
