package test;

import core.verification.report.Report;
import core.verification.report.VerificationReport;

public class TestReport {
	public static void main(String[] args) {
		VerificationReport report = Report.getToTest();
		
		report.print();
	}
}
