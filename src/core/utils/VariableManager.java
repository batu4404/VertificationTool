package core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lenovo
 * luu tru, quan ly cac bien trong method
 */
public class VariableManager {
	List<Variable> listVariables;
	
	public VariableManager() {
		listVariables = new ArrayList<>();
	}
	
	public VariableManager(VariableManager other) {
		copyList(other.listVariables);
	}
	
	public VariableManager(List<Variable> listVariables) {
		this.listVariables = listVariables;
	}

	public List<Variable> getListVariables() {
		return listVariables;
	}
	
	public void setListVariables(List<Variable> listVariables) {
		this.listVariables = listVariables;
	}
	
	public void addVariable(Variable var) {
		listVariables.add(var);
	}
	
	/**
	 * @param variableName
	 * @return bien co ten variableName, null neu khong tim thay
	 */
	public Variable getVariableByName(String variableName) {
		for (Variable var: listVariables) {
			if (var.getName().equals(variableName)) {
				return var;
			}
		}
		
		return null;
	}
	
	public void show() {
		for (Variable var: listVariables) {
			System.out.println(var);
		}
	}
	
	public int size() {
		return listVariables.size();
	}
	
	public Variable getVariable(int index) {
		return listVariables.get(index);
	}
	
	public VariableManager clone() {
		return new VariableManager(this);
	}
	
	private void copyList(List<Variable> list) {
		listVariables = new ArrayList<>();
		for (Variable var: list) {
			listVariables.add(var.clone());
		}
	}
}
