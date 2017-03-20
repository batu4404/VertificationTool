package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import core.cfg.cfgbuilder.CFGBuilder;
import core.cfg.declaration.VtCFG;
import core.solver.SMTInput;
import core.solver.Z3Runner;
import core.utils.LauncherSpoon;
import core.utils.Variable;
import spoon.compiler.ModelBuildingException;
import spoon.compiler.SpoonCompiler;
import spoon.compiler.SpoonResource;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.FactoryImpl;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.DefaultCoreFactory;
import spoon.support.StandardEnvironment;
import spoon.support.compiler.FileSystemFile;
import spoon.support.compiler.FileSystemFolder;
import spoon.support.compiler.jdt.JDTBasedSpoonCompiler;

public class Core {
	public Core() {
		smtInput = new SMTInput();
		cfgBuilder = new CFGBuilder();
		userAssertion = new UserAssertion();
	}
	
	public Core(String pathFile)
			throws ModelBuildingException, FileNotFoundException {
		this();
		
		this.pathFile = pathFile;
		
		create();
	}
	
	public String[] getMethodSignatures() {
		return methodSignatures;
	}
	
	private void create() 
			throws ModelBuildingException, FileNotFoundException {
		
		launcher = new LauncherSpoon();
		launcher.addInputResource(pathFile);
		launcher.buildModel();
		
		List<CtMethod> methodList = launcher.getMethods();
		if (methodList == null) {
			System.out.println("null");
		}
		
		int nMethods = methodList.size();
		methodSignatures = new String[nMethods];
		methodCFGList = new ArrayList<>();
	
		SMTInput smtInput = new SMTInput();
		for(int i = 0; i < nMethods; i++) {
			methodSignatures[i] = methodList.get(i).getSignature();
			methodCFGList.add( cfgBuilder.buildCFG(methodList.get(i)).index() );
		}
	}
	
	public List<String> runSolver(String methodSignature,List<String> conditions) 
				throws Exception {


		int index = find(methodSignatures, methodSignature);
		
		VtCFG mf = methodCFGList.get(index);
		
		// Sinh file meta
		PrintStream printStream;
		try {
			printStream = new PrintStream(new File("metaSMT.txt"));
			mf.printMetaSMT(printStream);
			printStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		String fileDir = "input.smt";
	    FileOutputStream fo = new FileOutputStream(fileDir);
	    if (mf == null) {
	    	System.out.println(mf);
	    	System.exit(-1);
	    }
	    if (smtInput == null) {
	    	System.out.println("smtInput is null");
	    	System.exit(-1);
	    }
	    
	    smtInput.setFormula(mf.getListFomula());
		smtInput.setListVariables(mf.getVariableManager().getListVariables());
		
		String constraintTemp;
		for(int i = 0; i < conditions.size(); i++) {
			constraintTemp = userAssertion.setParameter(mf.getParameters())
								.createUserAssertion(conditions.get(i));
			constraintTemp = "(not " + constraintTemp + ")";
			conditions.set(i, constraintTemp);
		}
		
		List<String> constraints = new ArrayList<>();
		constraints.addAll(conditions);
		smtInput.setConstraints(constraints);
	    smtInput.printInputToOutputStream(fo);
	 //   List<String> result = Z3Runner.runZ3(fileDir);
	    result = Z3Runner.runZ3(fileDir);
	    result.forEach(System.out::println);
	    
	    List<String> result1 = new ArrayList<String>();
	    List<Variable> parameters = mf.getVariableManager().getListVariables();
	    result1.add(result.get(0));
	    for (Variable v: parameters) {
	    	String varName = v.getName() + "_0"; 
	    	for (int i = 1; i < result.size(); i++) {
	   
	    		if (result.get(i).indexOf(varName) >= 0) {
	    			String valueLine = result.get(i+1);
	//    			System.out.println("value: " + valueLine);
	//    			System.out.println("indexof(\"(\"): " + valueLine.indexOf("("));
	    			valueLine = valueLine.replace('(', ' ');
	    			valueLine = valueLine.replace(')', ' ');
	    			valueLine = valueLine.replace(" ", "");
	//    			System.out.println("value: " + valueLine);
	    			result1.add(v.getName() + " = " + valueLine);
	    			break;
	    		}
	    	}
	    }
	    
	    for (int i = 1; i < result.size(); i++) {
	 	   
    		if (result.get(i).indexOf("return") >= 0) {
    			String valueLine = result.get(i+1);
    			System.out.println("value: " + valueLine);
    			System.out.println("indexof(\"(\"): " + valueLine.indexOf("("));
    			valueLine = valueLine.replace('(', ' ');
    			valueLine = valueLine.replace(')', ' ');
    			valueLine = valueLine.replace(" ", "");
    			System.out.println("value: " + valueLine);
    			result1.add("return = " + valueLine);
    			break;
    		}
    	}
	    
	    return result1;
	}
	
	public List<String> getSolverLog() {
		return result;
	}
	
	private int find(String[] arr, String value) {
		if (arr == null || value == null) 
			return -1;
		
		for (int i = 0; i < arr.length; i++) {
			if (value.equals(arr[i]))
				return i;
		}
		
		return -1;
	}
	
	LauncherSpoon launcher;
	CFGBuilder cfgBuilder;
	
	private String pathFile;
	private List<VtCFG> methodCFGList;
	private SMTInput smtInput;
	private String[] methodSignatures;
	
	private UserAssertion userAssertion;
	
	List<String> result;
}
