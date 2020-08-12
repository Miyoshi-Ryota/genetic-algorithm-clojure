# genetic-algorithm

A Clojure library designed to run simple-GA.
The library's GA target to minimize objective function which takes binary solution and returns number.
We adapt uniform-crossover and normal mutation.

## Usage
* Add :dependencies in your project.clj file.
```clojure
:dependencies [[org.clojure/clojure "1.10.1"]
               [genetic-algorithm "0.1.1"]]
```
* Add :require in your clj file to import library.
```
(:require [genetic-algorithm.core])
```

* Sample usage
```clojure
(ns your-project.core
  (:require [genetic-algorithm.core]))

; mu is number of solution in population.
(def mu 10)
; lambda is number of children in a generation.
(def lambda 20)
; dimension is dimension of solution.
(def dimension 5)
; percent of '1' in initial solution.
(def p 0.5)

; Objective function. 
; The function have to takes a binary collection like a
; [1 0 0 1 0] then returns a number like a 0.34 or 2.
(defn eval-function1 [solution]
  (apply + solution))

(def max-generations 100)

(def mutation-rate 0.05)

(def result
  (genetic-algorithm.core/run mu lambda dimension p eval-function1 max-generations mutation-rate))

(println result)
=> [0 0 0 0 0]
```

## License
MIT License

Copyright 2020 Ryota Miyoshi

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

