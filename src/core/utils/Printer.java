package core.utils;

import core.cfg.declaration.BeginIf;
import core.cfg.declaration.CFGNode;
import core.cfg.declaration.ConditionNode;
import core.cfg.declaration.EndNode;
import core.cfg.declaration.LinearNode;
import core.cfg.declaration.PairNode;
import core.cfg.declaration.SyncNode;

public class Printer {
	
	public static void printCFG(PairNode cfg) {
		CFGNode begin = cfg.getBegin();
	
	//	printCFG(cfg.getBegin(), cfg.getEnd());
		printCFG(cfg.getBegin(), cfg.getEnd(), "");
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
	 * in cfg giua 2 node begin va end
	 */
	public static void printCFG(CFGNode node, CFGNode end, String nSpaces) {
		
		if (node == null || node == end) {
			return;
		}	
		else if (node instanceof LinearNode) {
			System.out.println(nSpaces + ((LinearNode) node).getConstraint());	
			
			printCFG(node.getNext(), end, nSpaces);	// 4 spaces
		}
		else if (node instanceof BeginIf) {
			BeginIf begin = (BeginIf) node;
			printCFG(begin.getNext(), end, nSpaces);
			printCFG(begin.getEndNode().getNext(), end, nSpaces);
		}
		else if (node instanceof ConditionNode) {
			ConditionNode cn = (ConditionNode) node;
			System.out.println(nSpaces + "if ( " + cn.getCondition() + " ) {");
			printCFG(cn.getThenNode(), cn.getEnd() , nSpaces + "    ");	// 4 spaces
			System.out.println(nSpaces + "}");
			System.out.println(nSpaces + "else {");
			printCFG(cn.getElseNode(), cn.getEnd(), nSpaces + "    ");	// 4 spaces
			System.out.println(nSpaces + "}");
			printCFG(cn.getEnd(), end, nSpaces + "");	// 4 spaces
		}
		else {
			String constraint = node.getConstraint();
			if ( !constraint.equals("")) {
				System.out.println(nSpaces + constraint);	
			}
			printCFG(node.getNext(), end, nSpaces);
		}
	}
	
	public static void printCFGPrefix(CFGNode node, CFGNode end, String nSpaces) {

		if (node == null || node == end) {
			return;
		}	
		else if (node instanceof LinearNode) {
		
			System.out.println(nSpaces + node.getPrefixConstraint());	
			
			printCFGPrefix(node.getNext(), end, nSpaces);	// 4 spaces
		}
		else if (node instanceof BeginIf) {
			BeginIf begin = (BeginIf) node;
			printCFGPrefix(begin.getNext(), end, nSpaces);
			printCFGPrefix(begin.getEndNode().getNext(), end, nSpaces);
		}
		else if (node instanceof ConditionNode) {
			ConditionNode cn = (ConditionNode) node;
			System.out.println(nSpaces + "if ( " + cn.getPrefixConstraint() + " ) {");
			printCFGPrefix(cn.getThenNode(), cn.getEnd() , nSpaces + "    ");	// 4 spaces
			System.out.println(nSpaces + "}");
			System.out.println(nSpaces + "else {");
			printCFGPrefix(cn.getElseNode(), cn.getEnd(), nSpaces + "    ");	// 4 spaces
			System.out.println(nSpaces + "}");
			printCFGPrefix(cn.getEnd(), end, nSpaces + "");	// 4 spaces
		}
		else {
			String constraint = node.getPrefixConstraint();
			if ( constraint != null) {
				System.out.println(nSpaces + constraint);	
			}
			printCFGPrefix(node.getNext(), end, nSpaces);
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
	
	/*
	 * in cfg giua 2 node begin va end
	 */
	public static void printCFG(CFGNode node, String nSpaces) {
		
		if (node == null) {
			return;
		}	
		else if (node instanceof LinearNode) {
			System.out.println(nSpaces + node.getConstraint());	
			
			printCFG(node.getNext(), nSpaces);	// 4 spaces
		}
		else if (node instanceof BeginIf) {
			BeginIf begin = (BeginIf) node;
			printCFG(begin.getNext(), nSpaces);
			printCFG(begin.getEndNode().getNext(), nSpaces);
		}
		else if (node instanceof ConditionNode) {
			ConditionNode cn = (ConditionNode) node;
			System.out.println(nSpaces + "if ( " + cn.getCondition() + " ) {");
			printCFG(cn.getThenNode(), nSpaces + "    ");	// 4 spaces
			System.out.println(nSpaces + "}");
			System.out.println(nSpaces + "else {");
			printCFG(cn.getElseNode(), nSpaces + "    ");	// 4 spaces
			System.out.println(nSpaces + "}");
		}
		else if (node instanceof SyncNode) {
			System.out.println(nSpaces + node.getConstraint());	
			
			printCFG(node.getNext(), nSpaces);	// 4 spaces
		}
		else {
			printCFG(node.getNext(), nSpaces);
		}
	}
}
