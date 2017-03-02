package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns 1 if expr1 and expr2 are NOT equal.
 *Returns 0 otherwise
 */
public class NotEqual extends AbstractCommand{

	public NotEqual(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		return (myArgNode[0].toValue() != myArgNode[1].toValue()) ? 1 : 0;
	}

}
