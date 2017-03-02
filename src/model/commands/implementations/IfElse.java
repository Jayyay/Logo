package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *
 */
public class IfElse extends AbstractCommand {

	public IfElse(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 3;
	}

	@Override
	public double execute() {
		return (myArgNode[0].toValue() != 0) ? myArgNode[1].toValue() : myArgNode[2].toValue();

	}

}
