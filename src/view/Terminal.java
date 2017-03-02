package view;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import main.MsgType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javafx.concurrent.Worker;
import javafx.scene.Group;
import model.Displayable;

/**
 * The Terminal class
 * This class has two parts, one for displaying previous commands; the other for input new command
 * The former is implemented using WebView so as to enable multiple colors
 * The latter is implemented using TextArea
 * @author Yilun
 *
 */
public class Terminal implements Displayer, TerminalFunctionInterface {

	// the javascript function for scrolling HTML to bottom
	private static final String HEADER = "<head><script language=\"javascript\" type=\"text/javascript\">function toBottom(){window.scrollTo(0, document.body.scrollHeight);}</script></head>";

	private TerminalDelegateInterface myDelegate;
	private Displayable myDataSource;
	private WebView myHistoryPane;
	private TextArea myInputPane;

	private ToggleSwitch myInputToggle;
	private Queue<String> myRenderQueue;
	private ArrayList<String> myQueryHistory;
	private int myHistoryPointer;

	/**
	 * Public constructor
	 * @param root
	 * @param delegate
	 * @param dable
	 * @param terminalX
	 * @param terminalY
	 * @param terminalWidth
	 * @param terminalHeight
	 * @param toggleX
	 * @param toggleY
	 * @param toggleWidth
	 * @param toggleHeight
	 */
	public Terminal(
			Group root, 
			TerminalDelegateInterface delegate,
			Displayable dable,
			double terminalX, 
			double terminalY, 
			double terminalWidth, 
			double terminalHeight, 
			double toggleX, 
			double toggleY, 
			double toggleWidth, 
			double toggleHeight) {
		myDelegate = delegate;
		myDataSource = dable;

		myRenderQueue = new LinkedList<String>();
		myQueryHistory = new ArrayList<String>();
		myQueryHistory.add("");
		myHistoryPointer = 0;

		makeHistoryPane(root, terminalX, terminalY, terminalWidth, terminalHeight);
		makeInputPane(root, terminalX, terminalY, terminalWidth, terminalHeight);

		myInputToggle = new ToggleSwitch("Ctrl-Enter to Run", "Enter to Run");
		myInputToggle.setLayoutX(toggleX);
		myInputToggle.setLayoutY(toggleY);
		myInputToggle.setPrefSize(toggleWidth, toggleHeight);
		root.getChildren().add(myInputToggle);
	}

	private void makeInputPane(Group root, double terminalX, double terminalY, double terminalWidth,
			double terminalHeight) {
		myInputPane = new TextArea();
		myInputPane.setLayoutX(terminalX);
		myInputPane.setLayoutY(terminalY+terminalHeight*3/4);
		myInputPane.setPrefSize(terminalWidth, terminalHeight*1/4);
		myInputPane.setMinSize(terminalWidth, terminalHeight*1/4);
		myInputPane.setMaxSize(terminalWidth, terminalHeight*1/4);
		addKeyListener();
		root.getChildren().add(myInputPane);
		myInputPane.requestFocus();
	}

	private void addKeyListener() {
		myInputPane.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER) {
				enterKeySubmit(e);
			} else {
				historyScroll(e);
			}
		});
	}

	private void enterKeySubmit(KeyEvent e) {
		if(!e.isControlDown()!=myInputToggle.switchOnProperty().getValue()) {
			myDelegate.submitKeyed();
			e.consume();
		} else {
			String newText = myInputPane.getText();
			if(!myInputToggle.switchOnProperty().getValue())
				newText += "\n";
			myInputPane.setText(newText);
			myInputPane.positionCaret(newText.length());
		}
	}

	private void historyScroll(KeyEvent e) {
		if(e.getCode() == KeyCode.UP && myInputPane.getText().indexOf("\n")==-1) {
			myHistoryPointer--;
			if(myHistoryPointer < 0)
				myHistoryPointer++;
			myInputPane.setText(myQueryHistory.get(myHistoryPointer));
			myInputPane.positionCaret(Integer.MAX_VALUE);
		} else if(e.getCode() == KeyCode.DOWN && myInputPane.getText().indexOf("\n")==-1) {
			myHistoryPointer++;
			if(myHistoryPointer == myQueryHistory.size())
				myHistoryPointer--;
			myInputPane.setText(myQueryHistory.get(myHistoryPointer));
			myInputPane.positionCaret(Integer.MAX_VALUE);
		}
	}

	private void makeHistoryPane(Group root, double terminalX, double terminalY, double terminalWidth,
			double terminalHeight) {
		myHistoryPane = new WebView();
		myHistoryPane.setLayoutX(terminalX);
		myHistoryPane.setLayoutY(terminalY);
		myHistoryPane.setPrefSize(terminalWidth, terminalHeight*3/4);
		myHistoryPane.setMinSize(terminalWidth, terminalHeight*3/4);
		myHistoryPane.setMaxSize(terminalWidth, terminalHeight*3/4);
		myHistoryPane.getEngine().getLoadWorker().stateProperty().addListener(
				(e,o,n) -> {
					if(n == Worker.State.SUCCEEDED) {
						myRenderQueue.poll();
						renderNext();
					};
				});
		render(getSysInfoStr());
		root.getChildren().add(myHistoryPane);
	}

	private synchronized void render(String s) {
		myRenderQueue.add(s);
		if(myRenderQueue.size()==1)
			renderNext();
	}

	private void renderNext() {
		if(myRenderQueue.isEmpty())
			return;
		String content = (String) myHistoryPane.getEngine().executeScript("document.body.innerHTML");
		String s = myRenderQueue.peek();
		myHistoryPane.getEngine().loadContent(htmlize(content+s));
	}

	private String getSysInfoStr() {
		return String.format(
				"SLogo Interpreter on %s %s, Java %s<br>Type \"help\", \"copyright\", \"credits\", \"license\" for more information. ", 
				System.getProperty("os.name"), 
				System.getProperty("os.arch"),
				System.getProperty("java.version"));
	}

	private String htmlize(String s) {
		String htmlized = HEADER+"<body onload='toBottom()'>"+s+"</body>";
		return htmlized;
	}

	/**
	 * Submit text in the input area
	 * Clear input area
	 * Add text to history pane
	 * @return text originally in input area, or null is no action is needed
	 */
	public String submitText() {
		String cmd = myInputPane.getText();
		myQueryHistory.add(myQueryHistory.size()-1, cmd);
		myHistoryPointer = myQueryHistory.size()-1;
		String text = addPrefix(cmd, "$ ");

		myInputPane.clear();
		if(cmd.startsWith("license"))
			text += addPrefix("MIT License", "");
		else if(cmd.startsWith("copyright"))
			text += addPrefix("Copyright (c) 2016 Rachael Bransom, Sam Curtis, Jay Wang, and Yilun Zhou. \nAll rights reserved. ", "");
		else if(cmd.startsWith("help"))
			text += addPrefix("Help text", "");
		else if(cmd.startsWith("credits"))
			text += addPrefix("Thanks to Alan Turing, John von Neumann, Ken Thompson, Dennis Ritchie, Richard Stallman, and Linus Torvalds for all their contribution to computer science and humanity.", "");
		else {
			render(text);
			return cmd;
		}
		render(text);
		return null;
	}

	@Override
	public void display() {
		MsgType type = myDataSource.getMsgType();
		String msg = myDataSource.getMessage();
		render("<br>"+colorize(type, msg));
	}

	private String colorize(MsgType t, String s) {
		String color = "black";
		switch (t) {
		case RESULT:
			color = "black";
			break;
		case ERROR: 
			color = "red";
			break;
		case WARNING:
			color = "#af9b00";
			break;
		default:
			break;
		}
		return String.format("<font color=%s>%s</font>", color, s);
	}

	private String addPrefix(String s, String prefix) {
		return "<br>"+prefix+s.replace("\n", "<br>"+prefix);
	}


	public TextArea getInputPane() {
		return myInputPane;
	}


}
