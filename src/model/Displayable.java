package model;

import main.MsgType;

/**
 * A Displayable that provides necessary information for text displaying.
 * @author Jay
 */
public interface Displayable {
	
	/**
	 * Get the type of the message to be displayed.
	 * @return MsgType enum indicating the type.
	 */
	MsgType getMsgType();
	
	/**
	 * Get the message that should to be displayed.
	 * @return String text.
	 */
    String getMessage();
}
