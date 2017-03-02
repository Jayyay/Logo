package model.commands;

import java.util.Queue;

/**
 * A command that can be prepared (after parsing and ready to execute)
 * and executed (operate on the turtle or logic)
 * @author Jay
 */
public interface Command {
	/**
	 * Set the group mode of command.
	 * Aka whether it can take grouped(unlimited) arguments.
	 * @param isGroupMode boolean flag.
	 */
	void setGroupMode(boolean isGroupMode);
	
	/**
	 * Prepare the command with the argument list (queue) provided.
	 * And return all the unneeded args as a queue (if preparation succeeds)
	 * or return null if preparation fails.
	 * @param args, arguments in the form of a queue of strings
	 * @return a boolean indicating whether prepare succeeds
	 * true if valid and ready to execute, false otherwise.
	 */
	boolean prepare(Queue<String> args);
	
	/**
	 * Execute the command.
	 * @return result (all the commands return a double)
	 */
	double execute();

}
