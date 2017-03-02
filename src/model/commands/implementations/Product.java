package model.commands.implementations;

import controller.UpdateListener;
import model.Context;
import model.commands.AbstractCommand;

/**
 * @author rachelbransom
 *Returns product of the values of expr1 and expr2
 */
public class Product extends AbstractCommand {

	public Product(UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myArity = 2;
	}

	@Override
	public double execute() {
		double product = 1;
		for (int i = 0; i < myArgNode.length; i++) {
			product *= myArgNode[i].toValue();
		}
		return product;
	}

}
