package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.HeadGUIManager;

/**
 * Yup ur right. It is the main you are looking for.
 * @author team_2
 */
public class Main extends Application {
	
	public static void main(String [] args){
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		HeadGUIManager control = new HeadGUIManager(s);
		control.initialize();
	}
}
