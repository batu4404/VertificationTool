package core.utils;

import core.cfg.declaration.CFGNode;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtVariableReference;

public class Converttion {
	
	public static String prefix(CFGNode begin, CFGNode end) {
		
		String constraint = "";
		String temp;
		constraint = begin.getPrefixConstraint();
		begin = begin.getNext();
//		if (begin == null) {
//			return
//		}
		while(begin == null || begin != end) {
			temp = begin.getPrefixConstraint();
			constraint = wrapInfix(constraint, "&", temp);
		}
		
		return constraint;
	}
	
	public static String prefix(CtElement element) {
		
		if (element instanceof CtBinaryOperator) {
			return prefixBinaryOperator((CtBinaryOperator) element);
		}
		else if (element instanceof CtAssignment) {
			return prefixAssignment((CtAssignment) element);
		}
		else if (element instanceof CtLocalVariable) {
			return prefixLocalVariable((CtLocalVariable) element);
		}
		else if (element instanceof CtVariableAccess) {
			return element.toString();
		}
		else if (element instanceof CtLiteral) {
			return element.toString();
		}
		else if (element instanceof CtUnaryOperator) {
			CtUnaryOperator unaryOp = (CtUnaryOperator) element;
			unaryOp.getOperand();
			unaryOp.getKind();
			return null;
		}
		else {
			return null;
		}
	}
	
	private static String prefixBinaryOperator(CtBinaryOperator binOp) {
		CtExpression left = binOp.getLeftHandOperand();
		CtExpression right = binOp.getRightHandOperand();
		BinaryOperatorKind operator = binOp.getKind();
		
		String leftStr = prefix(left);
		String rightStr = prefix(right);
		String operatorStr = SpoonHelper.getStringBinaryOperationKind(operator);
		
		return wrapPrefix(leftStr, rightStr, operatorStr);
	}
	
	private static String prefixAssignment(CtAssignment ass) {
		
		CtExpression assignment = ass.getAssignment();	
		
		String assignedStr = ass.getAssigned().toString();
		String assignmentStr = prefix(assignment);
		
		return wrapPrefix(assignedStr, assignmentStr, "=");
	}
	
	private static String prefixLocalVariable(CtLocalVariable localVar) {
		return localVar.toString();
	}
	
	public static String wrapPrefix(String left, String right, String operand) {
		return "(" + operand + " " + left + " " + right + ")";
	}
	
	public static String wrapInfix(String left, String right, String operand) {
		return "(" + left + " " + operand + " " + right + ")";
	}

}
