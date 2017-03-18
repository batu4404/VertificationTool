package core.cfg.declaration;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.channels.Pipe;
import java.util.List;

import core.cfg.cfgbuilder.CFGBuilder;
import core.cfg.declaration.node.BeginMethod;
import core.cfg.declaration.node.CFGNode;
import core.cfg.declaration.node.EndMethod;
import core.cfg.declaration.node.PairNode;
import core.solver.SMTTypeConvertion;
import core.utils.Printer;
import core.utils.Variable;
import core.utils.VariableManager;
import spoon.reflect.declaration.CtMethod;

public class VtCFG {
	BeginMethod begin;
	EndMethod end;
	
	CtMethod method;
	VariableManager vm;
	
	List<String> parameters; // danh sách tên các tham sô + return (nếu hàm có trả về giá trị)
	String returnType;	// phương thức có trả về giá trị hay là kiểu void
	
	public VtCFG() {
		begin = new BeginMethod();
		end = new EndMethod();
	}
	
	public BeginMethod getBegin() {
		return begin;
	}
	
	public EndMethod getEnd() {
		return end;
	}
	
	public VtCFG setBegin(BeginMethod begin) {
		this.begin = begin;
		return this;
	}
	
	
	
	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public void setPreCFG(PairNode pair) {
		begin.setNext( pair.getBegin() );
		pair.getEnd().setNext(end);
	}

	public VtCFG setMethod(CtMethod method) {
		this.method = method;
		return this;
	}
	
	
	/**
	 * @return the vm
	 */
	public VariableManager getVariableManager() {
		return vm;
	}

	/**
	 * @param vm the vm to set
	 */
	public VtCFG setVariableManager(VariableManager vm) {
		this.vm = vm;
		return this;
	}

	/**
	 * @return the method
	 */
	public CtMethod getMethod() {
		return method;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(EndMethod end) {
		this.end = end;
	}
	
	

	/**
	 * @return the parameters
	 */
	public List<String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public void printPrefix() {
		Printer.printCFGPrefix(begin, end, "");
	}
	
	public void index() {
		CFGNode next = begin.getNext();
		while (next != end) {
			next.index(vm);
			next = next.getNext();
		}
	}
	
	public String getFormula() {
		String formula = "";
		
		CFGNode next = begin.getNext();
		while (next != end) {
			next.index(vm);
			next = next.getNext();
		}
		
		return formula;
	}
	
	public void printFormula() {
		CFGNode node = begin.getNext();
		String f;
		
		
		int lastIndex;
		
		// (declare-fun a_0 () Int)
		for (Variable var: vm.getListVariables()) {
			lastIndex = var.getIndex();
			for (int i = 0; i <= lastIndex; i++) {
				System.out.println("(declare-fun " + var.getName() + "_" + i + " () Int)");
			}
			
		}
	
		while (node != end) {
			f = node.getFormula();
			if (f != null) {
				System.out.println("(assert " + f + ")");
			}
			node = node.getNext();
		}
	}
	
	public void printSMTFormual(PrintStream printStream) {
		CFGNode node = begin.getNext();
		String f;
		
		int lastIndex;
		
		// (declare-fun a_0 () Int)
		for (Variable var: vm.getListVariables()) {
			lastIndex = var.getIndex();
			for (int i = 0; i <= lastIndex; i++) {
				printStream.println("(declare-fun " + var.getName() + "_" + i + 
										" () "+ SMTTypeConvertion.getSMTType(var.getType()) +")");
			}
		}
		
		if (!returnType.equals("void")) {
			printStream.println("(declare-fun return () " + 
							SMTTypeConvertion.getSMTType(returnType)+ ")");
		}
		
	
		while (node != end) {
			f = node.getFormula();
			if (f != null) {
				printStream.println("(assert " + f + ")");
			}
			node = node.getNext();
		}
	}
}
