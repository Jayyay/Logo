package controller;
/**
 * A listener that listens to the input.
 * @author Jay
 */
public interface InputListener {
	
	/**
	 * Called when there's a new input string.
	 * @param inputStr the input string.
	 */
    void onInput(String inputStr);
    
    /**
     * Called when there's a change of language.
     * @param languageType the language type enum.
     */
    void onSetLanguage(LanguageType languageType);
}