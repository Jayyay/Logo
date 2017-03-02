package model.commands.implementations;

import java.util.Queue;

import controller.UpdateListener;
import model.Context;
import model.StackFrame;
import model.commands.AbstractCommand;
import model.commands.ArgNode;
import model.commands.ListNode;

/**
 * DoTimes Command
 * @author Jay
 */
public class DoTimes extends AbstractCommand {
	
	private String myVarName;
	private ArgNode myLimitNode;
	private ListNode myListNode;
	
	public DoTimes(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public boolean prepare(Queue<String> args) {
		// '['
		String listStart = myMatcher.getSymbol(args.poll());
		if (listStart == null || !listStart.equals(LIST_START)) {
			return false;
		}
		
		// Variable
		myVarName = args.poll();
		if (myVarName == null || !myMatcher.getSymbol(myVarName).equals("Variable")) {
			return false;
		}
		
		// Limit
		myLimitNode = constructArgNode(args);
		if (myLimitNode == null) {
			return false;
		}
		
		// ']'
		String listEnd = myMatcher.getSymbol(args.poll());
		if (listEnd == null || !listEnd.equals(LIST_END)) {
			return false;
		}
		
		// '[ command(s) ]'
		StackFrame stackFrame = new StackFrame();
		stackFrame.setVariable(myVarName, 0);
		myContext.pushStackFrame(stackFrame);
		ArgNode listNode = constructArgNode(args);
		if (listNode == null || !(listNode instanceof ListNode)) {
			myContext.popStackFrame();
			return false;
		}
		myContext.popStackFrame();
		myListNode = (ListNode) listNode;
		return true;
	}

	@Override
	public double execute() {
		StackFrame stackFrame = new StackFrame();
		myContext.pushStackFrame(stackFrame);
		stackFrame.setVariable(myVarName, 0);
		double limit = (int) myLimitNode.toValue();
		double finalValue = 0;
		for (int i = 1; i <= limit; i++) {
			stackFrame.setVariable(
				myVarName,
				stackFrame.getVariableWithName(myVarName).getMyValue() + 1
			);
			finalValue = myListNode.toValue();
		}
		myContext.popStackFrame();
		return finalValue;
	}
	
}
