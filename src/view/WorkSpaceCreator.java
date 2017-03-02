package view;

import java.util.Optional;
import java.util.ResourceBundle;

// import com.apple.laf.AquaButtonBorder.Toggle;

// import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

//code for dialog prompt adapted from https://examples.javacodegeeks.com/desktop-java/javafx/dialog-javafx/javafx-dialog-example/

public class WorkSpaceCreator {
	private ToggleButton myCreateWorkSpaceButton;
	private CreateWorkSpaceListener myWorkSpaceListener;
	private ResourceBundle myResources;
	
	public WorkSpaceCreator(ToggleGroup group, CreateWorkSpaceListener create, ResourceBundle resource){
		myResources = resource;
		myCreateWorkSpaceButton = new ToggleButton(myResources.getString("CreateWorkspace"));
		myCreateWorkSpaceButton.setToggleGroup(group);
		myCreateWorkSpaceButton.setOnAction (e -> createWorkSpace());
		myWorkSpaceListener = create;
	}
	
	
	private void createWorkSpace() {
		// TODO Auto-generated method stub
		if(promptForWorkSpaceName() != null){
			myWorkSpaceListener.createWorkSpace(promptForWorkSpaceName());
		}
	}


	private String promptForWorkSpaceName() {
		TextInputDialog workSpaceNamePrompt = new TextInputDialog();
		workSpaceNamePrompt.setTitle(myResources.getString("NameWorkspace"));
		workSpaceNamePrompt.setHeaderText(myResources.getString("EnterNameWorkspace"));

		Optional<String> result = workSpaceNamePrompt.showAndWait();

		if (result.isPresent()) {
			return result.get();
		}

		//actionStatus.setText("Text entered: " + entered);
		return null;
	}


	public ToggleButton getCreateButton() {
		// TODO Auto-generated method stub
		return myCreateWorkSpaceButton;
	}

}
