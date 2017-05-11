package test;

public class TestFloatCDFPL
{
	// pre-condition IN > -1.57079632679f && IN < 1.57079632679f
	// assert: (result <= 0.99 && result >= -0.99)	// sin 1 - false
	// assert: (result <= 1.0 && result >= -1.0) 	// sin 2 - false
	// assert: (result <= 1.001 && result >= -1.001) 	// sin 3 - false
	// assert: (result <= 1.01 && result >= -1.01) 	// sin 4 - true
	// assert: (result <= 1.1 && result >= -1.1) 	// sin 5 - true
	// assert: (result <= 1.2 && result >= -1.2) 	// sin 6 - true
	// assert: (result <= 1.0 && result >= -1.0) 	// sin 7 - true
	// assert: (result <= 1.0 && result >= -1.0) 	// sin 8 - true
	public static float sin(float IN) {
		
		float x = IN;
		float result = x - (x*x*x)/6 + (x*x*x*x*x)/120 + (x*x*x*x*x*x*x)/5040;
		
		return result;
	}
	
	
	// pre-condition: IN >= 0.0f && IN < 1.0f
	// assert: (result >= 0 && result < 1.39)			// square 1 - false
	// assert: (result >= 0 && result < 1.398)			// square 2 - false
	// assert: (result >= 0 && result < 1.39843)		// square 3 - false
	// assert: (result >= 0 && result < 1.39844)		// square 4 - true
	// assert: (result >= 0 && result < 1.3985)		// square 4 - true
	public static double square(float IN) {
		float x = IN;

		return 1 + 0.5*x - 0.125*x*x + 0.0625*x*x*x - 0.0390625*x*x*x*x;
	}
	
	public static void main() {
		float IN = -1.5f;
		float result = sin(IN);
		System.out.println("result" + result);
	}
}
