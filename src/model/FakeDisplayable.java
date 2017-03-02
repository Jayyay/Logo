package model;

import java.util.Random;

import main.MsgType;

/**
 * A fake displayable class to test Terminal
 * @author Yilun
 *
 */
public class FakeDisplayable implements Displayable {

	private String myString;
	private MsgType myType;
	
	public void choose() {
		int i = new Random().nextInt(3);
		if(i==0) {
			myType = MsgType.RESULT;
			myString = "This is normal output.";
		} else if(i==1) {
			myType = MsgType.WARNING;
			myString = "Line ... has a warning.";
		} else if(i==2) {
			myType = MsgType.ERROR;
			myString = "Error occurred!";
		}
	}
	
	@Override
	public MsgType getMsgType() {
		return myType;
	}

	@Override
	public String getMessage() {
		return myString;
	}

}
