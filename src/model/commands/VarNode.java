package model.commands;

import model.Context;

/**
 * A variable as an argument node.
 * @author Jay
 */
public class VarNode implements ArgNode {

	private String myVarName;
	private Context myContext;
	
	public VarNode(String varName, Context context) {
		myVarName = varName;
		myContext = context;
	}
	
	@Override
	public double toValue() {
		return myContext.getVariableWithName(myVarName).getMyValue();
	}

}
