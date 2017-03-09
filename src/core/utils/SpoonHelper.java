package core.utils;

import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;

/**
 * @author lenovo
 * Trợ giúp các công việc liên quan đến việc thao tác với các
 * thành phần trong spoon
 */
public class SpoonHelper {
	public static BinaryOperatorKind 
					unaryKindToBinaryKind(UnaryOperatorKind unaryKind) 
	{
		
		if (unaryKind == UnaryOperatorKind.POSTDEC
				|| unaryKind == UnaryOperatorKind.PREDEC) {
			return BinaryOperatorKind.MINUS;
		}
		else if (unaryKind == UnaryOperatorKind.POSTINC
				|| unaryKind == UnaryOperatorKind.PREINC) {
			return BinaryOperatorKind.PLUS;
		}
		else {
			return null;
		}
	}
}
