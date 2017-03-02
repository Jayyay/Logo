package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *returns the number of turtles created so far
 */
public class Turtles extends AbstractCommand {

	public Turtles(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 0;
	}

	@Override
	public double execute() {
		return myContext.getCanvasModel().getTurtleMap().size();
	}

}
