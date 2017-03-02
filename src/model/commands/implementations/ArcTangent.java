package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 * This class returns the result of applying the arctan function
 * to the given argNode value
 */
public class ArcTangent extends AbstractCommand {

	public ArcTangent(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double execute() {
		return Math.atan(Math.toRadians(myArgNode[0].toValue()));
	}
}
