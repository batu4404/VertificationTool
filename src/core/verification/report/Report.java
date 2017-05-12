package core.verification.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Report {
	public VerificationReport generateReport(List<String> result) {
		Iterator<String> iter = result.iterator();
		List<String> listError = new ArrayList<>();
		
		String str;
		String status = null;
		float time = 0;
		String model = "";
		
		while (iter.hasNext()) {
			str = iter.next();
			
			if (!str.contains("(") && !str.contains(")") && status == null) {
				status = str;
			}
			else if (str.contains("(error")) {
				listError.add(parseError(str));
			}
			else if (str.contains("(model")) {
				while (iter.hasNext()) {
					str = iter.next();
					if ( str.equals(")") ) {
						break;
					}
					else {
						model += str;
					}
				}
			}
			else if (str.contains(":total-time")) {
				String[] temp = str.split("[ ]+|[)]");
				time = Float.parseFloat(temp[temp.length-1]);
			}
		}
		
		VerificationReport report = new VerificationReport();
		report.setErrors(listError);
		report.setStatus(status);
		report.setSolverTime(time);
		
		List<DefineFun> parameters = new ArrayList<>();
		parameters.add(new DefineFun("x", "int", "1"));
		report.setParameters(parameters);
		report.setReturn(new DefineFun("return", "int", "1"));
		
		return report;
	}
	
	public static String parseError(String error) {
		int begin = error.indexOf("\"") + 1;
		int end = error.lastIndexOf("\"");
		
		return error.substring(begin, end);
	}
	
	public static VerificationReport getToTest() {
		List<String> list = new ArrayList<>();
		list.add("(error \"line 11 column 21: unknown constant IN\")");
		list.add("(error \"line 11 column 22: unknown constant IN\")");  
		list.add("sat");
		list.add("(model");
		list.add("  (define-fun IN_0 () Real");
		list.add("    (- (/ 3.0 2.0)))");
		list.add("  (define-fun return () Real");
		list.add("    (- (/ 71979.0 71680.0)))");
		list.add("  (define-fun result_0 () Real");
		list.add("    (- (/ 71979.0 71680.0)))");
		list.add("  (define-fun x_0 () Real");
		list.add("    (- (/ 3.0 2.0)))");
		list.add(")");
		list.add("(:eliminated-vars    3");
		list.add(" :memory             2.70");
		list.add(" :nlsat-decisions    1");
		list.add(" :nlsat-propagations 3");
		list.add(" :nlsat-stages       2");
		list.add(" :time               0.01");
		list.add(" :total-time         0.01)");
		
		Report report = new Report();
		VerificationReport verificationReport= report.generateReport(list);
		verificationReport.print();
		
		return verificationReport;
	}
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		list.add("(error \"line 11 column 21: unknown constant IN\")");
		list.add("(error \"line 11 column 22: unknown constant IN\")");  
		list.add("sat");
		list.add("(model");
		list.add("  (define-fun IN_0 () Real");
		list.add("    (- (/ 3.0 2.0)))");
		list.add("  (define-fun return () Real");
		list.add("    (- (/ 71979.0 71680.0)))");
		list.add("  (define-fun result_0 () Real");
		list.add("    (- (/ 71979.0 71680.0)))");
		list.add("  (define-fun x_0 () Real");
		list.add("    (- (/ 3.0 2.0)))");
		list.add(")");
		list.add("(:eliminated-vars    3");
		list.add(" :memory             2.70");
		list.add(" :nlsat-decisions    1");
		list.add(" :nlsat-propagations 3");
		list.add(" :nlsat-stages       2");
		list.add(" :time               0.01");
		list.add(" :total-time         0.01)");
		
		Report report = new Report();
		VerificationReport verificationReport= report.generateReport(list);
		verificationReport.print();
	}
}
