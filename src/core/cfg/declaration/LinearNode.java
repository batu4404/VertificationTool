package core.cfg.declaration;

import spoon.reflect.code.CtStatement;

/**
 * Class khai báo các node chi trỏ tới 1 node tiếp theo
 * chỉ chứa con trỏ next tới node tiếp theo
 * dùng để biểu diễn cho các câu lệnh bình thường trong chương trình
 */
public class LinearNode extends CFGNode {
	/**
	 * con trỏ tới node tiếp theo
	 */
	private CFGNode next;
	private CtStatement statement;
	
	public LinearNode() {}
	
	public LinearNode(CtStatement statement) {
		this.statement = statement;
	}

	public CtStatement getStatement() {
		return statement;
	}	

	public void setStatement(CtStatement statement) {
		this.statement = statement;
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
