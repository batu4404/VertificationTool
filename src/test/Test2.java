package test;

public class Test2
{
	// pre-condition IN > -1.57079632679f && IN < 1.57079632679f
	// assert: (result <= 0.99 && result >= -0.99)	// sin 1
	public static float sin(float IN) {
		
		float x = IN;
		float result = x - (x*x*x)/6 + (x*x*x*x*x)/120 + (x*x*x*x*x*x*x)/5040;
		
		return result;
	}
	
	public static void main() {
		float IN = -1.5f;
		float result = sin(IN);
		System.out.println("result" + result);
	}
}
