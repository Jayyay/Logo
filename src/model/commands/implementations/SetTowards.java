package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.TurtleModel;
import model.commands.TurtleCommand;

/**
 * @author rachelbransom
 * Turns turtle to face the point(x,y) where (0,0) is the
 * centre of the screen
 * returns number of degrees turtle turned
 */
public class SetTowards extends TurtleCommand {
	
	private double x;
	private double y;

	public SetTowards(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double executeAsTurtle(TurtleModel turtle) {
		x = myArgNode[0].toValue();
		y = myArgNode[1].toValue();
		turtle.setHeadDegree(Math.toDegrees(angleOfCoord()));
		return 0;
	}
	
	private double angleOfCoord(){
		return Math.tan(x/y);
	}

}
