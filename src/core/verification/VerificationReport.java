package core.verification;

import java.util.List;

public class VerificationReport {
	private String status;
	private List<String> errors;
	private float solverTime;
	
	public boolean isSAT() {
		return "sat".equals(status);
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}
	
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	/**
	 * @return the solverTime
	 */
	public float getSolverTime() {
		return solverTime;
	}
	
	/**
	 * @param solverTime the solverTime to set
	 */
	public void setSolverTime(float solverTime) {
		this.solverTime = solverTime;
	}
	
	
}
