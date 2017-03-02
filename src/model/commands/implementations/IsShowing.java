package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Returns 1 if first turtle is showing, 0 if it is hiding
 */
public class IsShowing extends TurtleCommand {

	public IsShowing(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		return turtle.isTurtleShown() ? 1 : 0;
	}

}
