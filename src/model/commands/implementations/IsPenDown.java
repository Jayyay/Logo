package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 * returns 1 if the first turtle's pen is down, 0 if it is up
 */
public class IsPenDown extends TurtleCommand {

	/**
	 * @param updateListener
	 * @param context
	 * @param turtles
	 */
	public IsPenDown(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		return turtle.isPenUp() ? 0 : 1;
	}

}
