package view;

import javafx.scene.control.TextArea;

/**
 * Terminal function interface. Terminal should implement this and use this to accept command from other classes
 * @author Sam, Yilun
 *
 */
public interface TerminalFunctionInterface {
	
	/**
	 * Get the input TextArea
	 * @return
	 */
	public TextArea getInputPane();
	
	/**
	 * Submit text in the input area
	 * @return
	 */
	public String submitText();
}
