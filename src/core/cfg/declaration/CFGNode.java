package core.cfg.declaration;

public abstract class CFGNode {
	private NodeType nodeType;

	public NodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
	
}
