package model.commands.implementations;

import controller.UpdateListener;
import main.MsgType;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *puts pen down such that when the turtle moves, it leaves
 *a trail.
 *Returns 1
 */
public class PenDown extends TurtleCommand {

	public PenDown(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		if (!turtle.isPenUp()) {
			myContext.setMessage(
				myResourceBundle.getString("Turtle") + " " +
				turtle.getID() + " " + myResourceBundle.getString("alreadyDown"),
				MsgType.WARNING
			);
			myUpdateListener.onDisplayShouldUpdate();
		}
		turtle.setPenUp(false);
		return 1;
	}
	

}
