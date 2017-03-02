package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 *erases turtle's trails and sends it to the home position
 *currently returns turtle's distance moved
 */
public class ClearScreen extends TurtleCommand {

	/**
	 * @param context
	 * @param turtles
	 */
	public ClearScreen(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		double distanceToReturn = distanceMoved(turtle);
		myContext.getCanvasModel().setClearScreen(true);
		turtle.reset();
		return distanceToReturn;
	}
	
	private double distanceMoved(TurtleModel t){
		return Math.sqrt(Math.pow(t.getCoord().getX(),2) + Math.pow(t.getCoord().getY(),2));
	}

}
