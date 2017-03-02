package model.commands.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.AbstractCommand;
import model.commands.ArgNode;
import model.commands.ListNode;

/**
 * @author rachelbransom Tell turtles in the first list to run commands in the
 *         second list Returns result of last command run After command has run,
 *         the previously told turtles are reset so that the turtles just asked
 *         are no longer told
 */
public class Ask extends AbstractCommand {

	private List<ArgNode> myListOfTurtles;
	private ListNode myListNode;
	private List<TurtleModel> prevousTurtlesTold;

	public Ask(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public boolean prepare(Queue<String> args) {
		myListOfTurtles = new ArrayList<>();
		String listStart = args.poll();
		if (listStart == null || !myMatcher.getSymbol(listStart).equals(LIST_START)) {
			return false;
		}

		while (true) {
			String nextArg = args.peek();
			if (nextArg == null) {
				return false;
			}
			if (myMatcher.getSymbol(nextArg).equals(LIST_END)) {
				args.poll();
				break;
			}
			ArgNode nextArgNode = constructArgNode(args);
			if (nextArgNode == null) {
				return false;
			}
			myListOfTurtles.add(nextArgNode);
		}

		ArgNode listNode = constructArgNode(args);
		if (listNode == null || !(listNode instanceof ListNode)) {
			return false;
		}
		myListNode = (ListNode) listNode;
		return true;
	}

	@Override
	public double execute() {
		savePreviousToldTurtles();

		List<TurtleModel> turtlesToAsk = new ArrayList<>();
		Set<Integer> idsSeen = new HashSet<>();
		Map<Integer, TurtleModel> myTurtleMap = myContext.getCanvasModel().getTurtleMap();

		for (ArgNode turtle : myListOfTurtles) {
			int nextId = (int) turtle.toValue();
			if (idsSeen.contains(nextId)) { // ignore duplicates
				continue;
			}
			if (!myTurtleMap.containsKey(nextId)) { // turtle is not created yet
				myTurtleMap.put(nextId, new TurtleModel(nextId));
			}
			turtlesToAsk.add(myTurtleMap.get(nextId));
			idsSeen.add(nextId);
		}
		myContext.getCanvasModel().tellTurtles(turtlesToAsk);
		double valueToReturn = myListNode.toValue();

		setPreviousToldTurtlesBack();
		return valueToReturn;
	}

	private void savePreviousToldTurtles() {
		prevousTurtlesTold = myContext.getCanvasModel().getTurtlesTold();
	}
	
	private void setPreviousToldTurtlesBack(){
		myContext.getCanvasModel().tellTurtles(prevousTurtlesTold);
	}

}
