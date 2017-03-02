package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns 1 if expr is 0
 *Returns 0 if expr is non-zero
 */
public class Not extends AbstractCommand {

	public Not(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double execute() {
		return  (myArgNode[0].toValue()==0) ? 1 : 0;
	}
	
}
