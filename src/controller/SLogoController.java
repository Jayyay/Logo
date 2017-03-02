package controller;

import model.Displayable;
import model.SLogoProcessor;
import model.Trackable;
import model.Visualizable;
import view.Displayer;
import view.GUIManager;
import view.Tracker;
import view.Visualizer;

/**
 * The Controller of the SLogo.
 * It acts as a InputListener for view, and a UpdateListener for model.
 * When receiving an input signal, it asks the parser to handle the input.
 * Similarly, when receiving an update signal, it asks the visualizer or displayer
 * to handle the update.
 * @author Jay
 */
public class SLogoController implements InputListener, UpdateListener {
	
	private GUIManager myGUI;
	private SLogoProcessor myProcessor;
	
	private Visualizer myVisualizer;
	private Visualizable myVisualizable;
	
	private Displayer myDisplayer;
	private Displayable myDisplayable;
	
	private Tracker myTracker;
	private Trackable myTrackable;
	

	public SLogoController() {
		myProcessor = new SLogoProcessor(this);
		myDisplayable = myProcessor.getDisplayable();
		myVisualizable = myProcessor.getVisualizable();
		myTrackable = myProcessor.getTrackable();
		
		myGUI = new GUIManager(this, myDisplayable, myVisualizable,myTrackable);
		myDisplayer = myGUI.getDisplayer();
		myVisualizer = myGUI.getVisualizer();
		myTracker = myGUI.getTracker();
		
		
		onVisualizationShouldUpdate();
	}
	
	public GUIManager getGUI() {
		return myGUI;
	}
	
	/* Implementations of InputListener */
	@Override
	public void onInput(String inputStr) {
		myProcessor.process(inputStr);
	}
	
	@Override
	public void onSetLanguage(LanguageType languageType) {
		myProcessor.setLanguage(languageType);
	}
	
	/* Implementations of UpdateListener */
	@Override
	public void onVisualizationShouldUpdate() {
		myVisualizer.visualize();
	}

	@Override
	public void onDisplayShouldUpdate() {
		myDisplayer.display();
	}

	@Override
	public void onTrackingShouldUpdate() {
		myTracker.track();
	}
}
