package controller;
/**
 * A listener that listens to the update of the model.
 * @author Jay
 */
public interface UpdateListener {
	/**
	 * Called when there's a change in the model that would lead to
	 * the change of the visualization (a.k.a canvas)
	 */
	void onVisualizationShouldUpdate();
	
	/**
	 * Called when there's a change in the model that would lead to
	 * the change of the text display (a.k.a terminal)
	 */
    void onDisplayShouldUpdate();
    
    /**
     * Called when there's a change in the model that would lead to
     * the change of the history tracking (a.k.a option menu)
     */
    void onTrackingShouldUpdate();
}
