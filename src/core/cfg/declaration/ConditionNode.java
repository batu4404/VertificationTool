package core.cfg.declaration;

import core.utils.FormulaCreater;
import core.utils.Index;
import core.utils.Variable;
import core.utils.VariableManager;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;

/**
 * Node chứa biểu thức điều kiện và 2 nhánh else, then để biểu diễn
 * lệnh if-else, điều kiện trong vòng, lặp,...
 * 
 */
public class ConditionNode extends CFGNode {
	// thuộc tính next kế thừa từ CFGNode trỏ tới nhánh then
	// của biểu thức điều kiện
	private CFGNode elseNode;	// trỏ tới nhánh else
	private EndCondition end;	// trỏ tới node kết thúc
	private CtExpression condition;	// biểu thức điều kiện
	private CFGNode endOfThenBranch;
	private CFGNode endOfElseBranch;
	
	private VariableManager thenVM;
	private VariableManager elseVM;

	public ConditionNode(CtExpression condition) {
		CtExpression clone = condition.clone();
		
	//	this.condition = condition;
		this.condition = clone;
	}
	
	public CtExpression getCondition() {
		return condition;
	}

	public void setCondition(CtExpression condition) {
		this.condition = condition.clone();
	}

	public CFGNode getElseNode() {
		
		return elseNode;
	}
	
	public void setElseNode(CFGNode elseNode) {
		this.elseNode = elseNode;
	}
	
	public void setElse(PairNode elseBranch) {
		this.elseNode = elseBranch.getBegin();
		this.endOfElseBranch = elseBranch.getEnd();
	}
	
	public CFGNode getThenNode() {
		return next;
	}
	
	public void setThenNode(CFGNode thenNode) {
		next = thenNode;
	}
	
	public void setThen(PairNode thenBranch) {
		this.next = thenBranch.getBegin();
		this.endOfThenBranch = thenBranch.getEnd();
	}
	
	public CFGNode getEnd() {
		return end;
	}

	public void setEnd(EndCondition end) {
		this.end = end;
	}

	public String getConstrait() {
		return condition.toString();
	}
	
	public CFGNode getNext() {
		return end;
	}
	
	public CFGNode getEndOfThenBranch() {
		return endOfThenBranch;
	}

	public void setEndOfThenBranch(CFGNode endOfThenBranch) {
		this.endOfThenBranch = endOfThenBranch;
	}

	public CFGNode getEndOfElseBranch() {
		return endOfElseBranch;
	}

	public void setEndOfElseBranch(CFGNode endOfElseBranch) {
		this.endOfElseBranch = endOfElseBranch;
	}

	@Override
	public void index(VariableManager vm) {
		Index.index(condition, vm);
		
		thenVM = vm;
		elseVM = vm.clone();
		
		CFGNode thenNode = this.next;
		
		CFGNode nextNode = thenNode;
		if (nextNode == null)
			return;
		
		while(nextNode != end) {
			nextNode.index(vm);
			nextNode = nextNode.getNext();
		}
		
		nextNode = elseNode;
		if (nextNode == null)
			return;
		
		while(nextNode != end) {
			nextNode.index(elseVM);
			nextNode = nextNode.getNext();
		}
		
		syncIndex();
	}
	
	void print(CFGNode node) {
		if (node == null)
			return;
		
		System.out.println(node.getConstraint());
		print(node.getNext());
	}
	
	@Override
	public String getPrefixConstraint() {
		String conditionStr = FormulaCreater.createFormula(condition);
		
//		String thenConstraint = Converttion.prefix(next, end);
//		String elseConstraint = Converttion.prefix(elseNode, end);
//		
		return conditionStr;
	}
	
	@Override
	public String getFormula() {
		String conditionStr = FormulaCreater.createFormula(condition);
		String notConditionStr = FormulaCreater.createFormula(FormulaCreater.NEGATIVE, conditionStr);
//		System.out.println("next: " + next);
//		System.out.println("end: " + endOfThenBranch);
		
		String thenFormula = FormulaCreater.createFormula(next, end);
		String elseFormula = FormulaCreater.createFormula(elseNode, end);
		
		String formula = "";
		
		if (thenFormula == null && elseFormula == null) {
			return null;
		}	
		else if (thenFormula == null) {
			formula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, elseFormula);
		}
		else if (elseFormula == null) {
			formula = FormulaCreater.wrapPrefix(FormulaCreater.BINARY_CONNECTIVE, conditionStr, thenFormula);
		}
		else {
			thenFormula = FormulaCreater.wrapPrefix(conditionStr, FormulaCreater.BINARY_CONNECTIVE, thenFormula);
			elseFormula = FormulaCreater.wrapPrefix(notConditionStr, FormulaCreater.BINARY_CONNECTIVE, elseFormula);
			
			formula = FormulaCreater.wrapPrefix(FormulaCreater.LOGIC_AND, thenFormula, elseFormula);
		}
		
		return formula;
	}
	
	private void syncIndex() {
		int size = thenVM.size();
		Variable thenVar;
		Variable elseVar;
		// hai vế của biểu thức đồng bộ (leftHand = rightHand)
		String leftHand;
		String rightHand;

		CFGNode thenSyncTemp = new CFGNode() {};
		CFGNode elseSyncTemp = new CFGNode() {};
		CFGNode lastThenSync = thenSyncTemp;
		CFGNode lastElseSync = elseSyncTemp;
		SyncNode syncNode;
		for (int i = 0; i < size; i++) {
			thenVar = thenVM.getVariable(i);
			elseVar = elseVM.getVariable(i);
		
			if (thenVar.getIndex() < elseVar.getIndex()) {
				leftHand = elseVar.getVariableWithIndex();
				rightHand = thenVar.getVariableWithIndex();
				syncNode = new SyncNode(leftHand, rightHand);
				lastThenSync.setNext(syncNode);
				lastThenSync = syncNode;
			}
			else if (elseVar.getIndex() < thenVar.getIndex()) {
				leftHand = thenVar.getVariableWithIndex();
				rightHand = elseVar.getVariableWithIndex();
				syncNode = new SyncNode(leftHand, rightHand);
				lastElseSync.setNext(syncNode);
				lastElseSync = syncNode;
			}
		}
		
		
		elseNode = elseSyncTemp.getNext();
		lastElseSync.setNext(end);
		
		if (thenSyncTemp.getNext() != null) {
			endOfElseBranch.setNext(thenSyncTemp.getNext());
			endOfElseBranch = lastElseSync;
			lastThenSync.setNext(end);
		}
	}

}