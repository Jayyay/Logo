package model.commands.implementations;

import controller.UpdateListener;
import main.MsgType;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns quotient of the values of expr1 and expr2
 */
public class Quotient extends AbstractCommand {

	public Quotient(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		Double dividend = myArgNode[0].toValue();
		Double divisor = myArgNode[1].toValue();
		if (divisor == 0) {
			myContext.setMessage(myResourceBundle.getString("DivideZero"), MsgType.WARNING);
			myUpdateListener.onDisplayShouldUpdate();
			return 0;
		}
		else {
			return dividend / divisor;
		}
	}

}
