(declare-fun x_0 () Real)
(declare-fun return () Real)
(assert (= return (* x_0 x_0)))
(assert (not (> return 0)))
(check-sat)
(get-model)