package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Sets the color of the pen strikes, i.e. the path behind
 *the turtle. The doubles correspond to colors pre-decided.
 */
public class SetPenColor extends TurtleCommand {

	public SetPenColor(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double value = myArgNode[0].toValue();
		turtle.setPenColor(value);
		return value;
	}

}
