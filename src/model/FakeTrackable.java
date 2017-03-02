package model;

import java.util.ArrayList;
import java.util.List;

public class FakeTrackable implements Trackable {
	
	@Override
	public List<Variable> getAllVariables() {
		ArrayList<Variable> fakeVars = new ArrayList<Variable>();
		fakeVars.add(new Variable("count", 5.0));
		return fakeVars;
	}

	@Override
	public List<Function> getAllFunctions() {
		ArrayList<Function> fakeFunctions = new ArrayList<Function>();
		fakeFunctions.add(new Function("TEST FUNCTION", null, null, null));
		return fakeFunctions;
	}

	@Override
	public List<String> getCmdHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getAllTurtleIDs() {
		ArrayList<Integer> ids = new ArrayList<>();
		ids.add(0);
		return ids;
	}

}
