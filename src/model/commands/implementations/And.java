package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns 1 if firstArgNode and secondArgNode are non-zero
 *Otherwise returns 0
 */
public class And extends AbstractCommand {

	public And(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		for (int i = 0; i < myArgNode.length; i++) {
			if (myArgNode[0].toValue() == 0) {
				return 0;
			}
		}
		return 1;
	}
	
}
