package model.commands.implementations;

import controller.UpdateListener;
import javafx.geometry.Point2D;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *Moves the turtle back by expr steps, returns the distance moved by the turtle
 */
public class Backward extends TurtleCommand {

	public Backward(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double delta = 0;
		for (int i = 0; i < myArgNode.length; i++) {
			delta = myArgNode[i].toValue();
			turtle.setCoord(newCoord(delta, turtle));
		}
		return delta;
	}

	private Point2D newCoord(Double backwardsAmount, TurtleModel turtle) {
		double turtleAngle = Math.toRadians(turtle.getHeadDegree());
		double offsetX = backwardsAmount * Math.sin(turtleAngle);
		double offsetY = backwardsAmount * Math.cos(turtleAngle);
		return new Point2D(turtle.getX() - offsetX, turtle.getY() - offsetY);
	}
}
