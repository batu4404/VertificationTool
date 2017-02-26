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
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;
import spoon.support.reflect.code.CtBlockImpl;

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
		else if (statement instanceof CtBlock) {
			pairNode = generateCFG(((CtBlock)statement).getStatements());
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
		CtStatement forBody = ctFor.getBody(); //lay than vong lap
		List<CtStatement> forUpdate = ctFor.getForUpdate(); //lay phan update
		
		
		BeginFor begin = new BeginFor();	
		EndNode end = new EndNode();
		
		PairNode init = generateCFG(forInit);
		
		
		ConditionNode condition = new ConditionNode(conditionExp);
		
		PairNode body = generateCFG(forBody);
		
		PairNode update = generateCFG(forUpdate);
		
		begin.setNext(init.getBegin());
		init.getEnd().setNext(condition);
		
		
		return null;
	}
	
	/**
	 * Xay dung CFG cho khoi lenh if-else va them vao CFG
	 * @param currentNode: Node hien tai(cuoi cung) trong cfg truoc khi them cau lenh if vao
	 * @param ctIf
	 * @return : Node hien tai(cuoi cung) trong cfg sau khi them cau lenh if vao
	 */
	public PairNode generateCFG(CtIf ctIf) {
		
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
		thenCFG.getEnd().setNext(end);
		
		conditionNode.setElseNode(elseCFG.getBegin());
		elseCFG.getEnd().setNext(end);
		
		begin.setEndNode(end);
		PairNode pair = new PairNode(begin, end);
		
		System.out.println("if-else:");
		printCFG(pair);
		
		return pair;
	}
	
	/**
	 * @param statements: danh sach các câu lênh để sinh ra cfg
	 * @return: đối tượng chứa 2 tham chiếu đến 2 node đầu và cuối của cfg 
	 *  		được sinh ra từ statements
	 */
	public PairNode generateCFG(List<CtStatement> statements) {
		PairNode pairNode = new PairNode();
		PairNode pairTemp;
		
	//	CFGNode begin;
		CFGNode end;
		
		pairTemp = generateCFG(statements.get(0));
		pairNode.setBegin(pairTemp.getBegin());
	
		end = pairTemp.getEnd();
		
		for (int i = 1; i < statements.size(); i++) {

			pairTemp = generateCFG(statements.get(i));
			
			end.setNext(pairTemp.getBegin());

			end = pairTemp.getEnd();
		}
		
		pairNode.setEnd(end);
		
		return pairNode;
	}
	
	public static void printCFG(PairNode cfg) {
		CFGNode begin = cfg.getBegin();
	
		printCFG(cfg.getBegin(), cfg.getEnd());
	}
	
	public static void printCFG(CFGNode node, CFGNode end) {
		if (node == null || node == end || node instanceof EndNode) {
			return;
		}	
		else if (node instanceof LinearNode) {
			System.out.println(((LinearNode) node).getConstraint());	
			
			printCFG(node.getNext(), end);
		}
		else if (node instanceof BeginIf) {
			BeginIf begin = (BeginIf) node;
			printCFG(begin.getNext(), end);
			printCFG(begin.getEndNode().getNext(), end);
		}
		else if (node instanceof ConditionNode) {
			ConditionNode cn = (ConditionNode) node;
			System.out.println("if");
			printCFG(cn.getThenNode(), end);
			printCFG(cn.getElseNode(), end);
		}
		else {
			printCFG(node.getNext(), end);
		}
	}
	
	public static void printCFG(CFGNode node) {
		
		if (node == null || node instanceof EndNode) {
			return;
		}	
		else if (node instanceof LinearNode) {
			System.out.println(((LinearNode) node).getConstraint());	
			printCFG(node.getNext());
		}
		else if (node instanceof BeginIf) {
			BeginIf begin = (BeginIf) node;
			printCFG(begin.getNext());
			printCFG(begin.getEndNode().getNext());
		}
		else if (node instanceof ConditionNode) {
			ConditionNode cn = (ConditionNode) node;
			System.out.println("if");
			printCFG(cn.getThenNode());
			printCFG(cn.getElseNode());
		}
		else {
			printCFG(node.getNext());
		}
	}
	
	public static void main(String[] args) {
		LauncherSpoon launcher = new LauncherSpoon();
		String pathFile = "TestSpoon.java";
		launcher.addInputResource(pathFile);
		launcher.buildModel();
		List<CtMethod> methods = launcher.getMethods();
		System.out.println(methods.get(0));
		
		CtMethod method = methods.get(0);
		CtBlock body = method.getBody();
		List<CtStatement> statements = body.getStatements();
		
		CFGBuilder builder = new CFGBuilder();
		
		PairNode pair = builder.generateCFG(statements);
		
		System.out.println("cfg(test): ");
		printCFG(pair.getBegin());
	}
}
