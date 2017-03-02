package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * ID
 * @author Rachel, Jay
 */
public class ID extends AbstractCommand {

	public ID(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double execute() {
		return myContext.getCanvasModel().getActiveTurtleID();
	}

}
