package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import main.MsgType;

/**
 * A context model (running environment) containing all the context information.
 * e.g. all the variables, procedures, stack frames and a CanvasModel etc.
 * It is also exposed to frontend as a Displayable and a Trackable.
 * @author Jay
 */
public class Context implements Displayable, Trackable {
	
	private Map<String, Variable> myVariablePool;
	private Map<String, Function> myFunctionPool;
	private Stack<StackFrame> myStackFrames;
	
	
	// For Visualizable
	private CanvasModel myCanvasModel;
	
	// For Displayable
	private String myMessage;
	private MsgType myMessageType;
	
	// For Tracakble
	private List<String> myCmdHistory;
	
	public Context() {
		myVariablePool = new HashMap<>();
		myFunctionPool = new HashMap<>();
		myStackFrames = new Stack<>();
		myCanvasModel = new CanvasModel();
		myCmdHistory = new ArrayList<>();
	}
	
	public CanvasModel getCanvasModel() {
		return myCanvasModel;
	}
	
	/**
	 * Starting from the uppermost stack, all the way down to the heap, 
	 * get the "closest" (in terms of scope) Variable object associated with the name.
	 * @param varName the name of the variable (must start with a colon, e.g. ":a").
	 * @return Variable object if one exists, or null.
	 */
	public Variable getVariableWithName(String varName) {
		for (int i = myStackFrames.size() - 1; i >= 0; i--) {
			Variable var = myStackFrames.get(i).getVariableWithName(varName);
			if (var != null) {
				return var;
			}
		}
		return myVariablePool.get(varName);
	}
	
	/**
	 * Create or update a named variable with a double value.
	 * This value will have global access and exists in the heap.
	 * @param varName the name of the variable.
	 * @param value double value of the variable.
	 */
	public void setVariable(String varName, double value) {
		if (!myStackFrames.isEmpty()) {
			myStackFrames.peek().setVariable(varName, value);
			return;
		}
		if (myVariablePool.containsKey(varName)) {
			myVariablePool.get(varName).setMyValue(value);
		} else {
			myVariablePool.put(varName, new Variable(varName, value));
		}
	}
	
	/**
	 * Get the Function associated with the name.
	 * @param funcName function name.
	 * @return Function object.
	 */
	public Function getFunctionWithName(String funcName) {
		return myFunctionPool.get(funcName);
	}
	
	/**
	 * Put the Function object into the context.
	 * @param function
	 */
	public void setFunction(Function function) {
		myFunctionPool.put(function.getMyName(), function);
	}
	
	/**
	 * Push a stack frame as the current stack frame.
	 * @param stackFrame
	 */
	public void pushStackFrame(StackFrame stackFrame) {
		myStackFrames.push(stackFrame);
	}
	
	/**
	 * Pop(remove) the current stack frame.
	 */
	public void popStackFrame() {
		myStackFrames.pop();
	}
	
	/**
	 * Clear the stack frames.
	 */
	public void clearStackFrame() {
		myStackFrames.removeAllElements();
	}
	
	/**
	 * Set the feedback message to be displayed.
	 * @param messageToSet
	 * @param messageType
	 */
	public void setMessage(String messageToSet, MsgType messageType) {
		myMessage = messageToSet;
		myMessageType = messageType;
	}
	
	/**
	 * Add the command string to history list.
	 * @param cmdStr
	 */
	public void addCmdToHistory(String cmdStr) {
		myCmdHistory.add(cmdStr);
	}
	
	/* Implementations of Visualizable API for frontend */
	@Override
	public MsgType getMsgType() {
		return myMessageType;
	}

	@Override
	public String getMessage() {
		return myMessage;
	}

	/* Implementations of Trackable API for frontend */
	@Override
	public List<Variable> getAllVariables() {
		List<Variable> varList = new ArrayList<>();
		for (String varKey : myVariablePool.keySet()) {
			varList.add(myVariablePool.get(varKey));
		}
		return varList;
	}

	@Override
	public List<Function> getAllFunctions() {
		List<Function> funcList = new ArrayList<>();
		for (String funcKey : myFunctionPool.keySet()) {
			funcList.add(myFunctionPool.get(funcKey));
		}
		return funcList;
	}

	@Override
	public List<String> getCmdHistory() {
		return myCmdHistory;
	}

	@Override
	public List<Integer> getAllTurtleIDs() {
		return new ArrayList<>(myCanvasModel.getTurtleMap().keySet());
	}

}
