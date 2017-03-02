package model.commands.implementations;

import controller.UpdateListener;
import javafx.geometry.Point2D;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachel
 * moves each turtle forward by expr amount
 * Currently returns the amount moved
 */

public class Forward extends TurtleCommand {
	
	public Forward(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	protected double executeAsTurtle(TurtleModel turtle) {
		double delta = 0;
		for (int i = 0; i < myArgNode.length; i++) {
			delta = myArgNode[i].toValue();
			turtle.setCoord(newCoord(delta, turtle));
		}
		return delta;
	}
	
	private Point2D newCoord(Double forwardAmount, TurtleModel turtle){
		double turtleAngle = Math.toRadians(turtle.getHeadDegree());
		double offsetX = forwardAmount * Math.sin(turtleAngle);
		double offsetY = forwardAmount * Math.cos(turtleAngle);
		return new Point2D(turtle.getX()+offsetX, turtle.getY()+offsetY);
	}

}
