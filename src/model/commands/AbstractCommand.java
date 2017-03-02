package model.commands;

import java.util.ArrayList;
import java.util.Queue;
import java.util.ResourceBundle;

import controller.UpdateListener;
import main.MsgType;
import model.Context;
import model.Matcher;
import model.StackFrame;
import model.commands.implementations.Procedure;

/**
 * An abstract class as a base class for every command.
 * Implementing Command interface, it provides some core functionalities
 * that are common to each command's implementations.
 * e.g. prepare/construct the nodes of the 'expression tree',
 * identify grouped arguments and set correspondingly,
 * finding the right command class using reflection,
 * and send error message, etc.
 * @author Jay, rachelbransom
 */
public abstract class AbstractCommand implements Command {
	
	protected static final String PACKAGE_PREFIX = "model.commands.implementations.";
	protected static final String RESOURCE_FILE_NAME = "resources/warnings/English";
	protected ResourceBundle myResourceBundle = ResourceBundle.getBundle(
		"resources/languages/English"
	);
	protected static Matcher myMatcher;
	
	protected static final String CONSTANT = "Constant";
	protected static final String GROUP_START = "GroupStart";
	protected static final String GROUP_END = "GroupEnd";
	protected static final String LIST_START = "ListStart";
	protected static final String LIST_END = "ListEnd";
	protected static final String NO_MATCH = "NoMatch";
	protected static final String PROCEDURE = "Procedure";
	protected static final String VARIABLE = "Variable";
	
	protected ArgNode[] myArgNode;
	protected int myArity;
	protected boolean myIsGroupMode;
	protected Context myContext;
	protected UpdateListener myUpdateListener;
	
	protected AbstractCommand(UpdateListener updateListener, Context context) {
		myUpdateListener = updateListener;
		myContext = context;
		myArity = 0;
		myIsGroupMode = false;
	}
	
	/**
	 * Static, same to every sub class.
	 * Set the matcher for the input string.
	 * @param matcher
	 */
	public static void setMatcher(Matcher matcher) {
		myMatcher = matcher;
	}
	
	@Override
	public void setGroupMode(boolean isGroupMode) {
		myIsGroupMode = isGroupMode;
	}
	
	@Override
	public boolean prepare(Queue<String> args) {
		return myIsGroupMode
			? prepareWithGroupMode(args)
			: prepareWithArityDefined(args);
	}
	
	/**
	 * prepare with Group-Mode, aka this command can take unlimited arguments.
	 */
	private boolean prepareWithGroupMode(Queue<String> args) {
		ArrayList<ArgNode> argNodeList = new ArrayList<>();
		while (true) {
			String nextArgPeeked = args.peek();
			if (nextArgPeeked == null) {
				return false;
			}
			if (myMatcher.getSymbol(nextArgPeeked).equals(GROUP_END)) {
				args.poll();
				if (argNodeList.size() < myArity) {
					sendError(myResourceBundle.getString("TooFewArguments"));
					return false;
				} else {
					myArgNode = argNodeList.toArray(
						new ArgNode[argNodeList.size()]
					);
					return true;
				}
			}
			ArgNode nextNode = constructArgNode(args);
			if (nextNode == null) { // construction of the argument node failed.
				return false;
			}
			argNodeList.add(nextNode);
		}
	}
	
	/**
	 * Prepare with Non-Group-Mode, aka this command can only take
	 * a certain number of arguments (defined as myArity in each command's
	 * implementation).
	 */
	private boolean prepareWithArityDefined(Queue<String> args) {
		myArgNode = new ArgNode[myArity];
		for (int i = 0; i < myArity; i++) {
			myArgNode[i] = constructArgNode(args);
			if (myArgNode[i] == null) {// construction of the argument node failed.
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Constructs an argument node with the args provided.
	 * @param args arguments in the form of a queue of strings
	 * @return an ArgNode object if successfully constructed, or null.
	 */
	protected ArgNode constructArgNode(Queue<String> args) {
		String arg = args.poll();
		if (arg == null) {
			return null;
		}
		String symbol = myMatcher.getSymbol(arg);
		switch (symbol) {
		case NO_MATCH:
			sendUnknownIdentifierError(arg);
			return null;
		case CONSTANT:
			return makeNumNode(arg);
		case VARIABLE:
			return makeVarNode(arg);
		case LIST_START:
			myContext.pushStackFrame(new StackFrame());
			ListNode listNode = makeListNode(args);
			myContext.popStackFrame();
			return listNode;
		default:
			return makeCmdNode(arg, symbol, args);
		}
	}
	
	private NumNode makeNumNode(String numStr) {
		return new NumNode(Double.parseDouble(numStr));
	}
	
	private VarNode makeVarNode(String varName) {
		if (myContext.getVariableWithName(varName) == null) {
			sendUnknownVarError(varName);
			return null;
		}
		return new VarNode(varName, myContext);
	}
	
	private ListNode makeListNode(Queue<String> args) {
		ListNode listNode = new ListNode(myContext);
		String cmdStr;
		while (true) {
			cmdStr = args.poll();
			String symbol = myMatcher.getSymbol(cmdStr);
			if (symbol.equals(LIST_END)) {
				return listNode;
			}
			CmdNode nextCmdNode = makeCmdNode(cmdStr, symbol, args);
			if (nextCmdNode == null) {
				return null;
			}
			listNode.addCmdNode(nextCmdNode);
		}
	}
	
	private CmdNode makeCmdNode(String cmdStr, String cmdSymbol, Queue<String> args) {
		try {
			boolean isGroupMode = false;
			if (cmdSymbol.equals(GROUP_START)) { // group, next arg HAS to be a command
				isGroupMode = true;
				if (args.size() == 0) {
					sendInvalidSyntaxError(cmdStr);
					return null;
				}
				cmdStr = args.poll();
				cmdSymbol = myMatcher.getSymbol(cmdStr);
			}
			Command childCmd = getCommandWithSymbol(cmdStr, cmdSymbol);
			childCmd.setGroupMode(isGroupMode);
			if (!childCmd.prepare(args)) {
				sendInvalidSyntaxError(cmdStr);
				return null;
			}
			return new CmdNode(childCmd); 
		} catch (Exception e) {
			sendInvalidSyntaxError(cmdStr);
			e.printStackTrace();
		}
		return null;
	}
	
	private Command getCommandWithSymbol(String cmdStr, String cmdSymbol) throws Exception {
		Class<?> cmdClass = Class.forName(PACKAGE_PREFIX + cmdSymbol);
		Command childCmd;
		if (cmdSymbol.equals(PROCEDURE)) {
			childCmd = new Procedure(cmdStr, myUpdateListener, myContext);
		} else {
			childCmd = (Command) cmdClass.getDeclaredConstructor(
				UpdateListener.class,
				Context.class
			).newInstance(
				myUpdateListener,
				myContext
			);
		}
		return childCmd;
	}
	
	/**
	 * sends a message to the front end to tell the user there was an unknown
	 * identifier in the command
	 * @param string to display to the user
	 */
	protected void sendUnknownIdentifierError(String str) {
		String errorStr = myResourceBundle.getString("UnknownIdentifier") + " " + str;
		sendError(errorStr);
	}
	
	/**
	 * sends a message to the front end to tell the user there was invalid
	 * syntax in their command
	 * @param commandString that is invalid to show the user
	 */
	protected void sendInvalidSyntaxError(String commandString) {
		String errorStr = myResourceBundle.getString("InvalidSyntax") + " " + commandString;
		sendError(errorStr);
	}
	
	/**
	 * sends a message to the front end to tell the user there was an error with
	 * the variable, varName
	 * @param varName
	 */
	protected void sendUnknownVarError(String varName) {
		String errorStr = myResourceBundle.getString("UnknownVar") + " " + varName;
		sendError(errorStr);
	}
	
	/**
	 * sends a message to the front end to tell the user there was error
	 * with the string
	 * @param errorStr
	 */
	protected void sendError(String errorStr) {
		myContext.setMessage(errorStr, MsgType.ERROR);
		myUpdateListener.onDisplayShouldUpdate();
	}
	
	/**
	 * sends a message to the front end to tell the user the result of their
	 * command, in the case the command was successful
	 * @param resultValue
	 */
	protected void sendSuccessMessage(double resultValue) {
		myContext.setMessage(Double.toString(resultValue), MsgType.RESULT);
		myUpdateListener.onDisplayShouldUpdate();
	}
}
