package model;

import java.util.LinkedList;
import java.util.Queue;

import controller.LanguageType;
import controller.UpdateListener;
import model.commands.Execution;

/**
 * The Processor of SLogo, aka Model or Backend.
 * Splitting up the input string and send it to parsing and execution.
 * @author Jay, rachelbransom
 */
public class SLogoProcessor {

	private UpdateListener myUpdateListener;
	private Context myContext;
	private Matcher myMatcher;

	/**
	 * constructor which creates context, matcher, and sets
	 * updateListener
	 * @param updateListener
	 */
	public SLogoProcessor(UpdateListener updateListener) {
		myUpdateListener = updateListener;
		myContext = new Context();
		myMatcher = new Matcher();
		Execution.setMatcher(myMatcher);
	}

	/**
	 * @return context
	 */
	public Displayable getDisplayable() {
		return myContext;
	}

	/**
	 * @return context
	 */
	public Trackable getTrackable() {
		return myContext;
	}

	/**
	 * @return the canvas model of context
	 */
	public Visualizable getVisualizable() {
		return myContext.getCanvasModel();
	}

	/**
	 * sets matcher's language
	 * @param languageType
	 */
	public void setLanguage(LanguageType languageType) {
		myMatcher.setLanguage(languageType);
	}
	
	/**
	 * Given the user's input as a single string, this method
	 * splits the command appropriately, creating an array of 
	 * commands, and passes it off to be prepared elsewhere, and then
	 * calls for the command, once prepared, to be executed.
	 * @param inputStr
	 */
	public void process(String inputStr) {
		// add to command history
		myContext.addCmdToHistory(inputStr);
		myUpdateListener.onTrackingShouldUpdate();
		
		// create arguments queue
		Queue<String> args = new LinkedList<>();
		String[] cmdArr = removeCommentLines(inputStr);
		for (String s : cmdArr) {
			args.offer(s.toUpperCase());
		}

		// call execution command to do the real parse and execute
		Execution execution = new Execution(myUpdateListener, myContext);
		execution.prepare(args);
		execution.execute();
	}

	private String[] removeCommentLines(String inputStr) {
		String[] lines = inputStr.trim().split("\\n");
		String inputWithoutComments = "";
		for (String line : lines) {
			if ((line.startsWith("#") || line.equals(""))) {
				//ignore comment
			}
			else if (line.contains("#")) {
				inputWithoutComments += line.substring(0, line.indexOf('#')) + " ";
			}
			else {
				inputWithoutComments += line + " ";
			}
		}
		return inputWithoutComments.equals("")
			? new String[0]
			: inputWithoutComments.split("\\s+");
	}
	
}
