package core.cfg.declaration;

import core.utils.VariableManager;

public abstract class CFGNode {
	
	CFGNode next;
	
	public CFGNode() {}
	
	/**
	 * 
	 * @param next
	 */
	public void setNext(CFGNode next) {
		this.next = next;
	}

	public CFGNode getNext() {;
		return next;
	}
	
	public String getConstraint() {
		return "n/a";
	}
	
	public void index(VariableManager vm) {};
}
