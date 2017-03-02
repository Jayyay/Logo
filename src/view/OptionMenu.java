package view;

import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.Trackable;
import controller.LanguageType;

import java.util.ResourceBundle;

import java.io.File;
import controller.InputListener;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * Option menu class. This class is responsible for laying out all UI elements other than the canvas and the terminal. 
 * @author Sam, Yilun
 *
 */
public class OptionMenu implements Tracker {

	private static final String RESOURCE_FOLDER = "resources/languages/English";
	private GridPane myGridPane;
	private Trackable myDataSource;
	private InputListener myInputListener;
	private VariableHistory myVariableHistory;
	private FunctionHistory myFunctionHistory;
	private ResourceBundle myResources;
	private TerminalFunctionInterface myTerminal;
	private CommandHistory myCommandHistory;
	private LinePropertyListener myLinePropertyListener;
	private CanvasPropertyListener myCanvasPropertyListener;
	private int myActiveTurtle;

	public OptionMenu(Group root, double optionMenuX, double optionMenuY, 
			Trackable track, 
			InputListener il, 
			TerminalFunctionInterface tfi,
			LinePropertyListener lineListener,
			CanvasPropertyListener canvasListener){
		myDataSource = track;
		myInputListener = il;
		myResources = ResourceBundle.getBundle(RESOURCE_FOLDER);
		myVariableHistory = new VariableHistory(track,il);
		myFunctionHistory = new FunctionHistory(track, tfi);
		myTerminal = tfi;
		myCommandHistory = new CommandHistory(myDataSource, myTerminal);
		myActiveTurtle = 0;

		myGridPane = new GridPane();
		myGridPane.setGridLinesVisible(false);
		setGridPane(root,optionMenuX, optionMenuY);
		root.getChildren().add(myGridPane);
		
		myLinePropertyListener = lineListener;
		myCanvasPropertyListener = canvasListener;
	}


	private void setGridPane(Group root, double optionMenuX, double optionMenuY) {
		myGridPane.setLayoutX(optionMenuX);
		myGridPane.setLayoutY(optionMenuY);

		myGridPane.add(new Label(myResources.getString("Line-Type")), 0, 0);
		myGridPane.add(makeLineTypeComboBox(), 1, 0);

		myGridPane.add(new Label(myResources.getString("Language")), 0, 1);
		myGridPane.add(MakeLanguageComboBox(), 1, 1);

		myGridPane.add(new Label("Pen Width:"), 0, 3);
		myGridPane.add(makePenWidthTextField(), 1, 3);

		myGridPane.add(myVariableHistory.getVariableTable(), 0, 4);

		myGridPane.add(myFunctionHistory.getFunctionListView(), 1, 4);

		myGridPane.add(myCommandHistory.getCommandHistoryyListView(), 0, 5);
		myGridPane.add(makeSetImageButton(root), 0, 6);
		myGridPane.add(makeClearButton(), 0, 7);
		myGridPane.add(new Label("Select Turtle ID"), 0, 8);
		myGridPane.add(makeTurtleIdComboBox(), 0, 9);
	}

	private Button makeClearButton() {
		Button clearButton = new Button(myResources.getString("ClearCanvas"));
		clearButton.setOnAction(e -> ClearScreen());
		return clearButton;
	}

	private void ClearScreen() {
		myTerminal.getInputPane().setText(myResources.getString("ClearScreen"));
		myTerminal.submitText();
		myInputListener.onInput(myResources.getString("ClearScreen"));
	}


	private TextField makePenWidthTextField(){
		TextField penWidthTextField = new TextField();
		penWidthTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			final KeyCombination combo = new KeyCodeCombination(KeyCode.ENTER);
			public void handle(KeyEvent t) {
				if (combo.match(t)) {
					myLinePropertyListener.changeLineWidth(myActiveTurtle,Double.parseDouble(penWidthTextField.getText()));
				}
			}
		});

		penWidthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		    if(!penWidthTextField.getText().isEmpty() && penWidthTextField.getText() != null)
		    	myLinePropertyListener.changeLineWidth(myActiveTurtle, Double.parseDouble(newValue));
		});
		return penWidthTextField;
	}

	private Button makeSetImageButton(Group root) {
		final Button setImageButton = new Button(myResources.getString("ChooseTurtleImg"));
		setImageButton.setOnAction(e -> chooseNewImage(root));
		return setImageButton;
	}

	private void chooseNewImage(Group root) {
		FileChooser imageChoice = new FileChooser();
		imageChoice.setInitialDirectory(new File(myResources.getString("DefaultDirectory")));
		File newImage = imageChoice.showOpenDialog(root.getScene().getWindow());
		if(newImage != null){
			// TODO
			myCanvasPropertyListener.changeTurtleImage(myActiveTurtle, newImage);
		}
	}

	private ComboBox<Integer> makeTurtleIdComboBox(){
		final ComboBox<Integer> turtleIDChoiceBox = new ComboBox<Integer>();
		turtleIDChoiceBox.setValue(0);
		turtleIDChoiceBox.getItems().addAll(myDataSource.getAllTurtleIDs());
		turtleIDChoiceBox.valueProperty().addListener((e,o,n) -> {
			myActiveTurtle = n;
			myTerminal.getInputPane().setText("tell [" + n.toString() + "]");
			myTerminal.submitText();
		});
		return turtleIDChoiceBox;
	}

	private ComboBox<String> MakeLanguageComboBox() {
		final ComboBox<String> languageChoiceBox = new ComboBox<String>();
		languageChoiceBox.setValue(LanguageType.ENGLISH.toString());
		languageChoiceBox.getItems().addAll(LanguageType.CHINESE.toString(),
				LanguageType.ENGLISH.toString(),
				LanguageType.FRENCH.toString(),
				LanguageType.GERMAN.toString(),
				LanguageType.ITALIAN.toString(),
				LanguageType.PORTUGUESE.toString(),
				LanguageType.RUSSIAN.toString(),
				LanguageType.SPANISH.toString()
				);
		languageChoiceBox.valueProperty().addListener((e,o,n) -> {;
			myTerminal.getInputPane().setText(n);
			myTerminal.submitText();
			myInputListener.onSetLanguage(LanguageType.valueOf(n.toUpperCase()));
		});
		return languageChoiceBox;
	}

	private ComboBox<String> makeLineTypeComboBox() {
		final ComboBox<String> lineChoiceBox = new ComboBox<>();
		lineChoiceBox.setValue(myResources.getString("Solid"));
		lineChoiceBox.getItems().addAll(myResources.getString("Solid"), 
				myResources.getString("Dotted"), 
				myResources.getString("Dashed"));
		lineChoiceBox.valueProperty().addListener((e,o,n)-> {
			myLinePropertyListener.changeLineType(myActiveTurtle,n);
		});
		return lineChoiceBox;
	}
	
	
	public CommandHistory getCommandHistory() {
		return myCommandHistory;
	}
	
	@Override
	public void track() {
		myVariableHistory.updateVariableHistory();
		myFunctionHistory.updateFunctionHistory();
		myCommandHistory.updateCommandHistory();
	}

}
