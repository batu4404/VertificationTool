package core.cfg.declaration;

/**
 * Node biểu diễn các câu lệnh thông thường: gán,...
 */
public class NormalNode extends LinearNode {
	/**
	 * biểu thức của câu lệnh
	 */
	private String statement;

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
}
