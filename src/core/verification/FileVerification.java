package core.verification;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.LanguageCallback;

import core.spoon.LauncherSpoon;
import core.verification.report.VerificationReport;
import core.verification.userassertion.AssertionMethod;
import spoon.reflect.declaration.CtMethod;

public class FileVerification {
	
	public static final String JAVA_TAG = ".java";
	public static final String PP_FILE_TAG = ".xml";
	
	public FileVerification() {
		
	}
	
	public void verifyDirectory(File directory) {
		
		if (directory == null) {
			return;
		}
		else if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			
			for (File f: files) {
				verifyDirectory(f);
			}
		}
		else {
			verify(directory);
		}
	}
	
	public void verify(File file) {
		if (file == null) {
			System.out.println("file is null");
			return;
		}
		
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
		
		LauncherSpoon launcherSpoon = new LauncherSpoon();
		
		launcherSpoon.addInputResource(file);
		launcherSpoon.buildModel();
		
		List<CtMethod> listMethod = launcherSpoon.getMethods();
		
		MethodVerification mv = new MethodVerification();
		
		System.err.println("parent: " + file.getPath());
		
		
		File PPFile = new File(PPPathFile);
		if (!PPFile.exists()) {
			System.err.println("file is not exist: " + PPPathFile);
			return;
		}
		
		List<AssertionMethod> listAssertion = AssertionMethod.getUserAssertions(PPFile);
		
		VerificationReport report;
		for (AssertionMethod am: listAssertion) {
			System.err.println("hello");
			for (CtMethod method: listMethod) {
				if (method.getSimpleName().equals(am.getMethodName())) {
					try {
						
						report = mv.verify(method, am.getPreCondition(), am.getPostCondition());
						
						report.print();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
