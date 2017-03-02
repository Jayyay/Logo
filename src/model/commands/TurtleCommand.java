package model.commands;

import controller.UpdateListener;
import model.CanvasModel;
import model.Context;
import model.TurtleModel;

/**
 * An abstract turtle command extending AbstractCommand class,
 * providing common functionalities for commands related to turtle operations,
 * e.g. looping through all the turtles told to support multiple turtles.
 * @author Jay
 */
public abstract class TurtleCommand extends AbstractCommand {

	protected TurtleCommand(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public final double execute() {
		double finalValue = 0;
		CanvasModel canvas = myContext.getCanvasModel();
		for (TurtleModel turtle : canvas.getTurtlesTold()) {
			canvas.setActiveTurtleID(turtle.getID());
			finalValue = executeAsTurtle(turtle);
		}
		myUpdateListener.onVisualizationShouldUpdate();
		return finalValue;
	}
	
	/**
	 * Actual implementations of the actions done by each turtle.
	 * @param turtle turtle that acts
	 * @return result
	 */
	protected abstract double executeAsTurtle(TurtleModel turtle);
	
}
