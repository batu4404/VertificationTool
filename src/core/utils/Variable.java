package core.utils;

/**
 * @author lenovo
 * chứa thông tin về biên bao gồm loại, tên biên
 */
public class Variable {
	String type = "";
	String name = "";
	int index = -1;	// mặc định là -1 do chưa được khởi tạo giá trị
	
	public Variable() {
	}
	
	
	public Variable(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public Variable(String type, String name, int index) {
		this.type = type;
		this.name = name;
		this.index = index;
	}
	
	public Variable(Variable other) {
		name = other.name;
		type = other.type;
		index = other.index;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void increase() {
		index++;
	}
	
	public String getVariableWithIndex() {
		return name + "_" + index;
	}
	
	public Variable clone() {
		return new Variable(this);
	}
	
	public String toString() {
		return "type: " + type + ", name: " + name + ", index: " + index;
	}


	public boolean hasInitialized() {
		return index > -1;
	}
}
