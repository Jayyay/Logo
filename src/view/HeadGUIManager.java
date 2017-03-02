package view;

import java.util.HashMap;
import java.util.ResourceBundle;
import controller.SLogoController;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The meta-GUI manager for managing all workspaces
 * @author Sam, Yilun
 *
 */
public class HeadGUIManager implements SaveListener, LoadListener, CreateWorkSpaceListener {
	private static final String RESOURCE_FOLDER = "resources/languages/English";
	private ResourceBundle myResources;
	private Scene myScene;
	private Stage myPrimaryStage;
	private BorderPane myBorderPane;
	private HBox myWorkSpaceBox;
	private ToggleGroup myToggleButtons;
	//why cant this just be map?
	private HashMap<String, SLogoController> myControllers;
	private SLogoController myCurrentController;

	/**
	 * Constructor
	 * @param s
	 */
	public HeadGUIManager(Stage s){
		myResources = ResourceBundle.getBundle(RESOURCE_FOLDER);
		myPrimaryStage = s;
	}

	/**
	 * Initialize intialize variables. 
	 */
	public void initialize() {
		myControllers = new HashMap<String, SLogoController>();
		myBorderPane = new BorderPane();
		myToggleButtons = new ToggleGroup();
		myScene = new Scene(myBorderPane,1500,1500);
		addInitialWorkSpaceTabs();
		myPrimaryStage.setScene(myScene);
		myPrimaryStage.show();
	}

	private void addInitialWorkSpaceTabs(){
		myWorkSpaceBox = new HBox();
		myWorkSpaceBox.getChildren().add(new WorkSpaceCreator(myToggleButtons, this,myResources).getCreateButton());
		myWorkSpaceBox.getChildren().add(new WorkSpaceSaver(myToggleButtons, this, myPrimaryStage,myResources).getSaveButton());
		myWorkSpaceBox.getChildren().add(new WorkSpaceLoader(myToggleButtons, this, myPrimaryStage,myResources).getLoadButton());
		makeNewWorkSpace(myResources.getString("DefaultWorkspaceName"));
		myBorderPane.setTop(myWorkSpaceBox);
	}

	private void makeNewWorkSpace(String string) {
		ToggleButton workSpaceButton = new ToggleButton(string);
		myControllers.put(string, new SLogoController());
		workSpaceButton.setOnAction(e -> workSpaceSelected(workSpaceButton,workSpaceButton.getText()));
		workSpaceButton.setToggleGroup(myToggleButtons);
		myWorkSpaceBox.getChildren().add(myWorkSpaceBox.getChildren().size() - 3, workSpaceButton);
		workSpaceSelected(workSpaceButton, string);
	}
	
	private void workSpaceSelected(ToggleButton selected, String controllerKey) {
		myToggleButtons.selectToggle(selected);
		myCurrentController = myControllers.get(controllerKey);
		myBorderPane.setCenter(myCurrentController.getGUI().getRoot());
	}

	@Override
	public void save(String filePath) {
		myCurrentController.getGUI().getOptionMenu().getCommandHistory().createHistoryTextFile(filePath);
	}

	@Override
	public void load(String filePath, String commandHistory) {
		makeNewWorkSpace(filePath);
		myCurrentController.onInput(commandHistory);
		
	}
	
	@Override
	public void createWorkSpace(String workSpacename) {
		makeNewWorkSpace(workSpacename);
	}

}
