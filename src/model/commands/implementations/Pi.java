package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author Jay
 *This class returns Pi
 */
public class Pi extends AbstractCommand {

	public Pi(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double execute() {
		return Math.PI;
	}

}
