package model.commands;

/**
 * A argument node, which will result in a double value after toValue() call.
 * It could be simply a number (already a value),
 * or a variable (whose value will be looked up when used),
 * or a command (that will evaluate into a value),
 * or a list of commands (same above).
 * @author Jay
 */
public interface ArgNode {
	
	/**
	 * Evaluate to an actual double value
	 * @return value
	 */
	double toValue();
}
