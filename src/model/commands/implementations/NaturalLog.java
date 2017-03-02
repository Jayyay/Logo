package model.commands.implementations;

import controller.UpdateListener;
import main.MsgType;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns natural log of expr
 */
public class NaturalLog extends AbstractCommand {

	public NaturalLog(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 1;
	}

	@Override
	public double execute() {
		double value = myArgNode[0].toValue();
		if (value == 0) {
			myContext.setMessage(myResourceBundle.getString("logOfZero"), MsgType.WARNING);
			myUpdateListener.onDisplayShouldUpdate();
			return 0;
		}
		else {
			return Math.log(value);
		}
	}

}
