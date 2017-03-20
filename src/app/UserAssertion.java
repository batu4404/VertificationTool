package app;

import java.util.List;

import core.utils.Helper;
import core.utils.InfixToPostfix;

public class UserAssertion {
	String input;
	List<String> parameters;
	String[] elementMath;
	
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
        InfixToPostfix theTrans = new InfixToPostfix(input);
		String postfix = theTrans.doTrans(); 
	
        
        elementMath = postfix.split(" ");
       
        Helper.reverse(elementMath);
//        for (String s: elementMath) {
//        	System.out.println("s: " + s);
//        }
        
        addIndexForParameter();
        
        for(int i = elementMath.length - 2; i >= 0; i--) {
			if( InfixToPostfix.isOperator(elementMath[i].charAt(0)) ) {
				String temp = "(" + elementMath[i] + " " + elementMath[i+1] + " " + elementMath[i+2] + ")";
				elementMath[i] = temp;
				for (int j = i+1; j < elementMath.length-3; j++) {
					elementMath[j] = elementMath[j+2];
				}
			}
		}
        
        return elementMath[0];
	}

	public UserAssertion setParameter(List<String> parameters) {
		this.parameters = parameters;
		return this;
	}
	
	private void addIndexForParameter() {
		if (elementMath == null || parameters == null)
			return;
		int length = elementMath.length;
		for (int i = 0; i < length; i++) {
			if (parameters.contains(elementMath[i]) && 
					!elementMath[i].equalsIgnoreCase("return")) {
				elementMath[i] = elementMath[i] + "_0";
			}
		}
	}
	
	
}
