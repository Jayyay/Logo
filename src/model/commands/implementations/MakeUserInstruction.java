package model.commands.implementations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import controller.UpdateListener;
import main.MsgType;
import model.Context;
import model.Function;
import model.StackFrame;
import model.commands.AbstractCommand;
import model.commands.ArgNode;
import model.commands.ListNode;

/**
 * TO command with supporting for recursion.
 * @author Jay
 */
public class MakeUserInstruction extends AbstractCommand {

	private String myFuncName;
	private List<String> myVarNames;
	private StackFrame myStackFrame;
	private String myFuncInString;
	
	public MakeUserInstruction(UpdateListener updateListener, Context context) {
		super(updateListener, context);
	}

	@Override
	public boolean prepare(Queue<String> args) {
		// slap a stackframe onto context for preparing.
		myStackFrame = new StackFrame();
		myContext.pushStackFrame(myStackFrame);
		
		// function name
		String funcName = args.poll();
		if (funcName == null || !myMatcher.getSymbol(funcName).equals(PROCEDURE)) {
			String errorStr = myResourceBundle.getString("IllegalFuncName") 
				+ " " + funcName;
			sendError(errorStr);
			return false;
		}
		myFuncName = funcName;
		
		// [ parameters(s) ]
		if (!extractVarNames(args)) {
			return false;
		}
		
		// [ command(s) ]
		myContext.setFunction(
			new Function(myFuncName, myVarNames, null, null)
		);
		Queue<String> argsCopy = new LinkedList<>(args);
		myArgNode = new ArgNode[1];
		myArgNode[0] = constructArgNode(args);
		if (myArgNode[0] == null || !(myArgNode[0] instanceof ListNode)) {
			return false;
		}
		
		// pop the stackframe we just pushed.
		myContext.popStackFrame();
		
		// jot down the function in string representation.
		myFuncInString = getStringForm(argsCopy, args);
		return true;
	}

	@Override
	public double execute() {
		myContext.setFunction(
			new Function(myFuncName, myVarNames, myFuncInString, (ListNode) myArgNode[0])
		);
		myUpdateListener.onTrackingShouldUpdate();
		return 1;
	}
	
	private boolean extractVarNames(Queue<String> args) {
		myVarNames = new ArrayList<>();
		String listStart = args.poll();
		if (listStart == null || !myMatcher.getSymbol(listStart).equals(LIST_START)) {
			return false;
		}
		
		while (true) {
			String varName = args.poll();
			if (varName == null) {
				return false;
			}
			String symbol = myMatcher.getSymbol(varName);
			switch (symbol) {
			case LIST_END:
				return true;
			case VARIABLE:
				if (myVarNames.contains(varName)) {
					myContext.setMessage(
						varName + myResourceBundle.getString("ParameterMoreThanOnce"),
						MsgType.ERROR
					);
					return false;
				}
				myVarNames.add(varName);
				myStackFrame.setVariable(varName, 0);
				break;
			default:
				sendUnknownIdentifierError(varName);
				return false;
			}
		}
	}

	/**
	 * Get the string form of this function.
	 */
	private String getStringForm(Queue<String> oldArgs, Queue<String> args) {
		StringBuilder sb = new StringBuilder();
		sb.append("TO ");
		sb.append(myFuncName);
		sb.append(" [ ");
		for (String parameter : myVarNames) {
			sb.append(parameter);
			sb.append(" ");
		}
		sb.append("]\n");
		while (oldArgs.size() > args.size()) {
			sb.append(oldArgs.poll());
			sb.append(" ");
		}
		return sb.toString();
	}
}
