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

/**
 * @author rachelbransom
 *
 */
public class Tell extends AbstractCommand {
	
	private List<ArgNode> myListOfTurtles;
	public Tell(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
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
				return true;
			}
			ArgNode nextArgNode = constructArgNode(args);
			if (nextArgNode == null) {
				return false;
			}
			myListOfTurtles.add(nextArgNode);
		}
	}

	@Override
	public double execute() {
		List<TurtleModel> turtlesToTell = new ArrayList<>();
		Set<Integer> idsSeen = new HashSet<>();
		Map<Integer, TurtleModel> turtleMap = myContext.getCanvasModel().getTurtleMap();
		int nextId = 0;
		for (ArgNode turtle : myListOfTurtles) {
			nextId = (int)turtle.toValue();
			if (idsSeen.contains(nextId)) { // ignore duplicates
				continue;
			}
			if (!turtleMap.containsKey(nextId)) { // turtle is not created yet 
				turtleMap.put(nextId, new TurtleModel(nextId));
			}
			turtlesToTell.add(turtleMap.get(nextId));
			idsSeen.add(nextId);
		}
		myContext.getCanvasModel().tellTurtles(turtlesToTell);
		myUpdateListener.onVisualizationShouldUpdate();
		return nextId;
	}

}
