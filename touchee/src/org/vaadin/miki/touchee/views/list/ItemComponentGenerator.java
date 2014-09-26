package org.vaadin.miki.touchee.views.list;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;

/**
 * Helper interface to generate components based on items.
 * 
 * @author miki
 *
 */
public interface ItemComponentGenerator {

	/**
	 * Gets the component that corresponds to a given item.
	 * 
	 * @param itemId
	 *            Identifier of the item, as found in the container.
	 * @param item
	 *            Item.
	 * @param selected
	 *            Whether or not the item is currently selected.
	 * @return A component that corresponds to the item.
	 */
	public Component getComponent(Object itemId, Item item, boolean selected);

}
