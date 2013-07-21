(ns mandel.test.core
  (:use [mandel.core])
  (:use [clojure.test]))

(def c-test {:R 1.0 :C 1.0})

(deftest test-abs
	(is (= (abs c-test) (. Math sqrt 2))))

(deftest test-add
	(is (= (add c-test c-test) {:R 2.0 :C 2.0})))

(deftest test-square
	(is (= (square c-test) {:R 0.0 :C 2.0})))

(deftest test-breaks-mandel
	(is (false? (breaks-mandel {:R 0.1, :C 0.1} 100)))
	(is (breaks-mandel c-test 5)))

(deftest test-map-to-plane
	(is (= start (map-to-plane 0 0))))