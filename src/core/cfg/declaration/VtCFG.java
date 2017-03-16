package core.cfg.declaration;

import java.nio.channels.Pipe;

import core.cfg.cfgbuilder.CFGBuilder;
import core.utils.Printer;
import core.utils.VariableManager;
import spoon.reflect.declaration.CtMethod;

public class VtCFG {
	BeginMethod begin;
	EndMethod end;
	
	CtMethod method;
	VariableManager vm;
	
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
	
		while (node != end) {
			f = node.getFormula();
			if (f != null) {
				System.out.println(f);
			}
			node = node.getNext();
		}
	}
}
