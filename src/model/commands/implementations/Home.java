package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *moves the turtle to the center of the screen (0,0)
 *currently returns distance moved by first turtle
 */
public class Home extends TurtleCommand {

	public Home(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double distanceToReturn = distanceMoved(turtle);
		turtle.reset();
		return distanceToReturn;
	}
	
	private double distanceMoved(TurtleModel t){
		return Math.sqrt(Math.pow(t.getCoord().getX(),2) + Math.pow(t.getCoord().getY(),2));
	}
	

}
