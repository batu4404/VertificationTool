package core.cfg.declaration.node;

import core.utils.VariableManager;

public abstract class CFGNode {
	
	CFGNode next;
	CFGNode previous;
	
	public CFGNode() {}
	
	public void setPrevious(CFGNode previous) {
		this.previous = previous;
	}
	
	public CFGNode getPrevious() {;
		return next;
	}
	
	public void setNext(CFGNode next) {
		this.next = next;
		if (next != null) {
			next.setPrevious(this);
		}
	}

	public CFGNode getNext() {;
		return next;
	}
	
	public String getConstraint() {
		return "";
	}
	
	public void index(VariableManager vm) {};
	
	public String getPrefixConstraint() {
		return null;
	}
	
	public String getFormula() {
		return null;
	}
}
