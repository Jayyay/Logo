package model;

import java.util.Map;

import javafx.geometry.Point2D;

/**
 * A Visualizable that provides necessary information for visualization.
 * @author Jay, Rachel
 */
public interface Visualizable {
	
	/* Environment */
	/**
	 * Get whether or not a clear-screen should be performed.
	 * @return true if screen needs to be cleared, false if not.
	 */
	boolean shouldClearScreen();
	
    /**
     * @return double representing the color of the background
     */
	double getBackgroundColor();
	
	/* Turtle */
	/**
	 * Get whether the pen is up, aka no trail should be drawn.
	 * @return a map with key being turtleID,
	 * and value being a boolean true if pen for that turtle is up, false if down.
	 */
	Map<Integer, Boolean> isPenUp();
    
    /**
     * Get whether the turtle is shown
     * @return a map with key being turtleID,
     * and value being a boolean true if that turtle should be shown, false if not.
     */
	Map<Integer, Boolean> isTurtleShown();
    
    /**
     * Get the current coordinate of the turtle.
     * @return a map with key being turtleID,
     * and value being a Point2D indicating the turtle's (x, y), with the center being (0, 0).
     */
	Map<Integer, Point2D> getCoord();
    
    /**
     * Get the absolute degree of the direction of the turtle's head.
     * @return a map with key being turtleID,
     * and value being a double indicating the degree of the turtle.
     */
	Map<Integer, Double> getHeadDegree();
    
    /**
     * Gets the color of the turtle's path
     * @return a map with key being turtleID,
     * and value being a double representing the color of the turtle's pen strike
     */
	Map<Integer, Double> getPenColor();
    
    /**
     * Gets the shape of the turtle
     * @return a map with key being turtleID,
     * and value being shape of the turtle.
     */
	Map<Integer, Double> getShape();
    
    /**
     * @return a map with key being turtleID,
     * and value being the size of the turtle's path
     */
	Map<Integer, Double> getPenSize();
    
}
