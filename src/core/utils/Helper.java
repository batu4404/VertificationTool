package core.utils;

import spoon.reflect.code.CtExpression;

public class Helper {
	public String convertToString(CtExpression express) {
		return null;
	}
	
	public static void reverse(String[] arr) {
		int length = arr.length;
		int halfLength = length / 2;
		String temp;
		for (int i = 0; i < halfLength; i++) {
			temp = arr[i];
			arr[i] = arr[length - i -1];
			arr[length - i - 1] = temp;
		}
	}
}
