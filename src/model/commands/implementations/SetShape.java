package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Sets the shape of the turtle, based on a pre-determined
 *map of doubles to shapes
 */
public class SetShape extends TurtleCommand {

	public SetShape(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double value = myArgNode[0].toValue();
		turtle.setShape(value);
		return value;
	}

}
