package view;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.Visualizable;

//code for converting File to input stream taken from http://www.baeldung.com/convert-file-to-input-stream

/**
 * The canvas class. This class implements Visualizer, and draws out the turtle on screen
 * @author Yilun
 *
 */
public class DrawingCanvas implements Visualizer, LinePropertyListener, CanvasPropertyListener {

	public static final int MAX_RANGE = 1000;

	private Group myRoot;
	private Visualizable myDataSource;
	private Rectangle myBoard;
	private ResourceBundle myResources;

	private double myCanvasX;
	private double myCanvasY;
	private double myCanvasWidth;
	private double myCanvasHeight;

	// private double myLineWidth;

	private Map<Integer, ImageView> myTurtles;
	private Map<Integer, ArrayList<Point2D>> myPointss; // global position
	private Map<Integer, ArrayList<Line>> myDrawnLiness;
	private Map<Integer, Boolean> myTurtlesShown;
	private Map<Integer, Color> myLineColors;
	private Map<Integer, Double> myLineWidths;

	public DrawingCanvas(Group root, Visualizable vable, double canvasX, double canvasY, double canvasWidth, double canvasHeight) {
		myRoot = root;
		myResources = ResourceBundle.getBundle("resources/languages/English");
		myDataSource = vable;
		myCanvasX = canvasX; 
		myCanvasY = canvasY;
		myCanvasWidth = canvasWidth;
		myCanvasHeight = canvasHeight;
		myTurtles = new HashMap<Integer, ImageView>();
		myPointss = new HashMap<Integer, ArrayList<Point2D>>();
		myDrawnLiness = new HashMap<Integer, ArrayList<Line>>();
		myBoard = new Rectangle(canvasX, canvasY, canvasWidth, canvasHeight);
		myBoard.setFill(Color.LIGHTBLUE);
		root.getChildren().add(myBoard);
		myTurtlesShown = new HashMap<Integer, Boolean>();
		myPointss = new HashMap<Integer, ArrayList<Point2D>>();
		myLineColors = new HashMap<Integer, Color>();
		myLineWidths = new HashMap<Integer, Double>();
	}

	private void createTurtle(int idx) {
		assert !myTurtles.containsKey(idx) : "Turtle "+idx+" already exists!";
		ImageView turtle = new ImageView();
		myTurtles.put(idx, turtle);
		setTurtleImage(idx, new File(myResources.getString("DefaultTurtleImage")));
		Point2D local = localToGlobal(0,0);
		moveTurtle(idx, local, 0);
		myPointss.put(idx, new ArrayList<Point2D>());
		myPointss.get(idx).add(local);
		myDrawnLiness.put(idx, new ArrayList<Line>());
		myTurtlesShown.put(idx, false);
		myLineColors.put(idx, Color.BLACK);
		myLineWidths.put(idx, 1.0);
	}

	private void eraseTraces(int idx) {
		myPointss.get(idx).clear();
		myPointss.get(idx).add(localToGlobal(0, 0));
		moveTurtle(idx, 0, 0, 0);
		myRoot.getChildren().removeAll(myDrawnLiness.get(idx));
		myDrawnLiness.get(idx).clear();
	}

	private void deleteTurtle(int idx) {
		assert myTurtles.containsKey(idx) : "Turtle "+idx+" does not exist!";
		eraseTraces(idx);
		myRoot.getChildren().remove(myTurtles.get(idx));
		myTurtles.remove(idx);
		myPointss.remove(idx);
		myDrawnLiness.remove(idx);
		myTurtlesShown.remove(idx);
		myLineColors.remove(idx);
	}

	private void setTurtleImage(int idx, File imageFile){
		try {
			myTurtles.get(idx).setImage(new Image(new FileInputStream(imageFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Point2D localToGlobal(double x, double y) {
		double globalX = (x/MAX_RANGE+0.5)*myCanvasWidth + myCanvasX;
		double globalY = (-y/MAX_RANGE+0.5)*myCanvasHeight + myCanvasY;
		return new Point2D(globalX, globalY);
	}

	private Point2D localToGlobal(Point2D pt) {
		return localToGlobal(pt.getX(), pt.getY());
	}

	@Override
	/**
	 * visualize function implementing the Visualizer interface
	 */
	public void visualize() {
		if(myDataSource.shouldClearScreen()) {
			for(int i : myTurtles.keySet())
				eraseTraces(i);
			return;
		}
		for(int i : myDataSource.isTurtleShown().keySet()) {
			updateTurtle(i);
		}
		for(int i : myTurtles.keySet()) {
			if(!myDataSource.isTurtleShown().containsKey(i))
				deleteTurtle(i);
		}

	}

	private void updateTurtle(int i) {
		if(!myTurtles.containsKey(i))
			createTurtle(i);

		Point2D local = myDataSource.getCoord().get(i);
		Point2D global = localToGlobal(local);
		moveTurtle(i, local, myDataSource.getHeadDegree().get(i));
		Point2D previousEnd = myPointss.get(i).get(myPointss.get(i).size()-1);
		myPointss.get(i).add(global);
		updateLineColor(i);
		int bgc = (int) myDataSource.getBackgroundColor();
		changeBackgroundColor(bgc);
		drawLine(i, global, previousEnd);
		updateTurtleVisibility(i);
	}

	private void updateLineColor(int i) {
		int c = myDataSource.getPenColor().get(i).intValue();
		changeLineColor(i, c);
		int r = (c << 8) >>> 24; // erase 25-32, keep only 17-24 digits
		int g = (c << 16) >>> 24; // erase 17-32, keep only 9-16 digits
		int b = (c << 24) >>> 24; // erase 9-32, keep only 1-8 digits
		Color color = new Color(r/255.0, g/255.0, b/255.0, 1);
		myLineColors.put(i, color);
	}

	private void drawLine(int i, Point2D global, Point2D previousEnd) {
		if(!myDataSource.isPenUp().get(i)) {
			Line l = new Line(previousEnd.getX(), previousEnd.getY(), global.getX(), global.getY());
			l.setStroke(myLineColors.get(i));
			l.setStrokeWidth(myLineWidths.get(i));
			myDrawnLiness.get(i).add(l);
			myRoot.getChildren().add(l);
		}
	}

	private void updateTurtleVisibility(int i) {
		if(myTurtlesShown.get(i)!=myDataSource.isTurtleShown().get(i)) {
			myTurtlesShown.put(i, !myTurtlesShown.get(i));
			if(myTurtlesShown.get(i))
				myRoot.getChildren().add(myTurtles.get(i));
			else
				myRoot.getChildren().remove(myTurtles.get(i));
		}
	}

	private void moveTurtle(int idx, double x, double y, double angle) {
		Point2D pt = localToGlobal(x, y);
		ImageView turtle = myTurtles.get(idx);
		double w = turtle.getImage().getWidth();
		double h = turtle.getImage().getHeight();
		turtle.setX(pt.getX() - w/2);
		turtle.setY(pt.getY() - h/2);
		turtle.setRotate(angle);
	}

	private void moveTurtle(int idx, Point2D ptLocal, double angle) {
		moveTurtle(idx, ptLocal.getX(), ptLocal.getY(), angle);
	}

	@Override
	public void changeLineColor(int idx, int c) {
		int r = (c << 8) >>> 24; // erase 25-32, keep only 17-24 digits
		int g = (c << 16) >>> 24; // erase 17-32, keep only 9-16 digits
		int b = (c << 24) >>> 24; // erase 9-32, keep only 1-8 digits
		Color newColor = new Color(r/255.0, g/255.0, b/255.0, 1);
		myLineColors.put(idx, newColor);
		for(Line l : myDrawnLiness.get(idx))
			l.setStroke(newColor);
	}

	@Override
	public void changeLineType(int idx, String type) {
		switch (type) {
		case "Solid":
			for(Line l : myDrawnLiness.get(idx)) {
				l.getStrokeDashArray().clear();
			}
			break;
		case "Dotted":
			for(Line l : myDrawnLiness.get(idx)) {
				l.getStrokeDashArray().clear();
				l.getStrokeDashArray().addAll(3d, 3d);
			}
			break;
		case "Dashed":
			for(Line l : myDrawnLiness.get(idx)) {
				l.getStrokeDashArray().clear();
				l.getStrokeDashArray().addAll(10d, 10d);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void changeBackgroundColor(int c) {
		int r = (c << 8) >>> 24; // erase 25-32, keep only 17-24 digits
		int g = (c << 16) >>> 24; // erase 17-32, keep only 9-16 digits
		int b = (c << 24) >>> 24; // erase 9-32, keep only 1-8 digits
		Color color = new Color(r/255.0, g/255.0, b/255.0, 1);
		myBoard.setFill(color);
	}

	@Override
	public void changeTurtleImage(int idx, File newImageFile) {
		ImageView turtle = myTurtles.get(idx);
		double w = turtle.getImage().getWidth();
		double h = turtle.getImage().getHeight();
		double turtleXPos = turtle.getX() + w/2;
		double turtleYPos = turtle.getY() + h/2;
		double turtleAngle = turtle.getRotate();
		setTurtleImage(idx, newImageFile);
		w = turtle.getImage().getWidth();
		h = turtle.getImage().getHeight();
		turtle.setX(turtleXPos - w/2);
		turtle.setY(turtleYPos - h/2);
		turtle.setRotate(turtleAngle);
	}

	@Override
	public void changeLineWidth(int idx, double width) {
		myLineWidths.put(idx, width);
		for(Line l : myDrawnLiness.get(idx))
			l.setStrokeWidth(width);

	}

}
