package view;

import java.io.File;

/**
 * An interface for changing canvas property. DrawingCanvas should implements it
 * @author Yilun
 *
 */
public interface CanvasPropertyListener {

	/**
	 * change background color
	 * @param color an integer represented by (r<<16 | g<<8 | b), in which r, g, and b are integers between 0 and 255
	 */
	public void changeBackgroundColor(int color);
	
	/**
	 * Change turtle image
	 * @param idx the turtle whose image to change
	 * @param newImageFile new image file
	 */
	public void changeTurtleImage(int idx, File newImageFile);
	
}
