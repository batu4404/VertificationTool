package core.verification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.cfg.cfgbuilder.CFGBuilder;
import core.cfg.declaration.VtCFG;
import core.solver.SMTInput;
import core.solver.Z3Runner;
import core.verification.report.Report;
import core.verification.report.VerificationReport;
import core.verification.userassertion.UserInput;
import spoon.reflect.declaration.CtMethod;

public class MethodVerification {
	
	CtMethod method;
	String precondition;
	String postcondition;
	int nLoops = 5;
	
	static String SMTINPUT_DIR = "smt/";

	public MethodVerification() {
		
	}
	
	public void setNumberOfLoops(int nLoops) {
		this.nLoops = nLoops;
	}

	
	/**
	 * verify a method with pre-condition and post-condition
	 * @param method: method to verify
	 * @param preCondition
	 * @param postCondition
	 * @return	verification report
	 * @throws IOException 
	 */
	public VerificationReport verify(CtMethod method, String preCondition, String postCondition) 
			throws IOException {
		
		CFGBuilder builder = new CFGBuilder();
		VtCFG cfg = builder.setNumberOfLoop(nLoops).buildCFG(method);
		cfg.index();
		
		SMTInput smtInput = new SMTInput();
		smtInput.setFormula(cfg.getListFomula());
		smtInput.setListVariables(cfg.getVariableManager().getListVariables());
		
		String constraintTemp;

		List<String> constraints = new ArrayList<>();
		UserInput userInput = new UserInput();
		userInput.setParameter(cfg.getParameters());
		
		// add pre-condition
		if (!preCondition.equals("")) {
			constraintTemp = userInput.createUserAssertion(preCondition);
			constraints.add(constraintTemp);
		}
		
		// add user's assertion
		constraintTemp = userInput.createUserAssertion(postCondition);
		constraintTemp = "(not " + constraintTemp + ")";
		constraints.add(constraintTemp);
		
		smtInput.setConstraints(constraints);
		
		String methodName = method.getSimpleName();
		String path = SMTINPUT_DIR + methodName + ".smt";
		FileOutputStream fo = new FileOutputStream(new File(path));
	    smtInput.printInputToOutputStream(fo);
	    
	    List<String> result = Z3Runner.runZ3(path);
	    
	    result.forEach(System.out::println);
	    Report report = new Report();
	    VerificationReport verReport = report.generateReport(result);
	    System.out.println("status: " + verReport.getStatus());
	    System.out.println("time: " + verReport.getSolverTime());
		
		return verReport;
	}
}
