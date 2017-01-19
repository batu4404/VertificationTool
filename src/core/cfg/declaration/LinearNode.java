package core.cfg.declaration;

/**
 * Class khai báo các node chi trỏ tới 1 node tiếp theo
 * chỉ chứa con trỏ next tới node tiếp theo
 */
public abstract class LinearNode extends CFGNode {
	/**
	 * con trỏ tới node tiếp theo
	 */
	private CFGNode next;

	public CFGNode getNext() {
		return next;
	}

	public void setNext(CFGNode next) {
		this.next = next;
	}
	
}
