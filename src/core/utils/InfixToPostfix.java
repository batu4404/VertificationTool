package core.utils;

import java.io.IOException;
import java.util.Arrays;

public class InfixToPostfix {
    private Stack theStack;
    private String input;
    private String output = "";
    
    
    public static char operator[] = {'>', '<', '=', '+', '-', '*', '/' , '@', '?', '&'};
    
    public InfixToPostfix(String in) {
        input = in;
        int stackSize = input.length();
        theStack = new Stack(stackSize);
    }
    
    public String doTrans() {
    	String operand;
    	char ch;
    	int length = input.length();
    	
        for (int j = 0; j < length; j++) {
            ch = input.charAt(j);
            if (ch == ' ') {
            	continue;	// nếu ch là dấu cách thì bỏ qua
            }   	
            else if (isOperator(ch)) {
            	gotOper(ch, priority(ch));
            }
            else if (ch == '(') {
                theStack.push(ch);

            }
            else if (ch == ')') { 
                gotParen(ch); 
            }
            else {
            	operand = "" + ch;
            	j++;
            	while (j < length && isCharactorOfOperand(input.charAt(j)) ) {
            		ch = input.charAt(j);
            		operand += ch;
            		j++;
            	}
            	output = output  + " " + operand; 
            	j--;
            }
        }
        System.out.println("output: " + output);
        while (!theStack.isEmpty()) {
            output = output + " " + theStack.pop();
        }
        System.out.println(output);
        return output; 
    }
    
    public static boolean isOperator(char c) { // kiem tra xem co phai toan tu
	//	char operator[] = {'>', '<', '=', '+', '-', '*', '/', ')', '(' , '@', '?', '&'};
		Arrays.sort(operator);
		if (Arrays.binarySearch(operator, c) > -1)
			return true;
		else 
			return false;
	}
    
    public static boolean isCharactorOfOperand(char c) { // kiem tra xem co phai toan tu
    	//	char operator[] = {'>', '<', '=', '+', '-', '*', '/', ')', '(' , '@', '?', '&'};
    		Arrays.sort(operator);
    		if (c != ')' && c != ')' && c != ' ' && Arrays.binarySearch(operator, c) < 0)
    			return true;
    		else 
    			return false;
    	}
    
    public int priority(char c) {	// thiet lap thu tu uu tien
	
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
    
    
    public void gotOper(char opThis, int prec1) {
        while (!theStack.isEmpty()) {
        	
            char opTop = theStack.pop();
            if (opTop == '(') {
                theStack.push(opTop);
                break;
            } else {
                int prec2;
                prec2 = priority(opTop);
                
                if (prec2 < prec1) { 
                    theStack.push(opTop);
                    break;
                } 
                else output = output + " " + opTop;
            }
        }
        theStack.push(opThis);
    }
    
    public void gotParen(char ch) { 
        while (!theStack.isEmpty()) {
            char chx = theStack.pop();
            
            if (chx == '(') 
            	break; 
            else 
            	output = output + " " + chx; 
        }
    }
    public static void main(String[] args) throws IOException {
        String input = "-1+2*4/5-7+3/6";
        input = "a*-a+ (2*3-1)";
        input = "(x - 100) * (y < x)";
        String output;
        InfixToPostfix theTrans = new InfixToPostfix(input);
        output = theTrans.doTrans(); 
        System.out.println("Postfix is " + output + '\n');
    }
    class Stack {
        private int maxSize;
        private char[] stackArray;
        private int top;
        
        public Stack(int max) {
            maxSize = max;
            stackArray = new char[maxSize];
            top = -1;
        }
        public void push(char j) {
            stackArray[++top] = j;
        }
        public char pop() {
            return stackArray[top--];
        }
        public char peek() {
            return stackArray[top];
        }
        public boolean isEmpty() {
            return (top == -1);
        }
    }
}
