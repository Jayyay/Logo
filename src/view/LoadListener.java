package view;

/**
 * Listener interface for loading saved files
 * @author Sam, Yilun
 *
 */
public interface LoadListener {
	
	/**
	 * Load specific file
	 * @param filePath
	 * @param commandHistory
	 */
	public void load(String filePath, String commandHistory);
}
