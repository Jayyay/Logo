package view;

/**
 * A line property listener interface
 * This interface contains all methods to change line properties. 
 * All line properties are specific to each turtle. 
 * The DrawingCanvas should implement this interface
 * @author Yilun
 *
 */
public interface LinePropertyListener {

	/**
	 * Change line color
	 * @param idx turtle index
	 * @param color for the format, see the description in CanvasPropertyListener
	 */
	public void changeLineColor(int idx, int color);
	
	/**
	 * Change line type
	 * @param idx turtle index
	 * @param type one of "solid", "dashed", and "dotted"
	 */
	public void changeLineType(int idx, String type);
	
	/**
	 * Change line width
	 * @param idx turtle index
	 * @param width target width
	 */
	public void changeLineWidth(int idx, double width);
	
}
