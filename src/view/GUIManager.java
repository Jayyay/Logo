package view;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import controller.InputListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.Displayable;
import model.Trackable;
import model.Visualizable;

/**
 * The "main" class to lay out all elements on the GUI
 * @author Yilun
 *
 */
public class GUIManager implements TerminalDelegateInterface {

	public static final double CANVAS_X = 25;
	public static final double CANVAS_Y = 25;
	public static final double CANVAS_WIDTH = 500;
	public static final double CANVAS_HEIGHT = 500;
	
	public static final double TERMINAL_X = 25;
	public static final double TERMINAL_Y = CANVAS_Y+CANVAS_HEIGHT+25;
	public static final double TERMINAL_WIDTH = 800;
	public static final double TERMINAL_HEIGHT = 200;
	
	public static final double RUN_X = TERMINAL_X;
	public static final double RUN_Y = TERMINAL_Y+TERMINAL_HEIGHT+15;
	public static final double RUN_WIDTH = 100;
	public static final double RUN_HEIGHT = 30;
	
	public static final double OPTION_MENU_X = CANVAS_X + CANVAS_WIDTH + 15;
	public static final double OPTION_MENU_Y = CANVAS_Y;
	
	public static final double SCREEN_WIDTH = 1200;
	public static final double SCREEN_HEIGHT = RUN_Y+RUN_HEIGHT+25;
	
	private Group myRoot;
	private Scene myScene;
	private Terminal myTerminal;
	private DrawingCanvas myCanvas;
	private Button myRunButton;
	private InputListener myInputListener;
	private OptionMenu myOptionMenu;
	private TerminalFunctionInterface myTerminalFunctionInterface;
	
	public GUIManager(InputListener il, Displayable dable, Visualizable vable, Trackable table) {
		double factor = getResizingFactor();
		myRoot = new Group();
		myInputListener = il;
		myTerminal = new Terminal(myRoot, this, dable, 
				TERMINAL_X*factor, TERMINAL_Y*factor, TERMINAL_WIDTH*factor, TERMINAL_HEIGHT*factor, 
				(RUN_X+RUN_WIDTH+15)*factor, RUN_Y*factor, 200*factor, RUN_HEIGHT*factor);
		myTerminalFunctionInterface = myTerminal;
		myCanvas = new DrawingCanvas(myRoot, vable, 
				CANVAS_X*factor, CANVAS_Y*factor, CANVAS_WIDTH*factor, CANVAS_HEIGHT*factor);
		myOptionMenu = new OptionMenu(myRoot,
				OPTION_MENU_X*factor, OPTION_MENU_Y*factor, table, il, myTerminalFunctionInterface, myCanvas, myCanvas);
		new Palette(myRoot, 300, 450, 750, 250, table, myCanvas, myCanvas, myTerminal, il);
		addRunButton(factor);
	}

	private void addRunButton(double factor) {
		myRunButton = new Button("Run");
		myRunButton.setPrefSize(RUN_WIDTH*factor, RUN_HEIGHT*factor);
		myRunButton.setLayoutX(RUN_X*factor);
		myRunButton.setLayoutY(RUN_Y*factor);
		myRunButton.setOnMouseClicked(e -> {
			submitKeyed();
		});
		myRoot.getChildren().add(myRunButton);
	}

	private double getResizingFactor() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int monitorWidth = gd.getDisplayMode().getWidth();
		int monitorHeight = gd.getDisplayMode().getHeight();
		double maxScreenWidth = monitorWidth * 0.8;
		double maxScreenHeight = monitorHeight * 0.8;
		double widthScalingFactor = maxScreenWidth / SCREEN_WIDTH;
		double heightScalingFactor = maxScreenHeight / SCREEN_HEIGHT;
		double factor = Math.min(widthScalingFactor, heightScalingFactor);
		return factor;
	}
	
	public OptionMenu getOptionMenu(){
		return myOptionMenu;
	}
	
	public Group getRoot(){
		return myRoot;
	}

	public Scene getScene() {
		return myScene;
	}
	
	public String getTitle() {
		return "SLOGO IDE";
	}

	public Displayer getDisplayer() {
		return myTerminal;
	}
	
	public Visualizer getVisualizer() {
		return myCanvas;
	}
	
	public Tracker getTracker() {
		return myOptionMenu;
	}

	@Override
	public void submitKeyed() {
		String cmd = myTerminal.submitText();
		if(cmd!=null && myInputListener!=null)
			myInputListener.onInput(cmd);
	}

	public LinePropertyListener getLinePropertyListener() {
		return myCanvas;
	}
	
	public CanvasPropertyListener getCanvasPropertyListener() {
		return myCanvas;
	}
	
}
