package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns 1 if expr1 and expr2 are non-zero,
 *Otherwise 0
 */
public class Or extends AbstractCommand {

	public Or(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		return ((myArgNode[0].toValue()!= 0) || (myArgNode[1].toValue()!=0)) ? 1 : 0;
	}

}
