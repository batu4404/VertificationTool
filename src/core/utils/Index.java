package core.utils;

import java.util.List;

import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtVariableReference;

/**
 * @author lenovo
 * lop danh chi so
 */
public class Index {
	
	public static void index(CtElement element, VariableManager vm) {

		if (element instanceof CtBinaryOperator) {
			indexBinaryOperator((CtBinaryOperator) element, vm);
		}
		else if (element instanceof CtAssignment) {
			indexAssignment((CtAssignment) element, vm);
		}
		else if (element instanceof CtLocalVariable) {
			indexLocalVariable((CtLocalVariable) element, vm);
		}
		else if (element instanceof CtVariableAccess) {
//			System.out.println("hello: " + element);
			CtVariableAccess vac = (CtVariableAccess) element;
			CtVariableReference ctVar = vac.getVariable();
			Variable var = vm.getVariableByName(ctVar.getSimpleName());
			ctVar.setSimpleName(var.getVariableWithIndex());
		}
		else if (element instanceof CtUnaryOperator) {
			CtUnaryOperator unaryOp = (CtUnaryOperator) element;
			
			index(unaryOp.getOperand(), vm);
			
			unaryOp.getKind();
		}
	}
	
	private static void indexBinaryOperator(CtBinaryOperator binOp, VariableManager vm) {
		CtExpression left = binOp.getLeftHandOperand();
		CtExpression right = binOp.getRightHandOperand();
		
		index(left, vm);
		index(right, vm);
	}
	
	private static void indexAssignment(CtAssignment ass, VariableManager vm) {
		
		CtExpression assignment = ass.getAssignment();
		index(assignment, vm);
		
		CtVariableAccess assigned = (CtVariableAccess) ass.getAssigned();
		Variable var = vm.getVariableByName(assigned.getVariable().getSimpleName());
		
		if (var == null) {
//			System.out.println("var (assigned) is null: " + var);
			return;
		}
		var.increase();
		assigned.getVariable().setSimpleName(var.getVariableWithIndex());
	
	}
	
	private static void indexLocalVariable(CtLocalVariable localVar, VariableManager vm) {
		CtExpression assignment = localVar.getAssignment();
		index(assignment, vm);
//		System.out.println("name: " + localVar.getSimpleName());
		Variable var = vm.getVariableByName(localVar.getSimpleName());
		
		if (var == null) {
			System.out.println("var is null");
			return;
		}
		
		var.increase();
		localVar.setSimpleName(var.getVariableWithIndex());
	}
	
	
}
