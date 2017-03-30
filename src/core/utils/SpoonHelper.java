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
	
	public static String getStringBinaryOperationKind(BinaryOperatorKind operator) {
		String opStr = "";
		if(operator == BinaryOperatorKind.PLUS)
			opStr = "+";
		else if(operator == BinaryOperatorKind.MINUS)
			opStr = "-";
		else if(operator == BinaryOperatorKind.DIV)
			opStr = "/";
		else if(operator == BinaryOperatorKind.MUL)
			opStr = "*";
		else if(operator == BinaryOperatorKind.LT)
			opStr = "<";
		else if(operator == BinaryOperatorKind.LE)
			opStr = "<=";
		else if(operator == BinaryOperatorKind.GT)
			opStr = ">";
		else if(operator == BinaryOperatorKind.GE)
			opStr = ">=";
		else if(operator == BinaryOperatorKind.EQ)
			opStr = "=";
		else if(operator == BinaryOperatorKind.AND)
			opStr = "and";
		else if(operator == BinaryOperatorKind.OR)
			opStr = "or";
		else if(operator == BinaryOperatorKind.NE)
			opStr = "distinct";
		else 
			System.out.println("operator: " + operator);
		
		return opStr;
	}
	
	public static String getStringUnaryOperationKind(UnaryOperatorKind operator) {
		String opStr = "";
		if(operator == UnaryOperatorKind.NEG )
			opStr = "-";
		else if(operator == UnaryOperatorKind.NOT)
			opStr = "not";
		else if(operator == UnaryOperatorKind.POS)
			opStr = "+";
		
		return opStr;
	}
}
