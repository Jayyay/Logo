package model.commands.implementations;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.AbstractCommand;
import model.commands.ArgNode;
import model.commands.ListNode;

/**
 * @author rachelbransom
 *Tell turtles matching given condition to run commands given in the second list
 *returns result of last command
 *All previously told turtles are reset after the command executes
 */
public class AskWith extends AbstractCommand {

	private ArgNode myCondition;
	private ListNode myListNode;

	public AskWith(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	public boolean prepare(Queue<String> args) {
		String listStart = args.poll();
		if (listStart == null || !myMatcher.getSymbol(listStart).equals(LIST_START)) {
			return false;
		}

		String nextArg = args.peek();
		if (nextArg == null) {
			return false;
		}
		ArgNode nextArgNode = constructArgNode(args);
		if (nextArgNode == null) {
			return false;
		}
		myCondition = nextArgNode;

		String listEnd = args.peek();
		if (listEnd == null || !myMatcher.getSymbol(listEnd).equals(LIST_END)) {
			return false;
		}
		args.poll();

		ArgNode listNode = constructArgNode(args);
		if (listNode == null || !(listNode instanceof ListNode)) {
			return false;
		}
		myListNode = (ListNode) listNode;
		return true;
	}

	@Override
	public double execute() {
		// store the previous turtlesTold
		List<TurtleModel> prevousTurtlesTold = myContext.getCanvasModel().getTurtlesTold();

		// execute
		Map<Integer, TurtleModel> myTurtleMap = myContext.getCanvasModel().getTurtleMap();
		double valueToReturn = 0;

		for (TurtleModel turtle : myTurtleMap.values()) {
			myContext.getCanvasModel().tellTurtles(turtle);
			myContext.getCanvasModel().setActiveTurtleID(turtle.getID());
			if (myCondition.toValue() == 1) {
				valueToReturn = myListNode.toValue();
			}
		}

		// set turtlesTold back
		myContext.getCanvasModel().tellTurtles(prevousTurtlesTold);
		return valueToReturn;
	}

}
