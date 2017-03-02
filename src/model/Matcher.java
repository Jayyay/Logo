package model;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import controller.LanguageType;

/**
 * A matcher to convert a single input command to its symbol name.
 * e.g. "fd", "forward" (English) and "qianjin" (Chinese) will be converted to "Forward".
 * Modified from the example code provided in CS308.
 * @author Jay
 */
public class Matcher {

	private static final String RESOURCE_FOLDER = "resources/prompts/";
	private static final String SYNTAX = "Syntax";
    
    /* "types" and the regular expression patterns that recognize those types
     * note, it is a list because order matters (some patterns may be more generic)
     */
    private List<Entry<String, Pattern>> mySymbols;
    
    /**
     * Constructs a matcher.
     */
    public Matcher() {
        setLanguage(LanguageType.ENGLISH);
    }
    
    /**
     * Set the interpret language
     * @param languageType the language type enum.
     */
    public void setLanguage(LanguageType languageType) {
    	mySymbols = new ArrayList<>();
        addPatterns(RESOURCE_FOLDER + languageType.toString());
    	addPatterns(RESOURCE_FOLDER + SYNTAX);
    }
    
    /**
     * Adds the given resource file to this language's recognized types
     * @param syntax bundle name
     */
    private void addPatterns (String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(syntax);
        Enumeration<String> iter = resources.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String regex = resources.getString(key);
            mySymbols.add(new SimpleEntry<>(
        		key,
                Pattern.compile(regex, Pattern.CASE_INSENSITIVE))
            );
        }
    }
    
    /**
     * Returns the language's type associated with the given text if one exists.
     * @param text text to be matched.
     * @return the symbol name (if matched) or "ERROR".
     */
    public String getSymbol (String text) {
        for (Entry<String, Pattern> e : mySymbols) {
        	if (e.getValue().matcher(text).matches()) {
        		return e.getKey();
            }
        }
        return "NoMatch";
    }

}
