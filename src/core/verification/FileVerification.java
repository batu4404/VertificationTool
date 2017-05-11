package core.verification;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.LanguageCallback;

import core.spoon.LauncherSpoon;
import core.verification.userassertion.AssertionMethod;
import spoon.reflect.declaration.CtMethod;

public class FileVerification {
	
	public static final String JAVA_TAG = ".java";
	public static final String PP_FILE_TAG = ".xml";
	
	File file;
	
	public FileVerification() {
		
	}
	
	public void verify(File file) {
		LauncherSpoon launcherSpoon = new LauncherSpoon();
		
		launcherSpoon.addInputResource(file);
		launcherSpoon.buildModel();
		
		List<CtMethod> listMethod = launcherSpoon.getMethods();
		
		MethodVerification mv = new MethodVerification();
		
		System.err.println("parent: " + file.getPath());
		String javaPathFile = file.getPath();
		String PPPathFile = null;
		int index = javaPathFile.lastIndexOf(JAVA_TAG);
		if (index < 0) {
			return;
		}
		else {
			PPPathFile = javaPathFile.substring(0, index);
			PPPathFile += PP_FILE_TAG;
		}
		
		File PPFile = new File(PPPathFile);
		if (!PPFile.exists()) {
			System.err.println("file is not exist: " + PPPathFile);
			return;
		}
		
		List<AssertionMethod> listAssertion = AssertionMethod.getUserAssertions(PPFile);
		
		for (AssertionMethod am: listAssertion) {
			System.err.println("hello");
			for (CtMethod method: listMethod) {
				if (method.getSimpleName().equals(am.getMethodName())) {
					try {
						mv.verify(method, am.getPreCondition(), am.getPostCondition());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
