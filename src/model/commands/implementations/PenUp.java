package model.commands.implementations;

import controller.UpdateListener;
import main.MsgType;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Puts pen up such that when the turtle moves,
 *it does not leave a trail.
 *Returns 0;
 */
public class PenUp extends TurtleCommand {
	
	public PenUp(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		if (turtle.isPenUp()) {
			myContext.setMessage(
				myResourceBundle.getString("Turtle") + " " +
				turtle.getID() + " " + myResourceBundle.getString("alreadyUp"),
				MsgType.WARNING
			);
			myUpdateListener.onDisplayShouldUpdate();
		}
		turtle.setPenUp(true);
		return 0;
	}

}
