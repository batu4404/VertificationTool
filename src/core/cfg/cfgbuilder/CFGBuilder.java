package core.cfg.cfgbuilder;

import java.io.File;
import java.util.List;

import core.cfg.declaration.BeginFor;
import core.cfg.declaration.BeginIf;
import core.cfg.declaration.BeginNode;
import core.cfg.declaration.CFGNode;
import core.cfg.declaration.ConditionNode;
import core.cfg.declaration.EndNode;
import core.cfg.declaration.LinearNode;
import core.cfg.declaration.PairNode;
import core.utils.LauncherSpoon;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.support.reflect.code.CtBlockImpl;

public class CFGBuilder {
	CtMethod method;
	
	Factory factory;
	
	CFGBuilder(){}
	
	CFGBuilder(Factory factory){
		this.factory = factory;
	}
	
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
		else if(statement instanceof CtSwitch){
			pairNode = generateCFG(((CtSwitch)statement));
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
		
		
		return new PairNode(begin, end);
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
	 * Xay dung CFG cho khoi lenh switch-case
	 * @param ctSwitch: khoi lenh switch-case
	 * @return : đối tượng chứa 2 tham chiếu đến 2 node đầu và cuối của cfg 
	 *  		được sinh ra từ khoi lenh switch case
	 */
	public PairNode generateCFG(CtSwitch ctSwitch) {
		System.out.println("switch");
		BeginIf begin = new BeginIf();
		EndNode end = new EndNode();
		//System.out.print(ctSwitch.getCases());
		List<CtCase> ctCase = ctSwitch.getCases();
		CtExpression selector = ctSwitch.getSelector();
		
		//nestedNode la Node dung de noi cac case lai voi nhau, lam tu duoi len thi ban dau nested node se la khoi end
		CFGNode nestedNode = end;
		//sinh CFG cho cac Case tu duoi len tren
		for(int i = ctCase.size() - 1; i >= 0; i--){
			CtCase c = ctCase.get(i);
			CtExpression expression = c.getCaseExpression();
			if(i == ctCase.size() - 1 && null == expression){
				// khoi cuoi cung la khoi default
				c.getStatements().remove(c.getStatements().size()-1);
				PairNode element = generateCFG(c.getStatements());
				System.out.println("size: " + c.getStatements());
				element.setEnd(nestedNode);
				nestedNode = element.getBegin();
			}
			
			else{
				CtBinaryOperator compare = factory.Core()
						.createBinaryOperator()
						.setLeftHandOperand(selector)
						.setKind(BinaryOperatorKind.EQ)
						.setRightHandOperand(expression);	
				ConditionNode condition = new ConditionNode(compare);
				c.getStatements().remove(c.getStatements().size()-1);
				PairNode element = generateCFG(c.getStatements());
				element.setEnd(end);
				System.out.println("size: " + c.getStatements());
				condition.setThenNode(element.getBegin());
				condition.setElseNode(nestedNode);
				nestedNode = condition;
			}
		}
		
		begin.setNext(nestedNode);
		begin.setEndNode(end);
		
		
		
		printCFG(begin);
		
		return new PairNode(begin, end);
	}
	
	/**
	 * @param statements: danh sach các câu lênh để sinh ra cfg
	 * @return: đối tượng chứa 2 tham chiếu đến 2 node đầu và cuối của cfg 
	 *  		được sinh ra từ statements
	 */
	public PairNode generateCFG(List<CtStatement> statements) {
		PairNode pairNode = new PairNode();
		PairNode pairTemp;
		
		BeginNode begin;
		CFGNode end;
		if(0 == statements.size()){
			begin = new BeginNode();
			end = new EndNode();
			begin.setNext(end);
			return new PairNode(begin, end);
		}
		
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
	
	/*
	 * in cfg
	 */
	public static void printCFG(PairNode cfg) {
		CFGNode begin = cfg.getBegin();
	
		printCFG(cfg.getBegin(), cfg.getEnd());
	}
	
	/*
	 * in cfg giua 2 node begin va end
	 */
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
			System.out.println("if ( " + cn.getCondition() + " )");
			printCFG(cn.getThenNode(), end);
			printCFG(cn.getElseNode(), end);
		}
		else {
			printCFG(node.getNext(), end);
		}
	}
	
	/*
	 * in cfg cho den khi gap end node
	 */
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
			System.out.println("if ( " + cn.getCondition() + " )");
			printCFG(cn.getThenNode());
			printCFG(cn.getElseNode());
		}
		else {
			printCFG(node.getNext());
		}
	}
	
	
	public void setFactory(Factory factory) {
		this.factory = factory;
	}
	
	public static void main(String[] args) {
		LauncherSpoon launcher = new LauncherSpoon();
		String pathFile = "TestSpoon.java";
		launcher.addInputResource(pathFile);
		launcher.buildModel();
		List<CtMethod> methods = launcher.getMethods();
		//System.out.println(methods.get(0));
		
		CtMethod method = methods.get(0);
		CtBlock body = method.getBody();
		List<CtStatement> statements = body.getStatements();
		
		Factory factory = launcher.getFactory();
		CFGBuilder builder = new CFGBuilder(factory);
		
		PairNode pair = builder.generateCFG(statements);
		
	//	System.out.println("cfg(test): ");
	//	printCFG(pair.getBegin());
	}
}
