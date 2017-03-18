package core.cfg.declaration.node;

import core.utils.FormulaCreater;
import core.utils.Index;
import core.utils.VariableManager;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtReturn;

public class ReturnNode extends CFGNode {
	CtExpression returnedExpression;
	
	public static String RETURN_TAG = "return";
	
	public ReturnNode() {
		
	}
	
	public ReturnNode(CtReturn ctReturn) {
		returnedExpression = ctReturn.getReturnedExpression();
//		System.out.println("returnedExpression: " + returnedExpression.getClass());
	}
	
	public CtExpression getExpression() {
		return returnedExpression;
	}
	
	public void index(VariableManager vm) {
		Index.index(returnedExpression, vm);
	}
	
	@Override
	public String getConstraint() {
		String constraint = RETURN_TAG + " = " + returnedExpression.toString();
		return constraint;
	}
	
	@Override
	public String getPrefixConstraint() {
		String returnedExpressionPrefix = FormulaCreater.createFormula(returnedExpression);
		return FormulaCreater.wrapPrefix(FormulaCreater.EQUALITY, RETURN_TAG, returnedExpressionPrefix);
	}
	
	@Override
	public String getFormula() {
		String returnedExpressionPrefix = FormulaCreater.createFormula(returnedExpression);
		return FormulaCreater.wrapPrefix(FormulaCreater.EQUALITY, RETURN_TAG, returnedExpressionPrefix);
	}
}
