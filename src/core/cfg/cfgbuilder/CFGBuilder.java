package core.cfg.cfgbuilder;

import core.cfg.declaration.CFGNode;
import spoon.reflect.declaration.CtMethod;

public class CFGBuilder {
	CtMethod method;
	
	CFGBuilder(){}
	
	/**
	 * @param method: ast của method sinh ra từ spoon
	 * @return: node bắt đầu của method
	 * tạm thời chưa làm gì
	 */
	public CFGNode buildCFG(CtMethod method) {
		this.method = method;
		
		return null;
	}
}
