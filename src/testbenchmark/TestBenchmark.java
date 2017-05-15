package testbenchmark;

import java.io.File;
import java.io.IOException;

import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;

import core.verification.ExportExcel;
import core.verification.FileVerification;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TestBenchmark {
	public static void main(String[] args) throws RowsExceededException, WriteException, IOException {
		
		if (args.length < 1) {
			System.out.println("Nothing to do");
			System.exit(1);
		}
		ExportExcel.init();
		File file = new File(args[0]);
		FileVerification fv = new FileVerification();
		fv.verifyDirectory(file);
		ExportExcel.write();
	}
}
