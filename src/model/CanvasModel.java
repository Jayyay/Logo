package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Point2D;

/**
 * This class contains all the back-end related information about
 * the canvas like the collection of created turtles, told turtles,
 * and settings for the screen. The reason screen settings like
 * myShouldClearScreen is stored here because the user can change
 * these settings using commands in the terminal, thus the canvas
 * model needs to know their state so they can, for example, tell the
 * user what color the screen is.
 * @author rachelbransom, Jay
 */

public class CanvasModel implements Visualizable {

	private static final int BACKGROUND_DEFAULT = 12582899;
	
	private Map<Integer,TurtleModel> myTurtleMap;
	private List<TurtleModel> myTurtlesTold;
	private int myActiveTurtleID;
	private double myBackgroundColor = BACKGROUND_DEFAULT;
	private boolean myShouldClearScreen;
	
	/**
	 * constructor for canvas model with default settings
	 */
	public CanvasModel() {
		TurtleModel defaultTurtle = new TurtleModel(0);
		myTurtleMap = new HashMap<>();
		myTurtleMap.put(0, defaultTurtle);
		myTurtlesTold = new ArrayList<>();
		myTurtlesTold.add(defaultTurtle);
		myActiveTurtleID = 0;
	}
	
	/**
	 * Changes the state of myShouldClearScreen
	 * @param screenToClear
	 */
	public void setClearScreen(boolean screenToClear){
		myShouldClearScreen = screenToClear;
	}
	
	/**
	 * Sets background color to a double representing 
	 * a color
	 * @param backgroundColorDouble
	 */
	public void setBackgroundColor(Double backgroundColorDouble) {
		myBackgroundColor = backgroundColorDouble;
	}
	
	/**
	 * Changes the active turtle to which turtle ID is passed
	 * @param activeTurtleID
	 */
	public void setActiveTurtleID(int activeTurtleID) {
		myActiveTurtleID = activeTurtleID;
	}
	
	/**
	 * getter for the active turtle's ID
	 * @return active turtle's ID
	 */
	public int getActiveTurtleID() {
		return myActiveTurtleID;
	}
	
	@Override
	public boolean shouldClearScreen() {
		boolean returnValue = myShouldClearScreen;
		/*
		 * need to set it to be false to avoid always clearscreen
		 * after the first CS command is issued.
		 */
		myShouldClearScreen = false;
		return returnValue;
	}

	@Override
	public Map<Integer, Boolean> isPenUp() {
		Map<Integer, Boolean> myIsPenUpMap = new HashMap<Integer, Boolean>();
		for (Integer turtleID : myTurtleMap.keySet()) {
			myIsPenUpMap.put(turtleID, myTurtleMap.get(turtleID).isPenUp());
		}
		return myIsPenUpMap;
	}

	@Override
	public Map<Integer, Boolean> isTurtleShown() {
		Map<Integer, Boolean> myIsTurtleShownMap = new HashMap<Integer, Boolean>();
		for (Integer turtleID : myTurtleMap.keySet()) {
			myIsTurtleShownMap.put(turtleID, myTurtleMap.get(turtleID).isTurtleShown());
		}
		return myIsTurtleShownMap;
	}

	@Override
	public Map<Integer, Point2D> getCoord() {
		Map<Integer, Point2D> myTurtleCoordMap = new HashMap<Integer, Point2D>();
		for (Integer turtleID : myTurtleMap.keySet()) {
			myTurtleCoordMap.put(turtleID, myTurtleMap.get(turtleID).getCoord());
		}
		return myTurtleCoordMap;
	}

	@Override
	public Map<Integer, Double> getHeadDegree() {
		Map<Integer, Double> myTurtleHeadDegreeMap = new HashMap<Integer, Double>();
		for (Integer turtleID : myTurtleMap.keySet()) {
			myTurtleHeadDegreeMap.put(turtleID, myTurtleMap.get(turtleID).getHeadDegree());
		}
		return myTurtleHeadDegreeMap;
	}

	@Override
	public Map<Integer, Double> getPenColor() {
		Map<Integer, Double> myTurtlePenColorMap = new HashMap<Integer, Double>();
		for (Integer turtleID : myTurtleMap.keySet()) {
			myTurtlePenColorMap.put(turtleID, myTurtleMap.get(turtleID).getPenColor());
		}
		return myTurtlePenColorMap;
	}

	@Override
	public double getBackgroundColor() {
		return myBackgroundColor;
	}

	@Override
	public Map<Integer, Double> getShape() {
		Map<Integer, Double> myTurtleShapeMap = new HashMap<Integer, Double>();
		for (Integer turtleID : myTurtleMap.keySet()) {
			myTurtleShapeMap.put(turtleID, myTurtleMap.get(turtleID).getShape());
		}
		return myTurtleShapeMap;
	}

	@Override
	public Map<Integer, Double> getPenSize() {
		Map<Integer, Double> myTurtlePenSizeMap = new HashMap<Integer, Double>();
		for (Integer turtleID : myTurtleMap.keySet()) {
			myTurtlePenSizeMap.put(turtleID, myTurtleMap.get(turtleID).getPenSize());
		}
		return myTurtlePenSizeMap;
	}
	
	/**
	 * @return a map of all turtles created with the 
	 * key being their ID
	 */
	public Map<Integer, TurtleModel> getTurtleMap() {
		return myTurtleMap;
	}
	
	/**
	 * @return a list of all turtles currently 'told'
	 */
	public List<TurtleModel> getTurtlesTold() {
		return myTurtlesTold;
	}
	
	/**
	 * sets told turtles to be all turtles in the list turtlesToTell
	 * @param turtlesToTell
	 */
	public void tellTurtles(List<TurtleModel> turtlesToTell) {
		myTurtlesTold = turtlesToTell;
	}
	
	/**
	 * sets told turtles but when only a single turtle is to be told
	 * @param turtleToTell
	 */
	public void tellTurtles(TurtleModel turtleToTell) {
		List<TurtleModel> singleTurtleToTell = new ArrayList<TurtleModel>();
		singleTurtleToTell.add(turtleToTell);
		myTurtlesTold = singleTurtleToTell;
	}
	
	/**
	 * @return number of turtles created by user 
	 */
	public int getNumTurtlesCreated() {
		return myTurtleMap.keySet().size();
	}
}
