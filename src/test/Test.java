package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import core.cfg.cfgbuilder.CFGBuilder;
import core.cfg.declaration.VtCFG;
import core.utils.LauncherSpoon;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtMethod;

/**
 * Test các module của chương trình
 * test phần sinh ast từ spoon
 */
public class Test {
	public static void main(String[] args) {
		LauncherSpoon launcher = new LauncherSpoon();
		launcher.addInputResource("TestSpoon.java");
		launcher.buildModel();

		List<CtMethod> methodList = launcher.getMethods();

		CtMethod method = methodList.get(0);		
		int pos = method.getPosition().getLine();
		System.out.println("pos: " + pos);
		System.out.println(method);
		
		
		int nLoops = 3;
		
		CFGBuilder builder = new CFGBuilder();
		VtCFG cfg = builder.setNumberOfLoop(nLoops).buildCFG(method);
		cfg.printPrefix();
		
		cfg.index();
		PrintStream printStream;
		try {
			printStream = new PrintStream(new File("metaSMT.txt"));
			cfg.printMetaSMT(printStream);
			printStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		cfg.printPrefix();
		
		System.out.println("fomula");
	//	cfg.getFormula();
		cfg.printFormula();
		
		File smtFile = new File("smt.txt");
		try {
			PrintStream ps = new PrintStream(smtFile);
			cfg.printSMTFormual(ps);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
