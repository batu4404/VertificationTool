package core.cfg.declaration;

import spoon.reflect.code.CtExpression;

public class AssignNode extends LinearNode {
	private CtExpression expression;

	public CtExpression getExpression() {
		return expression;
	}

	public void setExpression(CtExpression expression) {
		this.expression = expression;
	}
}
