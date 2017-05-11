package test;

import java.io.File;

import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;

import core.verification.FileVerification;

public class TestVerification {
	public static void main(String[] args) {
		FileVerification fv = new FileVerification();
		fv.verify(new File("TestSpoon.java"));
		
//		File file = new File("TestSpoon.java");
//		
//		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
//		String name = FilenameUtils.getName(file.getAbsolutePath());
//		name = FilenameUtils.getBaseName(file.getAbsolutePath());
//		System.out.println("extension: " + extension);
//		System.out.println("name: " + name);
	}
}
