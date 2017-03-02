package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *This class returns the result of applying the sin function
 * to the given argNode value
 */
public class Sine extends AbstractCommand {

	public Sine(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double execute() {
		return Math.sin(Math.toRadians(myArgNode[0].toValue()));
	}
	
}
