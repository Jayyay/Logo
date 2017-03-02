package view;

import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Variable;

/**
 * class definition found at http://java-buddy.blogspot.com/2012/04/javafx-2-editable-tableview.html
 * 
 * Minor alterations made to the handle method by adding a try catch black that catches 
 * the NumberFormatException thrown when a user inputs an invalid value for the cell and 
 * displays an error message instead of simply throwing an exception to the console. 
 * 
 *
 */
public class EditingCell extends TableCell<Variable, Double> {
	 
    private TextField textField;
   
    public EditingCell() {}
   
    @Override
    public void startEdit() {
        super.startEdit();
       
        if (textField == null) {
            createTextField();
        }
       
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.selectAll();
    }
   
    @Override
    public void cancelEdit() {
        super.cancelEdit();
       
        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
       
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setGraphic(textField);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
           
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
              	  try{
              		  commitEdit(Double.parseDouble(textField.getText()));
              	  }
              	  catch (NumberFormatException e) {
						//TODO Show some sort of warning message here
              		  cancelEdit();
					}
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }
   
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
