package core.solver;

import java.util.List;

public class VerificationReport {
	
//	public static final int NOT_ALWAYS_TRUE = 0;
//	public static final int ALWAYS_TRUE = 1;
//	public static final int TIMEOUT = 2;
//	public static final int UNKNOWN = 3;
	
	public static final String NOT_ALWAYS_TRUE = "always true";
	public static final String ALWAYS_TRUE = "not always true";
	public static final String TIMEOUT = "timeout";
	public static final String UNKNOWN = "unknown";
	
	private String status;
	private List<DefineFun> parameters;
	private DefineFun ret;	// return
	private List<String> errors;
	private float solverTime;
	private float generateConstraintTime;
	
	public boolean isAlwaysTrue() {
		return ALWAYS_TRUE.equalsIgnoreCase(status);
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
	public void setStatus(String SMTStatus) {
		if ("sat".equals(SMTStatus)) {
			status = NOT_ALWAYS_TRUE;
		}
		else if ("unsat".equals(SMTStatus)) {
			status = ALWAYS_TRUE;
		}
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
	

	/**
	 * @return the parameters
	 */
	public List<DefineFun> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<DefineFun> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the ret
	 */
	public DefineFun getRet() {
		return ret;
	}

	/**
	 * @param ret the ret to set
	 */
	public void setRet(DefineFun ret) {
		this.ret = ret;
	}

	/**
	 * @return the generateConstraintTime
	 */
	public float getGenerateConstraintTime() {
		return generateConstraintTime;
	}

	/**
	 * @param generateConstraintTime the generateConstraintTime to set
	 */
	public void setGenerateConstraintTime(float generateConstraintTime) {
		this.generateConstraintTime = generateConstraintTime;
	}

	public void print() {
		System.out.println("status: " + status);
		if (errors != null) {
			for (String err: errors) {
				System.out.println("error: " + err);
			}
		}
		
		System.out.println("solver time: " + solverTime);
	}
}
