package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Moves the turtle's heading expr degrees to the right
 */
public class Right extends TurtleCommand {

	public Right(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double degreeToTurnRight = 0;
		for (int i = 0; i < myArgNode.length; i++) {
			degreeToTurnRight = myArgNode[i].toValue();
			turtle.setHeadDegree(turtle.getHeadDegree() + degreeToTurnRight);
		}
		return degreeToTurnRight;
	}
}
