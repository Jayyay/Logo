package view;

import java.util.ArrayList;

import controller.InputListener;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import model.Trackable;

/**
 * Palette class for choosing line and canvas colors. 
 * @author Yilun
 *
 */
public class Palette {
	
	private int myWidth;
	private int myHeight;
	private int myX, myY;
	private Group myRoot;
	private Canvas myCanvas;
	private Trackable myDataSource;
	private ComboBox<String> myIdxSelector;
	private Rectangle myColorSample;
	private ComboBox<String> myColorTypeSelector;
	private LinePropertyListener myLinePropertyListener;
	private WebView myColorTextDisplayer;
	private CanvasPropertyListener myCanvasPropertyListener;
	private TerminalFunctionInterface myTerminal;
	private Button myButton;
	private InputListener myInputListener;

	/**
	 * public constructor
	 * @param root the root of the scene
	 * @param width total width
	 * @param height total height
	 * @param x x position
	 * @param y y position
	 * @param table Trackable object for getting available indices
	 * @param cpl CanvasPropertyListener implementer
	 * @param lpl LinePropertyListener implementer
	 * @param tfi TerminalFunctionInterface implementer
	 * @param il InputListener implementer
	 */
	public Palette(Group root, int width, int height, int x, int y, Trackable table, 
			CanvasPropertyListener cpl, LinePropertyListener lpl, TerminalFunctionInterface tfi, InputListener il) {
		myRoot = root;
		myDataSource = table;
		myLinePropertyListener = lpl;
		myCanvasPropertyListener = cpl;
		myTerminal = tfi;
		myInputListener = il;
		myWidth = width;
		myHeight = height;
		myX = x;
		myY = y;
		makeColorPatternArea();
		makeColorSampleArea();
		makeColorTextDisplay();
		makeIdxSelector();
		makeColorTypeSelector();
		makeChangeColorButton();
	}

	private void makeChangeColorButton() {
		myButton = new Button("Change");
		myButton.setPrefWidth(myWidth*0.25);
		myButton.setPrefHeight(myHeight*0.1);
		myButton.setLayoutX(myX+myWidth*0.75);
		myButton.setLayoutY(myY+myHeight*0.9);
		myButton.setOnMouseClicked(e -> {
			changeColor();
		});
		myRoot.getChildren().add(myButton);
	}

	private void makeColorTypeSelector() {
		myColorTypeSelector = new ComboBox<String>();
		myColorTypeSelector.setValue("Canvas");
		myColorTypeSelector.getItems().add("Canvas");
		myColorTypeSelector.getItems().add("Path");
		myColorTypeSelector.setLayoutX(myX+myWidth*0.4);
		myColorTypeSelector.setLayoutY(myY+myHeight*0.9);
		myColorTypeSelector.setPrefHeight(myHeight*0.1);
		myRoot.getChildren().add(myColorTypeSelector);
	}

	private void makeIdxSelector() {
		ArrayList<String> strList = new ArrayList<String>();
		for(Integer i : myDataSource.getAllTurtleIDs())
			strList.add(i.toString());
		myIdxSelector = new ComboBox<String>();
		myIdxSelector.setValue(strList.get(0));
		myIdxSelector.getItems().addAll(strList);
		myIdxSelector.setLayoutX(myX+myWidth*0.15);
		myIdxSelector.setLayoutY(myY+myHeight*0.9);
		myIdxSelector.setPrefHeight(myHeight*0.1);
		myIdxSelector.setOnMouseClicked(e-> {
			ArrayList<String> strList2 = new ArrayList<String>();
			for(Integer i : myDataSource.getAllTurtleIDs())
				strList2.add(i.toString());
			myIdxSelector.getItems().setAll(strList2);
		});
		myRoot.getChildren().add(myIdxSelector);
	}

	private void makeColorTextDisplay() {
		myColorTextDisplayer = new WebView();
		myColorTextDisplayer.setLayoutX(myX);
		myColorTextDisplayer.setLayoutY(myY+myHeight*0.8);
		myColorTextDisplayer.setPrefSize(myWidth, myHeight*0.08);
		myColorTextDisplayer.setMaxSize(myWidth, myHeight*0.08);
		myColorTextDisplayer.setMinSize(myWidth, myHeight*0.08);
		myColorTextDisplayer.getEngine().loadContent(makeHTML((Color)myColorSample.getFill()));
		myRoot.getChildren().add(myColorTextDisplayer);
	}

	private void makeColorSampleArea() {
		myColorSample = new Rectangle(myX, myY+myHeight*0.9, myWidth*0.1, myHeight*0.1);
		myColorSample.setFill(Color.BLACK);
		myRoot.getChildren().add(myColorSample);
	}

	private void makeColorPatternArea() {
		myCanvas = new Canvas(myWidth, myHeight*0.78);
		myCanvas.setLayoutX(myX);
		myCanvas.setLayoutY(myY);
		myCanvas.setOnMouseClicked(e-> {
			// System.out.println(myColorTextDisplayer.getWidth()+" "+myColorTextDisplayer.getLayoutX());
			myColorSample.setFill(getColor((int)e.getX(), (int)e.getY()));
			myColorTextDisplayer.getEngine().loadContent(makeHTML((Color)myColorSample.getFill()));
			System.out.println(e.getX() +" " + e.getY());
		});
		makePattern();
		myRoot.getChildren().add(myCanvas);
	}
	
	// from http://stackoverflow.com/questions/17925318/how-to-get-hex-web-string-from-javafx-colorpicker-color
	private String toHex(Color c) {
		return String.format( "#%02X%02X%02X",
	            (int)( c.getRed() * 255 ),
	            (int)( c.getGreen() * 255 ),
	            (int)( c.getBlue() * 255 ) );
	}
	
	private String makeHTML(Color c) {
		String background = "#ffffff";
		if(c.getBrightness()>=0.9)
			background = "#000000";
		return String.format("<body style=\"background-color: %s\"><p style=\"color: %s; border: none; margin: 0px; padding: 0px; text-align: center\">%d</p></body>",
				background,
				toHex(c), 
				integerizeColor(c));
	}
	
	private int integerizeColor(Color c) {
		int r = (int) (c.getRed()*255);
		int g = (int) (c.getGreen()*255);
		int b = (int) (c.getBlue()*255);
		int cInt = r<<16 | g<<8 | b;
		return cInt;
	}

	private void changeColor() {
		Color c = (Color) myColorSample.getFill();
		int cInt = integerizeColor(c);
		
		if(myColorTypeSelector.getValue().equals("Canvas")) {
			String cmd = "SETBACKGROUND "+cInt;
			myTerminal.getInputPane().setText(cmd);
			myTerminal.submitText();
			myInputListener.onInput(cmd);
			
			myCanvasPropertyListener.changeBackgroundColor(cInt);
		} else if(myColorTypeSelector.getValue().equals("Path")) {
			String cmd = String.format("%s [ %s ] [ %s %d ]", 
					"ASK", myIdxSelector.getValue(), 
					"SETPENCOLOR", cInt);
			myTerminal.getInputPane().setText(cmd);
			myTerminal.submitText();
			myInputListener.onInput(cmd);
			int idx = Integer.parseInt(myIdxSelector.getValue());
			myLinePropertyListener.changeLineColor(idx, cInt);
		}
	}

	private Color getColor(int i, int j) {
		int h = (int)(360.0*i/myWidth);
		double s, l;
		if(j<myHeight/2) {
			l = 1.0;
			s = j*2.0/myHeight;
		} else {
			s = 1.0;
			l = 1.0-(j*2.0-myHeight)/myHeight;
		}
		return Color.hsb(h, s, l);
	}

	private void makePattern() {
		PixelWriter pw = myCanvas.getGraphicsContext2D().getPixelWriter();
		for(int i=0; i<myWidth; i++)
			for(int j=0; j<myHeight; j++)
				pw.setColor(i, j, getColor(i, j));
	}

}
