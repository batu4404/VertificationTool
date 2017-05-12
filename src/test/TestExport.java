package test;

import core.verification.report.DefineFun;
import core.verification.report.Report;
import core.verification.report.VerificationReport;

public class TestExport {
	public static void main(String[] args) {
		VerificationReport report = Report.getToTest();
		
		report.print();
		
		report.getStatus();		
		report.getParameters();
		report.getReturn();
		report.getErrors();
		report.getGenerateConstraintTime();
		report.getSolverTime();
	}
}
