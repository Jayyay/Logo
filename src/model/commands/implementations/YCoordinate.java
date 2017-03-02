package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Returns the y co-ordinate of the turtle
 */
public class YCoordinate extends TurtleCommand {

	/**
	 * @param updateListener
	 * @param context
	 */
	public YCoordinate(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		return turtle.getY();
	}

}
