package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A class that represents a variable.
 * @author Jay, Sam
 *
 */
public class Variable {
	private final SimpleStringProperty myName;
	private final SimpleDoubleProperty myValue;
	
	public Variable(String name, Double value){
		myName = new SimpleStringProperty(name);
		myValue = new SimpleDoubleProperty(value);
	}
	
	/**
	 * @return name of the varaible
	 */
	public String getMyName() {
		return myName.get();
	}
	
	/**
	 * sets name to name parameter
	 * @param name
	 */
	public void setMyName(String name) {
		myName.set(name);
	}
	
	/**
	 * @return value of the variable
	 */
	public Double getMyValue() {
		return myValue.get();
	}
	
	/**
	 * sets value of variable to value
	 * @param value
	 */
	public void setMyValue(Double value) {
		myValue.set(value);
	}
	
}
