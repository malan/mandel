(ns mandel.core
	(:gen-class)

 	(:import (javax.swing JFrame JLabel)
        (java.awt.image BufferedImage)
        (java.awt Dimension Color)))


(def canvas-width 105)
(def canvas-height 75)
(def start {:R -2.1 :C -1.4})
(def width 3.0)
(def height 3.0)
(def max-iterations 100)

(defn -main [& args]
  (println "Hello Mandel!"))

; Calculate the absolute value of a complex number. Given z = r + ci, |z| = sqrt(r^2 + c^2)
(defn abs [{r :R c :C}]
	(. Math sqrt (+ (* r r) (* c c))))

; Adds two complex numbers
(defn add [z1 z2]
	{:R (+ (z1 :R) (z2 :R)) :C (+ (z1 :C) (z2 :C)) })

; Squares a complex number. z^2 = (r^2 - c^2) + 2rci
(defn square [{r :R c :C}]
	{:R (- (* r r) (* c c)) :C (* 2 r c) })

; Calculates how many iterations C takes to break out of Mandel set, false if it doesn't within max-iterations
(defn breaks-mandel [c max-iterations]
	(loop [z c, iteration 0]
		(if (= iteration max-iterations)
			false
			(if (< (abs z) 2)
				(recur (add (square z) c) (inc iteration))
				iteration))))

; Maps a canvas coordinate to a point on a complex plan
(defn map-to-plane [x y]
	;Real part = Start.Real + (width)*x/canvas-width
	{:R (+ (start :R) (* (/ x canvas-width) width))
	 :C (+ (start :C) (* (/ y canvas-height) height))})

; Calculates the mandel mapping to canvas coordinates as such [x y #iterations-to-breakout/false]
(defn generate-mandel-map []
	(for [y (range canvas-height)
			x (range canvas-width)]
		[x y (breaks-mandel (map-to-plane x y) max-iterations)]))
			
(defn draw-ascii-mandel-map [mandel-map]
	(doseq [[x y iter] mandel-map]
			(if iter (print "*") (print " "))
			(if (= (inc x) canvas-width) (println))))
			
(draw-ascii-mandel-map (generate-mandel-map))

