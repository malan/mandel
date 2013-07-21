(ns mandel.core
	(:gen-class))

(defn -main [& args]
  (println "Hello Mandel!"))

(def c1 {:R 1.0 :C 0.9})

(use '[clojure.string :only (join)])

(defn print-complex [{r :R c :C}]
	(println r "+" c "i"))

; Calculate the absolute value of a complex number. Given z = r + ci, |z| = sqrt(r^2 + c^2)
(defn abs [{r :R c :C}]
	(. Math sqrt (+ (* r r) (* c c))))

; Adds two complex numbers
(defn add [z1 z2]
	{:R (+ (z1 :R) (z2 :R)) :C (+ (z1 :C) (z2 :C)) })

; Squares a complex number. z^2 = (r^2 - c^2) + 2rci
(defn square [{r :R c :C}]
	{:R (- (* r r) (* c c)) :C (* 2 r c) })

; Calculate if C breaks out of Mandel set, false if it doesn't within max-iterations
(defn breaks-mandel [c max-iterations])


(abs c1)