package view;

/**
 * Listener for saving workspace file
 * @author Sam, Yilun
 *
 */
public interface SaveListener {
	
	/**
	 * Save current workspace to specific file
	 * @param filePath
	 */
	public void save(String filePath);

}
