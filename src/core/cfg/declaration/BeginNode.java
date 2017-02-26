package core.cfg.declaration;

/**
 * Node bắt đầu của câu lệnh, vòng lặp, hàm
 */
public class BeginNode extends CFGNode {
	/**
	 * con trỏ tới node kết thúc
	 */
	private CFGNode endNode;

	public CFGNode getEndNode() {
		return endNode;
	}

	public void setEndNode(CFGNode endNode) {
		this.endNode = endNode;
	}
} 
