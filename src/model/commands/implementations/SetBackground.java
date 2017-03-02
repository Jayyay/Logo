package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *sets the background color of the canvas to the color associated
 *with the double value given
 */
public class SetBackground extends AbstractCommand {

	public SetBackground(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double execute() {
		double value = myArgNode[0].toValue();
		myContext.getCanvasModel().setBackgroundColor(value);
		myUpdateListener.onVisualizationShouldUpdate();
		return value;
	}

}
