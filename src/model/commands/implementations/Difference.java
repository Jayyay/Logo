package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *returns difference of the values of expr1 and expr2
 */
public class Difference extends AbstractCommand {

	public Difference(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		return myArgNode[0].toValue() - myArgNode[1].toValue();
	}

}
