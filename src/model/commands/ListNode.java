package model.commands;

import java.util.ArrayList;
import java.util.List;

import model.Context;
import model.StackFrame;

/**
 * A list of commands as an argument node.
 * @author Jay
 */
public class ListNode implements ArgNode {
	
	private List<CmdNode> myCmdList;
	private Context myContext;
	
	public ListNode(Context context) {
		myCmdList = new ArrayList<>();
		myContext = context;
	}
	
	/**
	 * adds a command node to the list of commands
	 * @param node to be added
	 */
	public void addCmdNode(CmdNode node) {
		myCmdList.add(node);
	}
	
	@Override
	public double toValue() {
		myContext.pushStackFrame(new StackFrame());
		double value = 0;
		for (CmdNode cmdNode : myCmdList) {
			value = cmdNode.toValue();
		}
		myContext.popStackFrame();
		return value;
	}
	
	/**
	 * @return list of all command nodes
	 */
	public List<CmdNode> getCommandList(){
		return myCmdList;
	}

}