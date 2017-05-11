package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import core.solver.DefineFun;

public class TestSmtInput {
	public static void main(String[] args) {
		String output =   "(error \"line 11 column 21: unknown constant IN\")"
						+ "(error \"line 11 column 22: unknown constant IN\")"  
						+ "sat"
						+ "(model"
						+ "  (define-fun IN_0 () Real"
						+ "    (- (/ 3.0 2.0)))"
						+ "  (define-fun return () Real"
						+ "    (- (/ 71979.0 71680.0)))"
						+ "  (define-fun result_0 () Real"
						+ "    (- (/ 71979.0 71680.0)))"
						+ "  (define-fun x_0 () Real"
						+ "    (- (/ 3.0 2.0)))"
						+ ")"
						+ "(:eliminated-vars    3"
						+ " :memory             2.70"
						+ " :nlsat-decisions    1"
						+ " :nlsat-propagations 3"
						+ " :nlsat-stages       2"
						+ " :time               0.01"
						+ " :total-time         0.01)";
		
//		timeout
//		(:memory     10.65
//		 :total-time 10.00)

//		tachModel(output);
//		for(String str:arrayString){
//			System.out.println(str);
//		}
/*		
		int i = 0;
		int open;
		int k = 0;
		int length = output.length();
		System.out.println("l: " + length);
		List<String> list = new ArrayList<>();
		String temp;
		i = output.length() - 1;
		while (i >= 0) {
			if (output.charAt(i) == ')') {
				open = i;
				i--;
				while (i >= 0) {
					if (output.charAt(i) == ')') {
						k++;
					}
					else if (output.charAt(i) == '(') {
						if (k == 0) {
							temp = output.substring(i, open+1);
							System.out.println("temp: " + temp);
							list.add(temp);
							output = output.replace(temp, "");
							break;
						}
						else {
							k--;
						}
					}
					
					i--;
				}
			}
			
			i--;
		}
		
		System.out.println("ouput: " + output);
		
*/
		
//		output = "model"
//				+ "  (define-fun IN_0 () Real"
//				+ "    (- (/ 3.0 2.0)))"
//				+ "  (define-fun return () Real"
//				+ "    (- (/ 71979.0 71680.0)))"
//				+ "  (define-fun result_0 () Real"
//				+ "    (- (/ 71979.0 71680.0)))"
//				+ "  (define-fun x_0 () Real"
//				+ "    (- (/ 3.0 2.0)))";
		
		output = output.replaceAll("[ ]+", " ");
		
		List<String> list = parse(output);
		
		List<String> errors = parseErrors(list);
		for (String str: errors) {
			System.out.println("error: " + str);
		}
		
//		for (String str: list) {
//			System.out.println(str);
//			
//			DefineFun defineFun = parseDefineFun(str);
//			System.out.println(defineFun);
//		}
	}
	
 	static List<String> arrayString = new ArrayList<>();
	static void tachModel(String str){
		if(str.contains(")") == false){
			arrayString.add(str);
			return;
		}
		int k = -1;
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) == '('){
				k = 0;
				for(int j = i+1; j < str.length(); j++){
					if(str.charAt(j) == '('){
						k++;
					}
					else if(str.charAt(j) == ')'){
						if(0 != k){
							String temp = str.substring(i, j);
							arrayString.add(temp);
							tachModel(temp);
						}
						else{
							k--;
						}
					}
				}
			}
		}
	}
	
	public static List<String> parse(String str) {
		
		List<String> listStr = new ArrayList<>();
		listStr.add("");
		
		String temp;
		int i = str.length() - 1;
		int k = 0;
		int end;
		while (i >= 0) {
			if (str.charAt(i) == ')') {
				end = i;
				i--;
				while (i >= 0) {
					if (str.charAt(i) == ')') {
						k++;
					}
					else if (str.charAt(i) == '(') {
						if (k == 0) {
							temp = str.substring(i, end+1);
							listStr.add(temp);
							str = str.replace(temp, "");
							break;
						}
						else {
							k--;
						}
					}
					
					i--;
				}
			}
			
			i--;
		}
		
		str = str.trim();
		listStr.set(0, str);
		
		return listStr;
	}
	
	static DefineFun parseDefineFun(String define) {

		List<String> list = new ArrayList<>();
		
		int i = 1;
		int begin = i;
		int k = 0;
		boolean hasParenthesis = false;
		int loop = define.length() - 1;
		while (i < loop) {
			if (define.charAt(i) == ' ' && !hasParenthesis) {
				if (begin != i) {
					list.add(define.substring(begin, i));
				}
		
				i++;
				begin = i;
			}
			else if (define.charAt(i) == '(') {
				if ( hasParenthesis ) {
					k++;
					i++;
				}
				else {
					hasParenthesis = true;
					k = 0;
					i++;
					begin = i;
				}
			}
			else if (define.charAt(i) == ')' && hasParenthesis) {
				if (k == 0) {
					list.add(define.substring(begin, i));
					hasParenthesis = false;
					i++;
					begin = i;
				}
				else {
					i++;
					k--;
				}
			}
			else {
				i++;
			}
		}
		
		DefineFun definefun = null;
		if (list.size() == 5) {
			definefun = new DefineFun(list.get(1), list.get(3), list.get(4));
		}
		
		return definefun;
	}
	
	static List<String> parseErrors(List<String> listStr) {
		List<String> listError = new ArrayList<>();
		System.out.println("hello");
		for (String str: listStr) {
			System.out.println("str: " + str);
			if (str.contains("error")) {
				System.out.println("error");
				int begin = str.indexOf("\"") + 1;
				int end = str.lastIndexOf("\"");
				
				listError.add(str.substring(begin, end));
			}
		}
		
		return listError;
	}
}

class Output {
	Map<String, String> errors;
	List<DefineFun> defineFuns;
	Map<String, String> statistics;
	
	public Output() {

	}
}