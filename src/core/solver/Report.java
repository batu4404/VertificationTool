package core.solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.verification.VerificationReport;

public class Report {
	public VerificationReport generateReport(List<String> result) {
		Iterator<String> iter = result.iterator();
		List<String> listError = new ArrayList<>();
		
		String str;
		String status = null;
		float time = 0;
		
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
		
		return report;
	}
	
	public static String parseError(String error) {
		int begin = error.indexOf("\"") + 1;
		int end = error.lastIndexOf("\"");
		
		return error.substring(begin, end);
	}
}
