package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *makes turtle invisible
 *return 0
 */
public class HideTurtle extends TurtleCommand {
	
	public HideTurtle(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		turtle.setTurtleShown(false);
		return 0;
	}

}
