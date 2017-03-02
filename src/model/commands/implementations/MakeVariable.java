package model.commands.implementations;

import java.util.Queue;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;
import model.commands.ArgNode;

/**
 * Make/Set command
 * @author Jay
 */
public class MakeVariable extends AbstractCommand {

	private String myVarName;
	private ArgNode myValueNode;
	
	public MakeVariable(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public boolean prepare(Queue<String> args) {
		String varName = args.poll();
		if (varName == null || !myMatcher.getSymbol(varName).equals(VARIABLE)) {
			return false;
		}
		myVarName = varName;
		myContext.setVariable(varName, 0);
		myValueNode = constructArgNode(args);
		if (myValueNode == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public double execute() {
		double value = myValueNode.toValue();
		myContext.setVariable(myVarName, value);
		myUpdateListener.onTrackingShouldUpdate();
		return myValueNode.toValue();
	}
	
}
