(declare-fun x_0 () Real)
(declare-fun return () Real)
(assert (= return (+ (+ (- 1 (/ (* x_0 x_0) 2)) (/ (* (* (* x_0 x_0) x_0) x_0) 24)) (/ (* (* (* (* (* x_0 x_0) x_0) x_0) x_0) x_0) 720))))
(assert (not (= (= return 0) return)))
(check-sat)
(get-model)