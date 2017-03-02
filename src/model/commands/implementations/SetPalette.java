package model.commands.implementations;

import controller.UpdateListener;
import main.MsgType;
import model.Context;
import model.TurtleModel;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom Sets color corresponding at given index to given r g b
 *         color values returns the index of the turtle whose color has been
 *         changed
 */
public class SetPalette extends AbstractCommand {

	TurtleModel myTurtle;

	public SetPalette(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 4;
	}

	@Override
	public double execute() {
		int myTurtleID = (int) myArgNode[0].toValue();
		checkIfTurtleExists(myTurtleID);

		int r = (int) myArgNode[1].toValue();
		int g = (int) myArgNode[2].toValue();
		int b = (int) myArgNode[3].toValue();

		if (r > 255 || g > 255 || b > 255) {
			myContext.setMessage(myResourceBundle.getString("outOfColorRange"), MsgType.WARNING);
			return 0;
		}
		return r << 16 | g << 8 | b;
	}

	private void checkIfTurtleExists(int id) {
		if (!myContext.getCanvasModel().getTurtleMap().containsKey(id)) {
			myTurtle = new TurtleModel(id);
			myContext.getCanvasModel().getTurtleMap().put(id, myTurtle);
			myContext.setMessage(myResourceBundle.getString("turtleDoesntExist"), MsgType.WARNING);
		} else {
			myTurtle = myContext.getCanvasModel().getTurtleMap().get(id);
		}
	}

}
