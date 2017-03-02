package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *if expr1 is not 0, expr2 (a command) will run. 
 *Otherwise, returns 0
 */
public class If extends AbstractCommand {

	/**
	 * @param updateListener
	 * @param context
	 * @param turtles
	 */
	public If(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		return (myArgNode[0].toValue() != 0) ? myArgNode[1].toValue() : 0;
	}

}
