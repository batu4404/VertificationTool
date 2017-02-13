package core.cfg.cfgbuilder;

import java.io.File;
import java.util.List;

import core.cfg.declaration.BeginFor;
import core.cfg.declaration.BeginIf;
import core.cfg.declaration.CFGNode;
import core.cfg.declaration.ConditionNode;
import core.cfg.declaration.EndNode;
import core.cfg.declaration.LinearNode;
import core.cfg.declaration.PairNode;
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
	 * @param statement: cau can them vao cfg
	 * @return: doi tuong kieu PairNode chua node dau và node cuối
	 *        của đoạn cfg vừa sinh ra từ statement
	 */
	public PairNode generateCFG(CtStatement statement) {
		PairNode pairNode = null;
		
		if (statement instanceof CtFor) {
			pairNode = generateCFG((CtFor) statement);
		}
		else if (statement instanceof CtIf) {
			pairNode = generateCFG((CtIf) statement);
		}
		else {
			LinearNode normal = new LinearNode(statement);
			pairNode = new PairNode(normal, normal);
		}
		
		return pairNode;
	}
	
	/**
	 * @param currentNode: Node hien tai(cuoi cung) trong cfg truoc khi them vong lap for vao
	 * @param ctFor
	 * @return : Node hien tai(cuoi cung) trong cfg sau khi them vong lap for vao
	 */
	public PairNode generateCFG(CtFor ctFor) {
		List<CtStatement> forInit = ctFor.getForInit();   //cau lenh khoi tao cac bien khoi tao(init) vd nhu 'int i = 0'
		CtExpression conditionExp = ctFor.getExpression();	// dieu kien lap
		CtStatement body = ctFor.getBody(); //lay than vong lap
		List<CtStatement> forUpdate = ctFor.getForUpdate(); //lay phan update
		
		
		BeginFor begin = new BeginFor();		
		
		return null;
	}
	
	/**
	 * Xay dung CFG cho khoi lenh if-else va them vao CFG
	 * @param currentNode: Node hien tai(cuoi cung) trong cfg truoc khi them cau lenh if vao
	 * @param ctIf
	 * @return : Node hien tai(cuoi cung) trong cfg sau khi them cau lenh if vao
	 */
	public PairNode generateCFG(CtIf ctIf) {
		
		PairNode pair = new PairNode();
		
		CtExpression condition = ctIf.getCondition();
		CtStatement thenStatement = ctIf.getThenStatement();
		CtStatement elseStatement = ctIf.getElseStatement();
		
		ConditionNode conditionNode = new ConditionNode(condition);
		BeginIf begin = new BeginIf();
		begin.setNext(conditionNode);
		
		EndNode end = new EndNode();
		
		PairNode elseCFG = generateCFG(elseStatement);
		PairNode thenCFG = generateCFG(thenStatement);
		
		conditionNode.setThenNode(thenCFG.getBegin());
		thenCFG.getEnd().setNext(begin);
		
		conditionNode.setElseNode(elseCFG.getBegin());
		elseCFG.getEnd().setNext(end);
		
		return pair;
	}
	
	public PairNode generateCFG(List<CtStatement> statements) {
		PairNode PairNode = 
		
		for (CtStatement st: statements) {
			
		}
		
		
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
