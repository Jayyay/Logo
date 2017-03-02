package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns negative of the values of expr
 */
public class Minus extends AbstractCommand {

	public Minus(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double execute() {
		return -myArgNode[0].toValue();
	}
	
}
