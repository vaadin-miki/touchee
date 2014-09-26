package org.vaadin.miki.touchee.views.list;

/**
 * Interface for objects that listen to {@link ItemStateEvent}s.
 * @author miki
 *
 */
public interface ItemStateListener {

	/**
	 * Invoked when a state of an item has changed.
	 * @param event Event with more information.
	 */
	public void itemStateChanged(ItemStateEvent event);
	
}
