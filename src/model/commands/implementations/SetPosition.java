package model.commands.implementations;

import controller.UpdateListener;
import javafx.geometry.Point2D;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Moves turtle to an absolute screen position where
 *(0,0) is the center of the screen
 */
public class SetPosition extends TurtleCommand {

	private double x;
	private double y;
	
	public SetPosition(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		x = myArgNode[0].toValue();
		y = myArgNode[1].toValue();
		turtle.setCoord(new Point2D(x,y));
		return distanceMoved(turtle);
	}
	
	private double distanceMoved(TurtleModel turtle) {
		return Math.sqrt(Math.pow(x-turtle.getX(), 2) + Math.pow(y-turtle.getY(), 2));
	}

}
