package model;

import java.util.List;

/**
 * A Visualizable that provides necessary information for history tracking.
 * @author Jay
 */
public interface Trackable {
    /**
     * Get all the variables that current exist.
     * @return a list of variables.
     */
    List<Variable> getAllVariables();
    
    /**
     * Get all the functions that current exist.
     * @return a list of functions in the form of strings.
     */
    List<Function> getAllFunctions();
    
    /**
     * Get the history of all the commands that user have input.
     * @return a list of strings
     */
    List<String> getCmdHistory();
    
    /**
     * Get all the ids of existing turtles;.
     */
    List<Integer> getAllTurtleIDs();
}
