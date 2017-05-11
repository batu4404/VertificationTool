package test;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

public class TestZ3 {
	
	static String smtEncoding = "(declare-fun IN_0 () Real)"	
								+ "(declare-fun f_IN_0 () Real)"
								+ "(declare-fun fp_IN_0 () Real)"
								+ "(declare-fun x_0 () Real)"
								+ "(declare-fun return () Real)"
								+ "(assert (= f_IN_0 (+ (+ (- IN_0 (/ (* (* IN_0 IN_0) IN_0) 6)) (/ (* (* (* (* IN_0 IN_0) IN_0) IN_0) IN_0) 120)) (/ (* (* (* (* (* (* IN_0 IN_0) IN_0) IN_0) IN_0) IN_0) IN_0) 5040))))"
								+ "(assert (= fp_IN_0 (+ (+ (- 1 (/ (* IN_0 IN_0) 2)) (/ (* (* (* IN_0 IN_0) IN_0) IN_0) 24)) (/ (* (* (* (* (* IN_0 IN_0) IN_0) IN_0) IN_0) IN_0) 720))))"
								+ "(assert (= x_0 (- IN_0 (/ f_IN_0 fp_IN_0))))"
								+ "(assert (= return x_0))"
								+ "(assert (and (> IN_0 (- 0 0.2)) (< IN_0 0.2)))"
								+ "(assert (not (< return 0.1)))"
								+ "(check-sat)"
								+ "(get-model)";
	
	public static void main(String[] args) {
		
		try {
			Map<String, String> config = new HashMap<>();
			config.put("model", "true"); 
			config.put("timeout", "10");
			Context ctx = new Context();
			
			Solver solver = ctx.mkSolver();
			
			solver = loadSMTLIBEncoding(config, smtEncoding);
			
			Status status = solver.check(); 
			
			Model model;
			
			if (status == Status.SATISFIABLE) { 
			    model = solver.getModel(); 
			} 
			
		//	Model model = solver.getModel();
			
		//	FuncDecl[] decls = model.getDecls();
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Solver loadSMTLIBEncoding(Map<String, String> config, String smtEncoding) throws Z3Exception { 
		 
		  Context context = new Context(config); 
		  Solver solver = context.mkSolver(); 
		  BoolExpr expr = context.parseSMTLIB2String(smtEncoding, null, null, null, null); 
		  solver.add(expr); 
		 
		  return solver; 
	} 
}