package core.utils;

import core.cfg.declaration.node.CFGNode;
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

public class FormulaCreater {
	
	public static String LOGIC_AND = "and";
	public static String LOGIC_OR = "or";
	public static String NEGATIVE = "not";
	public static String BINARY_CONNECTIVE = "=>";
	public static String EQUALITY = "=";
	
	public static String createFormula(CFGNode begin, CFGNode end) {
		
		if (begin == null) {
			return null;
		}
		
		
		String constraint;
		String temp;
		constraint = begin.getPrefixConstraint();
		begin = begin.getNext();
		
		CFGNode node = begin;

		while(node != null && node != end) {
			temp = node.getFormula();
	
			if (temp != null) {
				if (constraint == null) {
					constraint = temp;
				}
				else {
//					constraint = wrapInfix(LOGIC_AND, constraint, temp);
					constraint = wrapInfix(LOGIC_AND, temp, constraint);
				}
			}
			node = node.getNext();
		}
			
		return constraint;
	}
	
	public static String createFormula(CtElement element) {
		
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
			String operand = createFormula(unaryOp.getOperand());
			String kind = SpoonHelper.getStringUnaryOperationKind(unaryOp.getKind());
			
			String op = "(" + kind + " " + operand + ")";
			
			System.err.println("op: " + op);
		//	System.err.println("unary: " + unaryOp.getKind());
			
			return op;
		}
		else {
			return null;
		}
	}
	
	private static String prefixBinaryOperator(CtBinaryOperator binOp) {
		CtExpression left = binOp.getLeftHandOperand();
		CtExpression right = binOp.getRightHandOperand();
		BinaryOperatorKind operator = binOp.getKind();
		
		String leftStr = createFormula(left);
		String rightStr = createFormula(right);
		String operatorStr = SpoonHelper.getStringBinaryOperationKind(operator);
		
		return wrapPrefix(operatorStr, leftStr, rightStr);
	}
	
	private static String prefixAssignment(CtAssignment ass) {
		
		CtExpression assignment = ass.getAssignment();	
		
		String assignedStr = ass.getAssigned().toString();
		String assignmentStr = createFormula(assignment);
		
		return wrapPrefix(EQUALITY, assignedStr, assignmentStr);
	}
	
	private static String prefixLocalVariable(CtLocalVariable localVar) {
		return localVar.toString();
	}
	
	public static String wrapPrefix(String operand, String left, String right) {
		return "(" + operand + " " + left + " " + right + ")";
	}
	
	public static String wrapInfix(String left, String right, String operand) {
		return "(" + left + " " + operand + " " + right + ")";
	}

	public static String createFormula(String operator, String operand) {
		return "(" + operator + " " + operand + ")";
	}

}
