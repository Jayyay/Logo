package view;

/**
 * An interface for terminal delegate. This is used for terminal to notify its delegate that an
 * effective submission has occurred (e.g. when Enter key is pressed)
 * @author Yilun
 *
 */
public interface TerminalDelegateInterface {

	/**
	 * submission occurred
	 */
	public void submitKeyed();
	
}
