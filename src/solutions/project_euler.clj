(ns
    ^{:author "Stu Halloway"
       :doc "Some Project Euler solutions. See projecteuler.net."}
  solutions.project-euler
  (:use clojure.contrib.lazy-seqs)
  (:require [clojure.string :as str]))

(defn divides?
  "Does divisor divide dividend evenly?"
  [dividend divisor]
  (zero? (rem dividend divisor)))

(defn divides-any
  "Return a predicate that tests whether its arg can be 
   evenly divided by any of nums."
  [& nums]
  (fn [arg]
    (boolean (some #(divides? arg %) nums))))

(defn problem-1-recur
  "Sum the numbers divisible by 3 or 5, from 0 to upper."
  ([]
     (problem-1-recur 1000))
  ([upper]
     (let [divisible? (divides-any 3 5)]
       (loop [sum 0 n 1]
         (if (>= n upper)
           sum
           (recur
            (if (divisible? n) (+ sum n) sum)
            (inc n)))))))

(defn problem-1
  "Sum the numbers divisible by 3 or 5, from 0 to upper."
  ([] (problem-1 1000))
  ([upper]
     (apply
      +
      (filter
       (divides-any 3 5)
       (range upper)))))

(defn problem-1-left-to-right
  "Sum the numbers divisible by 3 or 5, from 0 to upper."
  ([] (problem-1-left-to-right 1000))
  ([upper]
     (->> (range upper)
          (filter (divides-any 3 5))
          (apply +))))

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

(defn problem-5
  "2520 is the smallest number that can be divided by each of the numbers
   from 1 to 10 without any remainder.

   What is the smallest number that is evenly divisible by all of the numbers
   from 1 to 20?"
  ([] (problem-5 20))
  ([upper]
     (first (let [divisors (range 2 (inc upper))]
              (filter
               (fn [n] (every? #(zero? (rem n %)) divisors))
               (iterate inc 2))))))

(defn unique-factors
  "Return unique factors up to max, removing factors that
   are subsumed by larger factors (e.g. if you have 4 you
   don't need 2.)"
  [max]
  (reduce
   (fn [result item]
     (if (some #(divides? % item) result)
       result
       (conj result item)))
   []
   (range max 2 -1)))

(defn square-of-sum
  [x]
  (let [sum (/ (* x (inc x)) 2)]
    (* sum sum)))

(defn sum-of-squares
  [x]
  (apply + (map #(* % %) (range (inc x)))))

(defn problem-6
  "sum of squares minus square of sum"
  [x]
  (- (square-of-sum x) (sum-of-squares x)))

(defn problem-7
  "for this to count, you need to understand how lazy-seq primes works..."
  [x]
  (nth primes (dec x)))
