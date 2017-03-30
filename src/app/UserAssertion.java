package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.utils.Helper;
import core.utils.InfixToPrefix;
import core.utils.Variable;

public class UserAssertion {
	String input;
	String assertion;
	List<Variable> parameters;
	String[] mathElements;
	
	public UserAssertion() {
		
	}
	
	public UserAssertion(String input) {
		this.input = input;
	}
	
	public UserAssertion setInput(String input) {
		this.input = input;
		return this;
	}
	
	public String createUserAssertion(String input) {
		
		this.input = input;
		
		preProcess();
		
		String prefix = InfixToPrefix.infixToPrefix(this.input);
		
		
		mathElements = prefix.split(" ");
		
		for (String s: mathElements) {
			System.out.print("-" + s);
		}
		System.out.println(" ");
		
		addIndexForParameter();
		
		addParenthesis();
		
		postReplace();
		
        return assertion;
	}

//	public UserAssertion setParameter(List<String> parameters) {
//	//	this.parameters = parameters;
//		return this;
//	}
	
	public UserAssertion setParameter(List<Variable> parameters) {
		this.parameters = parameters;
		return this;
	}
	
	private void addIndexForParameter() {
		if (mathElements == null || parameters == null)
			return;
		int length = mathElements.length;
		for (int i = 0; i < length; i++) {
			
			for (int j = 0; j < parameters.size(); j++) {
				if (parameters.get(j).getName().equals(mathElements[i]) &&
						!mathElements[i].equalsIgnoreCase("return") ) {
					mathElements[i] = mathElements[i] + "_0";
				}
			}
			
		}
	}
	
	/**
	 * Tiền xử lý input trước khi chuyển đổi
	 * thay thế các phép toán có 2 kí tự
	 */
	private void preProcess() {
	
		input = input.replaceAll(" ", "");
		input = input.replaceAll(">=", "@");
		System.err.println("after >=: " + input);
		input = input.replaceAll("<=", "~");
		System.err.println("after >=: " + input);
	}
	
	/**
	 * xử lý input sau khi chuyển đổi (trả lại tên cho em)
	 * thay thế lại các phép toán đã được thay thê trong preProcess
	 */
	private void postReplace() {
		assertion = assertion.replaceAll("@", ">=")
						.replaceAll("~", "<=")
						.replaceAll("&", "and")
						.replaceAll("!", "not");
	}
	
	private void addParenthesis() {
		boolean isInteger = true;
		String type;
		for(int i = mathElements.length - 2; i >= 0; i--) {
			if( InfixToPrefix.isOperator(mathElements[i].charAt(0)) ) {
				
				if ( isInteger ) {
					type = getType(mathElements[i+1]);
					if (type.equals("double") || type.equals("float")) {
						isInteger = false;
					}
					type = getType(mathElements[i+2]);
					if (type.equals("double") || type.equals("float")) {
						isInteger = false;
					}
				}
				
				if (mathElements[i].equals("/")) {
					if ( isInteger ) {
						mathElements[i] = "div";
					}
				}
				
				String temp = "(" + mathElements[i] + " " + mathElements[i+1] + " " + mathElements[i+2] + ")";
				
				mathElements[i] = temp;
				for (int j = i+1; j < mathElements.length-2; j++) {
					mathElements[j] = mathElements[j+2];
				}
			}
		}
		
		assertion = mathElements[0];
		System.out.println("assertion: " + assertion);
	}
	
	private String getType(String operand) {
		if (parameters == null)
			return "";
		
		String type = "";
		
		for (int i = 0; i < parameters.size(); i++) {
			if ( parameters.get(i).getName().equals(operand) ) {
				type = parameters.get(i).getType();
				break;
			}
		}
		
		return type;
	}
	
	public static void main(String[] args) throws IOException {
        String input = "-1+2*4/5-7+3/6";
        input = "a*-a+ (2*3-1)";
        input = "(x - 100) * (y < x)";
        input = "return <= 0";
        input = "return = n*(n+1) / 2";
     //   input = "return * return > 1";
        input = "(a>= 0)&(a<=1)";
        input = "return * return > (a+b)/2";
        
        List<Variable> parameters = new ArrayList<>();
        parameters.add(new Variable("int", "n"));
        
        if (input.contains("<=")) {
        	System.out.println("contain <= ");
        }
        else {
        	System.out.println("not contain <= ");
        }
//        input = input.replaceAll("<=", "1");
//        System.out.println("input: " + input);
        String output;
        UserAssertion theTrans = new UserAssertion(input);
        theTrans.setParameter(parameters);
        output = theTrans.createUserAssertion(input); 
        System.out.println("prefix is " + output + '\n');
    }
	
}
