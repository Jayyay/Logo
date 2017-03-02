package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns the remainder on dividing the values on expr1 and expr2
 */
public class Remainder extends AbstractCommand {

	public Remainder(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		return myArgNode[0].toValue() % myArgNode[1].toValue();
	}

}
