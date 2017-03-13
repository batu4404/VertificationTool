package test;

import java.io.File;
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
		
		CFGBuilder builder = new CFGBuilder();
		VtCFG cfg = builder.setNumberOfLoop(5).buildCFG(method);
		cfg.printPrefix();
	}
}
