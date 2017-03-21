package app;

import java.io.IOException;
import java.util.List;

import core.utils.Helper;
import core.utils.InfixToPostfix;
import core.utils.InfixToPrefix;

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
		
		this.input = input;
		
		reverseInfix();
		System.out.println("input: " + input);
		System.out.println("input: " + this.input);
		
        InfixToPostfix theTrans = new InfixToPostfix(this.input);
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
	
	// đảo ngược thứ tự các các phép toán và toán hạng trong biểu thức input
	private void reverseInfix() {
		int length = input.length();
		int i = 0;
		char ch;
		String inputReverse = "";
		String operand;	
		while (i < length) {
			ch = input.charAt(i);
			operand = "";
			if (InfixToPostfix.isCharactorOfOperand(ch)) {
				operand += ch;
				i++;
				while (i < length && InfixToPostfix.isCharactorOfOperand(input.charAt(i))) {
					ch = input.charAt(i);
					operand += ch;	
					i++;
				} 
				System.out.println("operand: " + operand);
				inputReverse = operand + inputReverse;
				i--;
			}
			else if (ch == ')') {
				inputReverse = '(' + inputReverse;
			}
			else if (ch == '(') {
				inputReverse = ')' + inputReverse;
			}
			else if (ch == ' ') {
				// do nothing
			}
			else {
				inputReverse = ch + inputReverse;
			}
			
			i++;
		}
		
		input = inputReverse;
	}
	
	public static void main(String[] args) throws IOException {
        String input = "-1+2*4/5-7+3/6";
        input = "a*-a+ (2*3-1)";
        input = "(x - 100) * (y < x)";
        input = "return = 0";
        String output;
        UserAssertion theTrans = new UserAssertion(input);
        output = theTrans.createUserAssertion(input); 
        System.out.println("prefix is " + output + '\n');
    }
	
}
