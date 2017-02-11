package core.cfg.cfgbuilder;

import java.io.File;
import java.util.List;

import core.cfg.declaration.BeginFor;
import core.cfg.declaration.BeginIf;
import core.cfg.declaration.CFGNode;
import core.cfg.declaration.ConditionNode;
import core.cfg.declaration.EndNode;
import core.cfg.declaration.LinearNode;
import core.utils.LauncherSpoon;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;

public class CFGBuilder {
	CtMethod method;
	
	CFGBuilder(){}
	
	/**
	 * @param method: ast của method sinh ra từ spoon
	 * @return: node bắt đầu của method
	 * tạm thời chưa làm gì
	 */
//	public CFGNode buildCFG(CtMethod method) {
//		this.method = method;
//		
//		return null;
//	}
	
	
	/**
	 * @param method: cau lenh hien tai va node hien tai
	 * @return: node bắt đầu của method
	 * tạm thời chưa làm gì
	 */
	public CFGNode generateCFG(CFGNode currentNode, CtStatement statement) {
		CFGNode returnNode = null;
		if (statement == null) {
			return currentNode;
		}
		if (statement instanceof CtFor) {
			returnNode = generateCFG(currentNode, (CtFor) statement);
		}
		else {
			returnNode = new LinearNode(statement);
		}
		for(int i = 0; i < 2; i ++){
			int a = 0;
		}
		
		
		return returnNode;
	}
	
	/**
	 * @param currentNode: Node hien tai(cuoi cung) trong cfg truoc khi them vong lap for vao
	 * @param ctFor
	 * @return : Node hien tai(cuoi cung) trong cfg sau khi them vong lap for vao
	 */
	public CFGNode generateCFG(CFGNode currentNode, CtFor ctFor) {
		List<CtStatement> forInit = ctFor.getForInit();   //cau lenh khoi tao cac bien khoi tao(init) vd nhu 'int i = 0'
		CtExpression conditionExp = ctFor.getExpression();	// dieu kien lap
		CtStatement body = ctFor.getBody(); //lay than vong lap
		List<CtStatement> forUpdate = ctFor.getForUpdate(); //lay phan update
		
		
		BeginFor begin = new BeginFor();
		currentNode.setNext(begin);
		
		currentNode = generateCFG(begin, forInit);
//		currentNode = generateCFG(currentNode, )
		
		
		
		return null;
	}
	
	/**
	 * Xay dung CFG cho khoi lenh if-else va them vao CFG
	 * @param currentNode: Node hien tai(cuoi cung) trong cfg truoc khi them cau lenh if vao
	 * @param ctIf
	 * @return : Node hien tai(cuoi cung) trong cfg sau khi them cau lenh if vao
	 */
	public CFGNode generateCFG(CFGNode currentNode, CtIf ctIf) {
		
		CtExpression condition = ctIf.getCondition();
		CtStatement thenStatement = ctIf.getThenStatement();
		CtStatement elseStatement = ctIf.getElseStatement();
		
		ConditionNode conditionNode = new ConditionNode(condition);
		BeginIf begin = new BeginIf();
		EndNode end = new EndNode();
		
		if (elseStatement == null) {
			LinearNode elseNode = new LinearNode();
			conditionNode.setElseNode(elseNode);
			elseNode.setNext(end);
		}
		
		
		return null;
	}
	
	public CFGNode generateCFG(CFGNode currentNode, List<CtStatement> statements) {
		
		return null;
	}
	
	
	
	public static void main(String[] args) {
		LauncherSpoon launcher = new LauncherSpoon();
		String pathFile = "TestSpoon.java";
		launcher.addInputResource(pathFile);
		launcher.buildModel();
		List<CtMethod> methods = launcher.getMethods();
		System.out.println(methods.get(0));
	}
}
