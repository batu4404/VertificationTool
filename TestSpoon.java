
public class TestSpoon
{
	
	public static int sum(int n) {
		int sum = 0;
		
		for (int i = 0; i <= n; i++) {
			sum = sum + i;
		}
		
		return sum;
	}

	public int max (int a, int b) {
		int max;
		if (a > b) {
			max = a;
		}
		else {
			max = b;
		}
		
		return max;
	}
	
	public int sqr (int a) {
		return a * a;
	}
	
	public static double foo(double a, double b) {
		double result;
		double c = 3 / (b - 3);
		if (a > b) {
			result = a / (b - 2);
			a += 1;
		} else {
			result = a + b;
			a += 1;
		}
			
		return result;
	}
	
	
	public static double abs(double x) {
		double abs = x;
		if (x < 0) {
			abs = -x;
		}
		
		return abs;
	}

}
