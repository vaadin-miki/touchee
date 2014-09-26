package org.vaadin.miki.touchee.views.edit;

/**
 * Listener for data-related events.
 * 
 * @author miki
 *
 */
public interface DataChangeListener {

	/**
	 * Called whenever the data is about to be changed or has changed. Listeners
	 * can indicate that the data should be rejected by calling event's reject()
	 * method.
	 * 
	 * @param event
	 *            Event with information about the state of the data.
	 */
	public void dataChanged(DataChangeEvent event);

}
