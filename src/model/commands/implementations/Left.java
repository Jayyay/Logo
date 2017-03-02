package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachel
 *turns turtle counterclockwise by degrees angle
 *returns value of degrees
 */
public class Left extends TurtleCommand {

	public Left(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double degreesLeftToMove = 0;
		for (int i = 0; i < myArgNode.length; i++) {
			degreesLeftToMove = myArgNode[i].toValue();
			turtle.setHeadDegree(turtle.getHeadDegree()-degreesLeftToMove);
		}
		return degreesLeftToMove;
	}
}
