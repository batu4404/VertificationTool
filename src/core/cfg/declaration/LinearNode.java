package core.cfg.declaration;

import spoon.reflect.code.CtExpression;

/**
 * Class khai báo các node chi trỏ tới 1 node tiếp theo
 * chỉ chứa con trỏ next tới node tiếp theo
 * dùng để biểu diễn cho các câu lệnh bình thường trong chương trình
 */
public abstract class LinearNode extends CFGNode {
	/**
	 * con trỏ tới node tiếp theo
	 */
	private CFGNode next;
	private CtExpression expression;

	public CtExpression getExpression() {
		return expression;
	}	

	public void setExpression(CtExpression expression) {
		this.expression = expression;
	}

	public CFGNode getNext() {
		return next;
	}

	public void setNext(CFGNode next) {
		this.next = next;
	}
	
	/**
	 * Lấy biểu thức dưới dạng chuỗi
	 */
	public String getExpressionString() {
		return null;
	}
}
