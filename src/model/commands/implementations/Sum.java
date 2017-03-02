package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author Jay
 * Sum command
 */
public class Sum extends AbstractCommand {

	public Sum(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		double sum = 0;
		for (int i = 0; i < myArgNode.length; i++) {
			sum += myArgNode[i].toValue();
		}
		return sum;
	}
	
}
