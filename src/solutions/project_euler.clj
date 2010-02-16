(ns solutions.project-euler
  (:use clojure.contrib.lazy-seqs)
  (:require [clojure.contrib.str-utils2 :as str]))

(defn problem-1
  "Sum the numbers divisible by 3 or 5, from 0 to upper."
  ([] (problem-1 1000))
  ([upper]
     (apply
      +
      (filter
       (fn [n] (some #(zero? (rem n %)) [3 5]))
       (range upper)))))

(defn fibos
  []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

(defn problem-2
  "Sum the even-valued Fibonaccis up to upper"
  ([] (problem-2 (* 4 1000 1000)))
  ([upper]
     (->> (fibos)
          (take-while #(< % upper))
          (filter even?)
          (apply +))))

(defn prime-factors
  "Returns the prime factors from smallest to largest"
  [n]
  (loop [factors []
         n n]
    (if (< n 2)
      factors
      (let [next-factor (first (filter #(zero? (rem n %)) primes))]
        (recur (conj factors next-factor) (quot n next-factor))))))

(defn problem-3
  "Greatest prime factor"
  ([] (problem-3 600851475143))
  ([n] (last (prime-factors n))))

(defn problem-4
  "Largest palindrome from product of two numbers"
  ([] (problem-4 1000))
  ([upper]
     (apply max
      (for [x (range upper) y (range x upper) :when (let [s (str (* x y))]
                                                    (= s (str/reverse s)))]
        (* x y)))))
