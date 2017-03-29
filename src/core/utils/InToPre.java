package core.utils;

import java.util.Arrays;
import java.util.Stack;

public class InToPre {

	static Stack<Character> stack = new Stack<>();
	static int top = -1;
	
	public static char operator[] = {'(', ')', '>', '<', '=', '+', '-', '*', '/' , '@', '?', '&', '~'};
    
    static {
    	Arrays.sort(operator);
    }
	
	public static void main(String[] args) {
	    String infix = "n*(n+1)/2";

		String prefix = infixtoprefix(infix);
	  
		prefix = reverse(prefix);
		
		System.out.println("prefix: " + prefix);
		
		
		String[] elementMath = prefix.split(" ");
	    
		for (String s: elementMath) {
        	System.out.print("  " + s);
        }
		
		System.out.println("");
        
        for(int i = elementMath.length - 2; i >= 0; i--) {
			if( InfixToPostfix.isOperator(elementMath[i].charAt(0)) ) {
				String temp = "(" + elementMath[i] + " " + elementMath[i+1] + " " + elementMath[i+2] + ")";
				elementMath[i] = temp;
				for (int j = i+1; j < elementMath.length-2; j++) {
					elementMath[j] = elementMath[j+2];
				}
			}
		}
        
        System.out.println("prefix: " + elementMath[0]);
	}
	
	
	public static String infixtoprefix(String infix) {
		String prefix = "";
		char symbol;
		stack.push('#');
		infix = reverse(infix);
		System.out.printf("reverse: %s\n", infix);
		for (int i = 0; i < infix.length(); i++) {
			symbol=infix.charAt(i);
			if ( !isOperator(symbol) ) {
				prefix += " " + symbol;
			} else {
				if (symbol==')') {
					stack.push(symbol);
				} else if(symbol == '(') {
					while (stack.peek() != ')') {
						prefix += " " + stack.pop();
					}
					stack.pop();
				} else {
					if (priority(stack.peek()) <= priority(symbol)) {
						stack.push(symbol);
					} else {
						while(priority(stack.peek()) >= priority(symbol)) {
							prefix += " " + stack.pop();
						}
						stack.push(symbol);
					}
				}
			}
		}
		while (stack.peek()!='#') {
			prefix += " " + stack.pop();
		}
		
		return prefix;
	}
	
	/**
	 * reverse a string
	 * @param input
	 * @return reverse of the input
	 */
	public static String reverse(String input) 
	{
		String result = "";
		for (int i = input.length()-1; i >= 0;--i) {
			result += input.charAt(i);
		}
		
		return result;
	}
	
	public int priority1(char c) {	// thiet lap thu tu uu tien
		
		if (c == '+' || c == '-') 
			return 1;
		else if ( c == '*' || c == '/' || c == '%') 
			return 2;
		else if ( c == '>' || c == '<') 
			return -1;
		else if ( c == '&') 
			return -2;
		else 
			return 0;
	}
	
	
	public static int priority(char symbol)
	{
		switch(symbol) {
		case '+':
	    case '-':
	        return 2;
		case '*':
        case '/':
	        return 4;
		case '$':
        case '^':
	        return 6;
		case '#':
        case '(':
        case ')':
        	return 1;
        default:
        	return 0;
		}
	}
	
	public static boolean isOperator(char c) { // kiem tra xem co phai toan tu
		//	char operator[] = {'>', '<', '=', '+', '-', '*', '/', ')', '(' , '@', '?', '&'};
		Arrays.sort(operator);
		if (Arrays.binarySearch(operator, c) > -1)
			return true;
		else 
			return false;
	}

}
