package app;

import core.utils.Helper;
import core.utils.InfixToPostfix;

public class UserAssertion {
	String input;
	
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
	
        
        String[] elementMath = postfix.split(" ");
       
        Helper.reverse(elementMath);
        for (String s: elementMath) {
        	System.out.println("s: " + s);
        }
        
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
}
