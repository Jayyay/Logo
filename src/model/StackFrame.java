package model;

import java.util.HashMap;
import java.util.Map;

/**
 * A stack frame providing the running environment of a procedure.
 * It is essential to controling the scope of the variables.
 * @author Jay
 */
public class StackFrame {
	
	private Map<String, Variable> myVariablePool;
	
	public StackFrame() {
		myVariablePool = new HashMap<>();
	}
	
	/**
	 * In this stack frame, get the Variable object associated with the name.
	 * @param varName the name of the variable (must start with a colon, e.g. ":a").
	 * @return Variable object if one exists, or null.
	 */
	public Variable getVariableWithName(String varName) {
		return myVariablePool.get(varName);
	}
	
	/**
	 * Create or update a named variable with a double value.
	 * This value only exists in this stack frame.
	 * @param varName the name of the variable.
	 * @param value double value of the variable.
	 */
	public void setVariable(String varName, double value) {
		if (myVariablePool.containsKey(varName)) {
			myVariablePool.get(varName).setMyValue(value);
		} else {
			myVariablePool.put(varName, new Variable(varName, value));
		}
	}
}
