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
 *For [ variable start end increment ] [ command ]
 *Runs command for each value specified in the range, i.e. from start to end, going 
 *by increment.
 *Returns the value of the final command executed (or 0 if no commands
 *are executed)
 *Variable is assigned to each succeeding value so that it can be accessed
 *by the command(s)
 *swallows error of non-integer increments and sends warning message to user
 */
public class For extends AbstractCommand {

	private String myVarName;
	private ArgNode myStartNode;
	private ArgNode myEndNode;
	private ListNode myListNode;
	
	public For(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public boolean prepare(Queue<String> args) {
		// [ :var start end ]
		String listStart = myMatcher.getSymbol(args.poll());
		if (listStart == null || !listStart.equals(LIST_START)) {
			return false;
		}
		myVarName = args.poll();
		if (myVarName == null || !myMatcher.getSymbol(myVarName).equals(VARIABLE)) {
			return false;
		}
		myStartNode = constructArgNode(args);
		if (myStartNode == null) {
			return false;
		}
		myEndNode = constructArgNode(args);
		if (myEndNode == null) {
			return false;
		}
		String listEnd = myMatcher.getSymbol(args.poll());
		if (listEnd == null || !listEnd.equals(LIST_END)) {
			return false;
		}
		
		// [ command(s) ]
		setUpStackFrame();
		ArgNode listNode = constructArgNode(args);
		myContext.popStackFrame();
		if (listNode == null || !(listNode instanceof ListNode)) {
			return false;
		}
		myListNode = (ListNode) listNode;
		
		return true;
	}
	
	private void setUpStackFrame(){
		StackFrame stackFrame = new StackFrame();
		stackFrame.setVariable(myVarName, 0);
		myContext.pushStackFrame(stackFrame);
	}
	
	@Override
	public double execute() {
		StackFrame stackFrame = new StackFrame();
		myContext.pushStackFrame(stackFrame);
		double endValue = myEndNode.toValue();
		double finalValue = 0;
		for (double i = myStartNode.toValue(); i <= endValue; i += 1) {
			stackFrame.setVariable(myVarName, i);
			finalValue = myListNode.toValue();
		}
		myContext.popStackFrame();
		return finalValue;
	}

}
