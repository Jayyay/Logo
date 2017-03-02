package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Returns the shape of the first (default) turtle
 */
public class Shape extends TurtleCommand {

	/**
	 * @param updateListener
	 * @param context
	 */
	public Shape(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 0;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		return turtle.getShape();
	}

}
