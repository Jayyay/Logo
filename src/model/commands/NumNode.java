package model.commands;

/**
 * A number as an argument node.
 * @author Jay
 */
public class NumNode implements ArgNode {
	
	private double myNum;
	
	public NumNode(double num) {
		myNum = num;
	}
	
	@Override
	public double toValue() {
		return myNum;
	}
	
}
