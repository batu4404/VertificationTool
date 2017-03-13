package core.cfg.declaration;

import java.nio.channels.Pipe;

import core.cfg.cfgbuilder.CFGBuilder;
import spoon.reflect.declaration.CtMethod;

public class VtCFG {
	BeginMethod begin;
	EndMethod end;
	
	CtMethod method;
	
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
	
	public void printPrefix() {
		CFGBuilder.printCFGPrefix(begin, end, "");
	}
}
