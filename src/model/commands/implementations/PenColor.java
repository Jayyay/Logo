package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Returns the pen color of the first turtle
 */
public class PenColor extends TurtleCommand {

	protected PenColor(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 0;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		return turtle.getPenColor();
	}

}
