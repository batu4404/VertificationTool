package core.utils;

public class InfixToPrefix {
	public static void main(String[] args) {
		String input = "-1+2*4/5-7+3/6";
        input = "a*-a+ (2*3-1)";
        input = "-10 + (x - 100) * (y < x)";
        input = "n * (n + 1) / 2";
        String output;
        InfixToPostfix theTrans = new InfixToPostfix(input);
        output = theTrans.doTrans(); 
        System.out.println("Postfix is " + output + '\n');
        
        String[] elementMath = output.split(" ");
       
        Helper.reverse(elementMath);
        for (String s: elementMath) {
        	System.out.print("  " + s);
        }
        
        System.out.println("  ");
        
        String prefix = "";
        
        for(int i = elementMath.length - 2; i >= 0; i--) {
			if( InfixToPostfix.isOperator(elementMath[i].charAt(0)) ) {
				
				String temp = "(" + elementMath[i] + " " + elementMath[i+1] + " " + elementMath[i+2] + ")";
				elementMath[i] = temp;
				for (int j = i+1; j < elementMath.length-3; j++) {
					elementMath[j] = elementMath[j+2];
				}
			}
		}
        
        System.out.println("test: " + elementMath[0]);
	}

}
