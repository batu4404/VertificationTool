package core.cfg.declaration;

import spoon.reflect.code.CtExpression;

/**
 * Node chứa biểu thức điều kiện và 2 nhánh else, then để biểu diễn
 * lệnh if-else, điều kiện trong vòng, lặp,...
 * 
 */
public class ConditionNode extends CFGNode {
	// thuộc tính next kế thừa từ CFGNode trỏ tới nhánh then
	// của biểu thức điều kiện
	private CFGNode elseNode;	// trỏ tới nhánh else
	private CtExpression condition;	// biểu thức điều kiện

	public ConditionNode(CtExpression condition) {
		this.condition = condition;
	}
	
	public CtExpression getCondition() {
		return condition;
	}

	public void setCondition(CtExpression condition) {
		this.condition = condition;
	}

	public CFGNode getElseNode() {
		
		return elseNode;
	}
	
	public void setElseNode(CFGNode elseNode) {
		this.elseNode = elseNode;
	}
	
	public CFGNode getThenNode() {
		return next;
	}
	
	public void setThenNode(CFGNode thenNode) {
		next = thenNode;
	}
	
	public String getConstrait() {
		return condition.toString();
	}
}