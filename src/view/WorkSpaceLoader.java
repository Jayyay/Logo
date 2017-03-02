package view;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//readfile method found at http://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file

public class WorkSpaceLoader {
	private ToggleButton myLoadButton;
	private LoadListener myLoadListener;
	private ResourceBundle myResources;

	public WorkSpaceLoader(ToggleGroup group, LoadListener load, Stage s, ResourceBundle resource) {
		myResources = resource;
		myLoadButton = new ToggleButton(myResources.getString("LoadWorkspace"));
		myLoadButton.setToggleGroup(group);
		myLoadButton.setOnAction(e -> loadWorkSpace(s));
		myLoadListener = load;
	}

	private void loadWorkSpace(Stage s) {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(s);
		try {
			String commands = readFile(file.getPath());
			myLoadListener.load(file.getPath(), commands);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String readFile(String path) 
			throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}


	public ToggleButton getLoadButton(){
		return myLoadButton;
	}

}
