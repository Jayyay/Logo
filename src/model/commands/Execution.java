package model.commands;

import java.util.Queue;

import controller.UpdateListener;
import model.Context;

/**
 * A conceptual wrapper command that is the entry point of every input str.
 * Aka it works as a command that executes every input str.
 * For example, 'fd 50' is wrapped as 'execute fd 50' conceptually.
 * It will signal the controller the feedback message - 
 * - if successful it sends the appropriate double response (with potential warnings like div 0)
 * - if unsuccessful it sends an error message and quits the parsing of the commands.
 * 
 * This class exists for several reasons 
 * 1) it avoids the duplicated code when finding the outer-most command
 * 2) it makes the parsing consistent
 * 3) it can take advantage of all the core/existing functionalities provided
 * in the AbstractCommand base class.
 * @author Jay
 */
public class Execution extends AbstractCommand {

	Queue<String> myArgs;
	
	public Execution(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public boolean prepare(Queue<String> args) {
		myArgs = args;
		return true;
	}
	
	@Override
	public double execute() {
		double finalValue = 0;
		boolean noError = true;;
		while (myArgs.size() > 0) {
			myContext.clearStackFrame();
			ArgNode root = constructArgNode(myArgs);
			if (root == null) {
				noError = false;
				break;
			}
			String nextArgPeeked = myArgs.peek();
			if (
				nextArgPeeked != null
				&& myMatcher.getSymbol(nextArgPeeked).equals(GROUP_END)
			) {
				/*
				 * manually remove ')' if the previous command
				 * overrides the prepare and does not support grouped arguments.
				 */
				myArgs.poll();
			}
			finalValue = root.toValue();
		}
		if (noError) {
			sendSuccessMessage(finalValue);
			return finalValue;
		}
		return 0;
	}
	
}
