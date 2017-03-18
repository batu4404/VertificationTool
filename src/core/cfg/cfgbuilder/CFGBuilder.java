package core.cfg.cfgbuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.cfg.declaration.VtCFG;
import core.cfg.declaration.node.BeginIf;
import core.cfg.declaration.node.BeginNode;
import core.cfg.declaration.node.CFGNode;
import core.cfg.declaration.node.ConditionNode;
import core.cfg.declaration.node.EmptyNode;
import core.cfg.declaration.node.EndCondition;
import core.cfg.declaration.node.EndNode;
import core.cfg.declaration.node.LinearNode;
import core.cfg.declaration.node.PairNode;
import core.cfg.declaration.node.ReturnNode;
import core.cfg.declaration.node.SyncNode;
import core.utils.LauncherSpoon;
import core.utils.Printer;
import core.utils.Variable;
import core.utils.VariableManager;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCase;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtOperatorAssignment;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtBlockImpl;

public class CFGBuilder {
	CtMethod method;
	
	List<String> parameters; 
	
	private int nLoops = 1;
	
	public CFGBuilder(){}
	
	CFGBuilder(int nLoops){
		this.nLoops = nLoops;
	}
	
	public int getNumberOfLoop() {
		return nLoops;
	}
	
	public CFGBuilder setNumberOfLoop(int nLoops) {
		this.nLoops = nLoops;
		return this;
	}
	
	/**
	 * @param method: ast của method sinh ra từ spoon
	 * @return: node bắt đầu của method
	 * tạm thời chưa làm gì
	 */
	public VtCFG buildCFG(CtMethod method) {
		this.method = method;
		
		CtBlock body = method.getBody();
		PairNode preCFG = generateCFG(body);
		
		VtCFG cfg = new VtCFG();
		cfg.setPreCFG(preCFG);
		
		VariableManager vm = buildVariableManager();
		cfg.setVariableManager(vm);
		
		
		CtTypeReference returnType = method.getType();
		cfg.setReturnType(returnType.toString());
		
		if (!returnType.toString().equals("void")) {
			parameters.add("return");
		}
		
		cfg.setParameters(parameters);
		
		return cfg;
	}
	
	
	/**
	 * @param statement: cau can them vao cfg
	 * @return: doi tuong kieu PairNode chua node dau và node cuối
	 *        của đoạn cfg vừa sinh ra từ statement
	 */
	public PairNode generateCFG(CtStatement statement) {
		PairNode pairNode = null;
		
		if (statement == null) {
			EmptyNode empty = new EmptyNode();
			pairNode = new PairNode(empty, empty);
		}
		else if (statement instanceof CtFor) {
			pairNode = generateCFG((CtFor) statement);
		}
		else if (statement instanceof CtWhile) {
			pairNode = generateCFG((CtWhile) statement);
		}
		else if (statement instanceof CtDo) {
			System.out.println("hello here");
			pairNode = generateCFG((CtDo) statement);
		}
		else if (statement instanceof CtIf) {
			pairNode = generateCFG((CtIf) statement);
		}
		else if (statement instanceof CtBlock) {
			pairNode = generateCFG(((CtBlock) statement).getStatements());
		}
		else if (statement instanceof CtSwitch) {
			pairNode = generateCFG(((CtSwitch) statement));
		}
		else if (statement instanceof CtReturn) {
			System.out.println("return");
			ReturnNode returnNode = new ReturnNode((CtReturn) statement);
			pairNode = new PairNode(returnNode, returnNode);
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
	public PairNode generateCFG(CtFor loop) {
		List<CtStatement> forInit = loop.getForInit();   //cau lenh khoi tao cac bien khoi tao(init) vd nhu 'int i = 0'
		CtExpression conditionExp = loop.getExpression();	// dieu kien lap
		CtStatement forBody = loop.getBody(); //lay than vong lap
		List<CtStatement> forUpdate = loop.getForUpdate(); //lay phan update
		
		EndCondition end = new EndCondition();
		
		PairNode init = generateCFG(forInit);
		
		CFGNode lastNode = end;
		EmptyNode emptyNode;
		for (int i = 0; i < nLoops; i++) {
			
			ConditionNode condition = new ConditionNode(conditionExp);
			
			PairNode body = generateCFG(forBody);
			PairNode update = generateCFG(forUpdate);
			
			// nối body với update
			body.getEnd().setNext(update.getBegin());
			
			condition.setThenNode(body.getBegin());
			update.getEnd().setNext(lastNode);
			condition.setEndOfThenBranch(lastNode);
			
			emptyNode = new EmptyNode();
			condition.setElseNode(emptyNode);
			condition.setEndOfElseBranch(emptyNode);
			emptyNode.setNext(end);
			
			condition.setEnd(end);
			
			lastNode = condition;
		}

		init.getEnd().setNext(lastNode);
		
		return new PairNode(init.getBegin(), end);
	}
	
	/**
	 * @param currentNode: Node hien tai(cuoi cung) trong cfg truoc khi them vong lap while vao
	 * @param loop
	 * @return : Node hien tai(cuoi cung) trong cfg sau khi them vong lap for vao
	 */
	public PairNode generateCFG(CtWhile loop) {
		
		CtExpression loopingExp = loop.getLoopingExpression();	// dieu kien lap
		CtStatement loopBody = loop.getBody(); //lay than vong lap
		
		EndCondition end = new EndCondition();
		
		CFGNode lastNode = end;
		for (int i = 0; i < nLoops; i++) {
			
			ConditionNode condition = new ConditionNode(loopingExp);
			
			PairNode body = generateCFG(loopBody);
			
			condition.setThenNode(body.getBegin());
			body.getEnd().setNext(lastNode);
			condition.setEndOfThenBranch(lastNode);
			
			condition.setElseNode(end);
			condition.setEnd(end);
			
			lastNode = condition;
		}
		
		return new PairNode(lastNode, end);
	}
	
	/**
	 * @param currentNode: Node hien tai(cuoi cung) trong cfg truoc khi them vong lap while vao
	 * @param loop
	 * @return : Node hien tai(cuoi cung) trong cfg sau khi them vong lap for vao
	 */
	public PairNode generateCFG(CtDo loop) {
		
		System.out.println("hello");
		
		CtExpression loopingExp = loop.getLoopingExpression();	// dieu kien lap
		CtStatement loopBody = loop.getBody(); //lay than vong lap
		
		EndCondition end = new EndCondition();
		
		CFGNode lastNode = end;
		PairNode body;
		for (int i = 0; i < nLoops; i++) {
			ConditionNode condition = new ConditionNode(loopingExp);
			
			body = generateCFG(loopBody);
			
			condition.setThenNode(body.getBegin());
			body.getEnd().setNext(lastNode);
			condition.setEndOfThenBranch(lastNode);
			
			condition.setElseNode(end);
			condition.setEnd(end);
			
			lastNode = condition;
		};
		
		body = generateCFG(loopBody);
		body.getEnd().setNext(lastNode);
		lastNode = body.getBegin();
		
		return new PairNode(lastNode, end);
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
	
		EndCondition end = new EndCondition();
		
		PairNode elseCFG = generateCFG(elseStatement);
		PairNode thenCFG = generateCFG(thenStatement);
		
//		conditionNode.setThenNode(thenCFG.getBegin());
		conditionNode.setThen(thenCFG);
		thenCFG.getEnd().setNext(end);
		
//		conditionNode.setElseNode(elseCFG.getBegin());
		conditionNode.setElse(elseCFG);
		elseCFG.getEnd().setNext(end);
		
		conditionNode.setEnd(end);
		
		PairNode pair = new PairNode(conditionNode, end);
		
		System.out.println("if-else:");
		Printer.printCFG(pair);
		
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
//				System.out.println("size: " + c.getStatements());
				element.setEnd(nestedNode);
				nestedNode = element.getBegin();
			}
			
			else {
				
				CtBinaryOperator compare = ctSwitch.getFactory().Core()
						.createBinaryOperator()
						.setLeftHandOperand(selector)
						.setKind(BinaryOperatorKind.EQ)
						.setRightHandOperand(expression);	
				ConditionNode condition = new ConditionNode(compare);
				c.getStatements().remove(c.getStatements().size()-1);
				PairNode element = generateCFG(c.getStatements());
				element.setEnd(end);
//				System.out.println("size: " + c.getStatements());
				condition.setThenNode(element.getBegin());
				condition.setElseNode(nestedNode);
				nestedNode = condition;
			}
		}
		
		begin.setNext(nestedNode);
		begin.setEndNode(end);
		
		
		Printer.printCFG(begin);
		
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
		
		CFGNode begin;
		CFGNode end;
		if(0 == statements.size()) {
			begin = new EmptyNode();
			end = begin;
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
		
		pairNode.setEnd(pairTemp.getEnd());
		
		return pairNode;
	}
	

	/**
	 * nối 2 đoạn cfg dc lưu trong 2 biến PairNode 
	 * @return
	 */
	private PairNode concat() {
		return null;
	}
	
	private VariableManager buildVariableManager() {
		VariableManager vm = new VariableManager();
		List<CtParameter> params = method.getParameters();
		
		parameters = new ArrayList<String>();
		
		String name;
		String type;
		Variable var;
		int i = 0;
		for (CtParameter param: params) {
			var = new Variable(param.getType().toString(), param.getSimpleName());
			var.setIndex(0); // chỉ số của tham biến khi bắt đầu method là 0
			vm.addVariable(var);
			
			parameters.add(param.getSimpleName());
		}
		
		List<CtElement> listEle = method.getElements(new TypeFilter(CtLocalVariable.class));
		CtLocalVariable lv;
		for (CtElement e: listEle) {
			lv = (CtLocalVariable) e;
			var = new Variable(lv.getType().toString(), lv.getSimpleName());
			vm.addVariable(var);
		}
		
		return vm;
	}
}
