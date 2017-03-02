package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *This class returns a random number, with the argNode being
 *the maximum possible value.
 */
public class Random extends AbstractCommand {

	public Random(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}
	
	@Override
	public double execute() {
		return Math.random() * myArgNode[0].toValue();
	}

}
