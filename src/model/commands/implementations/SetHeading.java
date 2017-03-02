package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Turns turtle to an absolute heading
 *Returns the number of degrees moved
 */
public class SetHeading extends TurtleCommand {

	public SetHeading(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double degree = myArgNode[0].toValue();
		turtle.setHeadDegree(degree);
		return degree;
	}
	

}
