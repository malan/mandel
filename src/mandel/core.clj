(ns mandel.core
	(:gen-class)

 	(:import (javax.swing JFrame JLabel)
        (java.awt.image BufferedImage)
        (java.awt Dimension Color)))

(def canvas-width 400)
(def canvas-height 400)
(def start {:R -2.1 :C -1.4})
(def width 3.0)
(def height 3.0)
(def max-iterations 100)

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

; Renders a mandel set in ASCII characters			
(defn draw-ascii-mandel-map [mandel-map]
	(doseq [[x y iter] mandel-map]
			(if iter (print "*") (print " "))
			(if (= (inc x) canvas-width) (println))))

; Maps a mandel iteration to a cool colour
(defn map-colour [iter]
	(if iter 
		(Color. (int (* (. Math sin (/ iter max-iterations)) 255)) 0 0)
		(Color. 0 0 0)))

; Draws the point of a mandel set on a Image Java Swing object
(defn draw-mandel-points[mandel-map graphics]
	(doseq [[x y iter] mandel-map]
		(.setColor graphics (map-colour iter))
		(.drawLine graphics x y x y)))

; Draws a mandel set on a Swing GUI
(defn draw-mandel-map [mandel-map]
	(let [	image (BufferedImage. canvas-width canvas-height BufferedImage/TYPE_INT_RGB)
			graphics (.createGraphics image)
			canvas (proxy [JLabel] []
                 (paint [g]  
                   (.drawImage g image 0 0 this)))]

		(draw-mandel-points mandel-map graphics)

		(doto (JFrame.)
		      (.add canvas)
		      (.setSize (Dimension. canvas-width canvas-height))
		      (.show))))

(defn -main [& args]
	(println "Drawing factal")
  	(time (draw-mandel-map (generate-mandel-map))))
