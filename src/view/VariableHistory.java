package view;

import java.util.ResourceBundle;

import controller.InputListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Variable;
import model.Trackable;

/**
 * @author samuelcurtis
 *
 */
public class VariableHistory {
	private static final String RESOURCE_FOLDER = "resources/languages/English";
	private Trackable myDataSource;
	private InputListener myInputListener;
	private TableView<Variable> myTableView;
	private ResourceBundle resources;
	
	public VariableHistory(Trackable table, InputListener il){
		resources = ResourceBundle.getBundle(RESOURCE_FOLDER);
		myDataSource = table;
		myInputListener = il;
		myTableView = new TableView<Variable>();
		myTableView.setEditable(true);
		makeVariableTable();
	}
	
	private void makeVariableTable() {
		myTableView.setMaxHeight(100);
		myTableView.setPlaceholder(new Label(resources.getString("NoVars"))); //http://stackoverflow.com/questions/24765549/remove-the-default-no-content-in-table-text-for-empty-javafx-table
	    myTableView.setItems(retrieveVariableData());
	    myTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    myTableView.getColumns().addAll(makeVariableNameColumn(),makeVariableValueColumn());
	}
	
	private ObservableList<Variable> retrieveVariableData(){
		//System.out.println(myDataSource.getAllVariables().get(0));
		return FXCollections.observableArrayList(myDataSource.getAllVariables());
	}
	
	private TableColumn makeVariableNameColumn(){
		TableColumn variableNameCol = new TableColumn(resources.getString("VariableColumn"));
		variableNameCol.setCellValueFactory(
	    		new PropertyValueFactory<Variable,String>("myName")
	    );
		return variableNameCol;
	}
	
	private TableColumn makeVariableValueColumn(){
		TableColumn variableValueCol = new TableColumn(resources.getString("ValueColumn"));
	    variableValueCol.setCellValueFactory(
	    		new PropertyValueFactory<Variable,Double>("myValue")
	    );
	    
	    Callback<TableColumn, TableCell> cellFactory =
	            new Callback<TableColumn, TableCell>() {
	                public TableCell call(TableColumn p) {
	                    return new EditingCell();
	                }
	            };
	   
	    variableValueCol.setCellFactory(cellFactory);
	    
	    variableValueCol.setOnEditCommit(
	    	    new EventHandler<CellEditEvent<Variable, Double>>() {
	    	        @Override
	    	        public void handle(CellEditEvent<Variable, Double> t) {
	    	            Variable editedVariable = (Variable) t.getTableView().getItems().get(
		    	                t.getTablePosition().getRow());
	    	        	editedVariable.setMyValue(t.getNewValue());
	    	            setVariable(editedVariable.getMyName(), t.getNewValue());
	    	        }
	    	    }
	    	);
	    
	    return variableValueCol;
	}
	
	private void setVariable(String myName, Double newValue) {
		System.out.println("MAKE" + " :" + myName + " " + newValue.toString());
		myInputListener.onInput("MAKE" + " " + myName + " " + newValue.toString());
	}
	
	public TableView<Variable> getVariableTable(){
		return myTableView;
		
	}

	public void updateVariableHistory() {
		myTableView.getItems().clear();
		myTableView.setItems(retrieveVariableData());
		
	}
	
}
