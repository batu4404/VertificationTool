package core.cfg.declaration.node;

import core.utils.FormulaCreater;
import core.utils.Index;
import core.utils.SpoonHelper;
import core.utils.VariableManager;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtVariableReference;

/**
 * Class khai báo các node chi trỏ tới 1 node tiếp theo
 * chỉ chứa con trỏ next tới node tiếp theo
 * dùng để biểu diễn cho các câu lệnh bình thường trong chương trình
 */
public class LinearNode extends CFGNode {
	/**
	 * con trỏ tới node tiếp theo
	 */
	private CtStatement statement;
	
	public LinearNode() {

	}
	
	public LinearNode(CtStatement statement) {
		this.statement = statement.clone();
		properStatement();
	}

	public CtStatement getStatement() {
		return statement;
	}	

	public void setStatement(CtStatement statement) {
		this.statement = statement;
		properStatement();
	}
	
	/**
	 * Lấy biểu thức dưới dạng chuỗi
	 */
	public String getExpressionString() {
		return null;
	}
	
	//sau khi unfold cfg 
	public String getConstraint() {
		if (statement == null)
			return null;
		else
			return statement.toString(); //  lam sau
		
	}
	
	public void index(VariableManager vm) {
		Index.index(statement, vm);
	}
	
	// chuẩn hóa biểu thức
	private void properStatement() {
		CoreFactory coreFactory = statement.getFactory().Core();
		
		if (statement instanceof CtUnaryOperator) {
			CtUnaryOperator unaryOp = (CtUnaryOperator) statement;
			CtVariableAccess operand = (CtVariableAccess) unaryOp.getOperand();
			UnaryOperatorKind unaryKind = unaryOp.getKind();
			BinaryOperatorKind binaryKind = SpoonHelper.unaryKindToBinaryKind(unaryKind); 

			CtVariableReference var = (CtVariableReference) operand.getVariable();
			
			CtLiteral rightHand = coreFactory.createLiteral().setValue(1);
		
			CtExpression assigned = coreFactory.createVariableWrite().setVariable(var.clone());
			CtExpression leftHand = coreFactory.createVariableRead().setVariable(var.clone());
			CtBinaryOperator assignment = coreFactory.createBinaryOperator()
													.setLeftHandOperand(leftHand)
													.setRightHandOperand(rightHand)
													.setKind(binaryKind);
			
			statement = (CtAssignment) coreFactory.createAssignment()
						.setAssigned(assigned)
						.setAssignment(assignment);			
		}
		else if (statement instanceof CtLocalVariable) {
			CtLocalVariable lc = (CtLocalVariable) statement;
			CtExpression assignment = lc.getAssignment();
			
			
			if (assignment == null) {
				statement = null;
				return;
			}
			
			CtVariableReference variableReference = lc.getReference();
			
			CtVariableWrite variableWrite = coreFactory.createVariableWrite()
													.setVariable(variableReference);
			
			statement = (CtAssignment) coreFactory.createAssignment()
										.setAssigned(variableWrite)
										.setAssignment(assignment);
			
		}
	}
	
	@Override
	public String getPrefixConstraint() {
		return FormulaCreater.createFormula(statement);
	}
	
	@Override
	public String getFormula() {
		return FormulaCreater.createFormula(statement);
	}
	
}
