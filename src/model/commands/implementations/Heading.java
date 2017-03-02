package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 * Returns the first turtle's heading in degrees
 */
public class Heading extends TurtleCommand {

	public Heading(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		return turtle.getHeadDegree();
	}

}
