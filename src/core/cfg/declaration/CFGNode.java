package core.cfg.declaration;


public abstract class CFGNode {
	
	CFGNode next;
	
	public CFGNode() {}
	
	/**
	 * 
	 * @param next
	 */
	public void setNext(CFGNode next) {
		
	}

	public CFGNode getNext() {;
		return next;
	}
}
