(declare-fun n_0 () Int)
(declare-fun fac_0 () Int)
(declare-fun fac_1 () Int)
(declare-fun fac_2 () Int)
(declare-fun fac_3 () Int)
(declare-fun i_0 () Int)
(declare-fun i_1 () Int)
(declare-fun i_2 () Int)
(declare-fun i_3 () Int)
(declare-fun return () Int)
(assert (= fac_0 1))
(assert (= i_0 1))
(assert (and (=> (<= i_0 n_0) (and (and (= fac_1 (* fac_0 i_0)) (= i_1 (+ i_0 1))) (and (=> (<= i_1 n_0) (and (and (= fac_2 (* fac_1 i_1)) (= i_2 (+ i_1 1))) (and (=> (<= i_2 n_0) (and (= fac_3 (* fac_2 i_2)) (= i_3 (+ i_2 1)))) (=> (not (<= i_2 n_0)) (and (= fac_3 fac_2) (= i_3 i_2)))))) (=> (not (<= i_1 n_0)) (and (= fac_3 fac_1) (= i_3 i_1)))))) (=> (not (<= i_0 n_0)) (and (= fac_3 fac_0) (= i_3 i_0)))))
(assert (= return fac_3))
(assert (not (= return 0)))
(check-sat)
(get-model)