package core.cfg.declaration;

import spoon.reflect.code.CtExpression;

/**
 * Node chứa biểu thức điều kiện và 2 nhánh else, then để biểu diễn
 * lệnh if-else, điều kiện trong vòng, lặp,...
 */
public class ConditionNode extends CFGNode {
	private CFGNode elseNode;
	private CFGNode thenNode;
	private CtExpression expression;	// biểu thức điều kiện

	
	public CtExpression getExpression() {
		return expression;
	}

	public void setExpression(CtExpression expression) {
		this.expression = expression;
	}

	public CFGNode getElseNode() {
		
		return elseNode;
	}
	
	public void setElseNode(CFGNode elseNode) {
		this.elseNode = elseNode;
	}
	
	public CFGNode getThenNode() {
		return thenNode;
	}
	
	public void setThenNode(CFGNode thenNode) {
		this.thenNode = thenNode;
	}
}
