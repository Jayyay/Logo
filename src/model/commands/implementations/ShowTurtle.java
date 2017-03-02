package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Makes turtle visible
 */
public class ShowTurtle extends TurtleCommand {
	
	public ShowTurtle(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		turtle.setTurtleShown(true);
		myUpdateListener.onVisualizationShouldUpdate();
		return 1;
	}

}
