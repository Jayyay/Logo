package model.commands.implementations;

import java.util.Queue;

import controller.UpdateListener;
import model.Context;
import model.StackFrame;
import model.commands.AbstractCommand;
import model.commands.ArgNode;
import model.commands.ListNode;

/**
 * @author rachelbransom, jay
 *
 */
public class Repeat extends AbstractCommand {

	private static final String REP_COUNT = ":REPCOUNT";

	public Repeat(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public boolean prepare(Queue<String> args) {
		myArgNode = new ArgNode[myArity];
		myArgNode[0] = constructArgNode(args);
		if (myArgNode[0] == null) {// construction of the repeat value node failed.
			return false;
		}

		constructStackFrame();
		myArgNode[1] = constructArgNode(args);
		if (myArgNode[1] == null || !(myArgNode[1] instanceof ListNode)) {
			// construction of the repeat body node failed.
			myContext.popStackFrame();
			return false;
		}
		myContext.popStackFrame();
		return true;
	}

	private void constructStackFrame() {
		StackFrame stackFrame = new StackFrame();
		stackFrame.setVariable(REP_COUNT, 0);
		myContext.pushStackFrame(stackFrame);
	}

	@Override
	public double execute() {
		StackFrame stackFrame = new StackFrame();
		myContext.pushStackFrame(stackFrame);
		int repeatTime = (int) myArgNode[0].toValue();
		double finalValue = 0;
		for (int i = 1; i <= repeatTime; i++) {
			stackFrame.setVariable(REP_COUNT, i);
			finalValue = myArgNode[1].toValue();
		}
		myContext.popStackFrame();
		return finalValue;
	}

}
