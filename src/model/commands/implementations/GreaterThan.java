package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns 1 if the value of expr1 is strictly greater than
 *the value of expr2, otherwise return 0
 */
public class GreaterThan extends AbstractCommand {

	public GreaterThan(UpdateListener updateListener, Context context ) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		return (myArgNode[0].toValue() > myArgNode[1].toValue()) ? 1 : 0;
	}
	
}
