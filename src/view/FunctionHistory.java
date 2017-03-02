package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Function;
import model.Trackable;

//custom listcell taken from http://java-buddy.blogspot.com/2013/05/implement-javafx-listview-for-custom.html

/**
 * Function history class
 * @author Sam, Yilun
 *
 */
public class FunctionHistory {
	
	private Trackable myDataSource;
	private ListView<Function> myFunctionHistoryList;
	private TerminalFunctionInterface myTerminal;
	
	/**
	 * Public constructor
	 * @param table the trackable serving as data source
	 * @param tfi the terminal to pass command
	 */
	public FunctionHistory(Trackable table, TerminalFunctionInterface tfi){
		myDataSource = table;
		makeFunctionHistory();
		myTerminal = tfi;
	}
	
	private void makeFunctionHistory() {
		myFunctionHistoryList = new ListView<>(retrieveFunctionData());
		myFunctionHistoryList.setPlaceholder(new Label("Function History"));
		myFunctionHistoryList.setCellFactory(new Callback<ListView<Function>, ListCell<Function>>(){
            @Override
            public ListCell<Function> call(ListView<Function> p) {
                ListCell<Function> cell = new ListCell<Function>(){
                    @Override
                    protected void updateItem(Function t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                        	setText(t.toString());
                        }
                    }
                };
                return cell;
            }
        });
		myFunctionHistoryList.setMaxHeight(100);
		myFunctionHistoryList.setOnMouseClicked(e -> handleFunctionClick());
	}
	
	private void handleFunctionClick() {
		if(myFunctionHistoryList.getItems().size() != 0){
			myTerminal.getInputPane().setText(myFunctionHistoryList.getSelectionModel().getSelectedItem().getMyName());
		}
	}




	private ObservableList<Function> retrieveFunctionData(){
		return FXCollections.observableArrayList(myDataSource.getAllFunctions());
	}



	/**
	 * Get function listview
	 * @return
	 */
	public ListView<Function> getFunctionListView(){
		return myFunctionHistoryList;
	}
	
	/**
	 * Update function history from data source
	 */
	public void updateFunctionHistory(){
		myFunctionHistoryList.getItems().clear();
		myFunctionHistoryList.setItems(retrieveFunctionData());
	}

}
