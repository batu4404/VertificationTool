package testbenchmark;

import java.io.File;

import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;

import core.verification.FileVerification;

public class TestBenchmark {
	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("Nothing to do");
			System.exit(1);
		}
		
		File file = new File(args[0]);
		FileVerification fv = new FileVerification();
		fv.verifyDirectory(file);
	}
}
