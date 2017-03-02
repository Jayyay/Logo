package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns 1 if expr1 and expr2 are equal
 *Otherwise, returns 0
 */
public class Equal extends AbstractCommand {

	public Equal(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		double compare = myArgNode[0].toValue();
		for (int i = 1; i < myArgNode.length; i++) {
			if (compare != myArgNode[i].toValue()) {
				return 0;
			}
		}
		return 1;
	}

}
