package view;

import java.io.File;
import java.util.ResourceBundle;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WorkSpaceSaver {
	private ToggleButton mySaveButton;
	private SaveListener mySaveListener;
	private ResourceBundle myResources;
	
	public WorkSpaceSaver(ToggleGroup group, SaveListener save, Stage s, ResourceBundle resource){
		myResources = resource;
		mySaveListener = save;
		mySaveButton = new ToggleButton(myResources.getString("SaveWorkspace"));
		mySaveButton.setToggleGroup(group);
		mySaveButton.setOnAction(e -> saveWorkSpace(s));
	}

	private void saveWorkSpace(Stage s){
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog(s);
		mySaveListener.save(file.getPath());
	}	
	
	public ToggleButton getSaveButton(){
		return mySaveButton;
	}
	
}
