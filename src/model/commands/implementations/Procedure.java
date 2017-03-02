package model.commands.implementations;

import java.util.Queue;

import controller.UpdateListener;
import model.Context;
import model.Function;
import model.StackFrame;
import model.commands.AbstractCommand;

/**
 * User-defined procedure command with supporting for recursion.
 * @author Jay
 */
public class Procedure extends AbstractCommand {

	private String myFuncName;
	
	public Procedure(String funcName, UpdateListener updateListener, Context context) {
		super(updateListener, context);
		myFuncName = funcName;
	}

	@Override
	public boolean prepare(Queue<String> args) {
		Function func = myContext.getFunctionWithName(myFuncName);
		if (func == null) {
			return false;
		}
		myArity = func.getVarNames().size();
		return super.prepare(args);
	}
	
	@Override
	public double execute() {
		Function func = myContext.getFunctionWithName(myFuncName);
		StackFrame stackFrame = new StackFrame();
		for (int i = 0; i < func.getVarNames().size(); i++) {
			stackFrame.setVariable(
				func.getVarNames().get(i),
				myArgNode[i].toValue()
			);
		}
		myContext.pushStackFrame(stackFrame);
		double finalValue = func.getListNode().toValue();
		myContext.popStackFrame();
		return finalValue;
	}
	
}
