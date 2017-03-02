package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Returns the first turtle's X coordinate from the center of the screen
 */
public class XCoordinate extends TurtleCommand {

	/**
	 * @param updateListener
	 * @param context
	 */
	public XCoordinate(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		return turtle.getX();
	}

}
