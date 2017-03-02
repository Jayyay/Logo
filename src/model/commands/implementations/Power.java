package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns expr1 raised to the power of expr2
 */
public class Power extends AbstractCommand {

	public Power(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		return Math.pow(myArgNode[0].toValue(), myArgNode[1].toValue());
	}

}
