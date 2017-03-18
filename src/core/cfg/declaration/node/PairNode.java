package core.cfg.declaration.node;


/**
 * Chá»©a con trá»� tá»›i Ä‘áº§u vÃ  cuá»‘i cá»§a cfg
 */
public class PairNode  {
	
	private CFGNode mBegin;
	private CFGNode mEnd;
	
	public PairNode() {
	}
	
	public PairNode(CFGNode begin, CFGNode end) {
		mBegin = begin;
		mEnd = end;
	}
	
	public CFGNode getBegin() {
		return mBegin;
	}
	
	public void setBegin(CFGNode begin) {
		mBegin = begin;
	}
	
	public CFGNode getEnd() {
		return mEnd;
	}
	
	public void setEnd(CFGNode end) {
		mEnd = end;
	}
}
