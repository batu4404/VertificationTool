package core.cfg.declaration;


/**
 * Chứa con trỏ tới đầu và cuối của cfg
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
		return mBegin;
	}
	
	public void setEnd(CFGNode end) {
		mEnd = end;
	}
}
