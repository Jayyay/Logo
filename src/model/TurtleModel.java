package model;



import javafx.geometry.Point2D;

/**
 * A turtle model, containing all the necessary information of the turtle.
 * e.g. position, heading, pen state etc.
 * It is also exposed to frontend as a Visualizable.
 * @author Rachel, Jay
 */
public class TurtleModel {
	private int myID;
	private double myHeadDegree, myPenColor, myShape, myPenSize;
	private boolean myIsShown, myIsPenUp;
	private Point2D myCoord;
	
	
	/**
	 * creates a turtle with all the default values
	 * @param id
	 */
	public TurtleModel(int id){
		myID = id;
		myHeadDegree = 0;
		myIsShown = true;
		myIsPenUp = false;
		myCoord = new Point2D(0,0);
	}
	
	/**
	 * @return the ID of the turtle
	 */
	public int getID() {
		return myID;
	}
	
	/**
	 * changes state of pen up
	 * @param penToBeUp
	 */
	public void setPenUp(boolean penToBeUp) {
		myIsPenUp = penToBeUp;
	}

	/**
	 * changes whether turtle is shown or not
	 * @param turtleToBeShown
	 */
	public void setTurtleShown(boolean turtleToBeShown) {
		myIsShown = turtleToBeShown;
	}
	
	/**
	 * sets the coordinates of the turtle
	 * @param coordToSet
	 */
	public void setCoord(Point2D coordToSet) {
		myCoord = coordToSet;
	}
	
	/**
	 * sets the shape of the turtle
	 * @param shapeDouble
	 */
	public void setShape(Double shapeDouble) {
		myShape = shapeDouble;
	}
	
	/**
	 * sets the pen color of the path of the turtle
	 * @param penColorDouble
	 */
	public void setPenColor(Double penColorDouble) {
		myPenColor = penColorDouble;
	}

	
	/**
	 * sets the pen size of the path of the turtle
	 * @param sizeDouble
	 */
	public void setPenSize(Double sizeDouble) {
		myPenSize = sizeDouble;
	}
	
	/**
	 * @return turtle's x co-ordinate
	 */
	public double getX() {
		return myCoord.getX();
	}

	/**
	 * @return turtle's y co-ordinate
	 */
	public double getY() {
		return myCoord.getY();
	}
	
	/**
	 * sets the turtle to be facing degree
	 * @param degree
	 */
	public void setHeadDegree(Double degree) {
		myHeadDegree = degree;
	}
	
	/* Implementations of Visualizable API for frontend */
	
	/**
	 * @return whether turtle's pen is up or not
	 */
	public boolean isPenUp() {
		return myIsPenUp;
	}
	
	/**
	 * @return whether turtle is shown
	 */
	public boolean isTurtleShown() {
		return myIsShown;
	}
	
	/**
	 * @return co-ordinates of turtle 
	 */
	public Point2D getCoord() {
		return myCoord;
	}
	
	/**
	 * @return turtle's head degree
	 */
	public double getHeadDegree() {
		return myHeadDegree;
	}

	/**
	 * @return turtle's pen color
	 */
	public double getPenColor() {
		return myPenColor;
	}

	/**
	 * @return shape of turtle
	 */
	public double getShape() {
		return myShape;
	}

	/**
	 * @return size of turtle's pen
	 */
	public double getPenSize() {
		return myPenSize;
	}
	
	/**
	 * resets a turtle to the middle of 
	 * the screen, facing upwards
	 */
	public void reset() {
		this.setCoord(new Point2D(0,0));
		this.setHeadDegree(0.0);
	}
}

