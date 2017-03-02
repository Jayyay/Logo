package model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import model.commands.ListNode;

/**
 * A class that represents a function (user-defined procedure).
 * @author Jay, Sam
 */
public class Function {
	
	private SimpleStringProperty myName;
	private List<String> myVarNames;
	private String myStringForm; 
	private ListNode myListNode;
	
	public Function(
		String name,
		List<String> varNames,
		String stringForm,
		ListNode listNode
	){
		myName = new SimpleStringProperty(name);
		myVarNames = varNames;
		myStringForm = stringForm;
		myListNode = listNode;
	}
	
	/**
	 * @return the name of the function
	 */
	public String getMyName(){
		return myName.get();
	}
	
	/**
	 * sets the function to the name newName
	 * @param newName
	 */
	public void setMyName(String newName){
		myName.set(newName);
	}
	
	/**
	 * @return all of the variable names in the function
	 */
	public List<String> getVarNames() {
		return myVarNames;
	}
	
	/**
	 * @return the list of commands in the function
	 */
	public ListNode getListNode() {
		return myListNode;
	}
	
	@Override
	public String toString() {
		return myStringForm;
	}
}
