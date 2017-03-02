package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *changes the size of the lines i.e. turtle path to the value of
 *expr
 *returns the new size of the lines
 */
public class SetPenSize extends TurtleCommand {

	/**
	 * @param updateListener
	 * @param context
	 */
	public SetPenSize(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double penSize = myArgNode[0].toValue();
		turtle.setPenSize(penSize);
		return penSize;
	}

}
