package model.commands;

/**
 * A command as an argument node, which contains an actual command 
 * which will result in a value.
 * @author Jay
 */
public class CmdNode implements ArgNode {

	private Command myCmd;
	
	public CmdNode(Command cmd) {
		myCmd = cmd;
	}
	
	@Override
	public double toValue() {
		return myCmd.execute();
	}
	
}
