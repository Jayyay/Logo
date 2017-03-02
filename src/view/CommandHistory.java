package view;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Trackable;

/**
 * Command history class
 * @author Sam, Yilun
 *
 */
public class CommandHistory {
	private Trackable myDataSource;
	private ListView<String> myCommandHistory;
	private TerminalFunctionInterface myTerminal;
	
	/**
	 * Constructor
	 * @param table: the Trackable object to provide data source
	 * @param tfi
	 */
	public CommandHistory(Trackable table, TerminalFunctionInterface tfi){
		myDataSource = table;
		makeCommandHistory();
		myTerminal = tfi;
	}
	
	
	private void makeCommandHistory() {
		myCommandHistory = new ListView<String>(FXCollections.observableArrayList(myDataSource.getCmdHistory()));
		myCommandHistory.setPlaceholder(new Label("Command History"));
		myCommandHistory.setMaxHeight(100);
		myCommandHistory.setOnMouseClicked(e -> handleCommandHistoryClick());
			
	}
	
	private void handleCommandHistoryClick() {
		if(myCommandHistory.getItems().size() != 0){
			myTerminal.getInputPane().setText(myCommandHistory.getSelectionModel().getSelectedItem());
		}
	}

	/**
	 * Get the command history as a ListView
	 * @return
	 */
	public ListView<String> getCommandHistoryyListView(){
		return myCommandHistory;
	}
	
	/**
	 * Update command history from the Trackable data source
	 */
	public void updateCommandHistory(){
		myCommandHistory.getItems().clear();
		myCommandHistory.setItems(FXCollections.observableArrayList(myDataSource.getCmdHistory()));
	}
	
	/**
	 * Create text file for IO
	 * @param pathName
	 */
	public void createHistoryTextFile(String pathName){
		List<String> lines = myDataSource.getCmdHistory();
		Path file = Paths.get(pathName);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
