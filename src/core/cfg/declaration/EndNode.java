package core.cfg.declaration;

public class EndNode extends CFGNode {
	private CFGNode next;

	public CFGNode getNext() {
		return next;
	}

	public void setNext(CFGNode next) {
		this.next = next;
	}
}
