package model.commands.implementations;

import controller.UpdateListener;
import main.MsgType;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *This class returns the result of applying the tan function
 * to the given argNode value
 */
public class Tangent extends AbstractCommand {
	
	public Tangent(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double execute() {
		double delta = Math.toRadians(myArgNode[0].toValue());
		try {
			return Math.tan(delta);
		} catch (Exception e){
			myContext.setMessage(myResourceBundle.getString("tanIllegal"), MsgType.WARNING);
			return Math.tan(0);
		}
	}
}
