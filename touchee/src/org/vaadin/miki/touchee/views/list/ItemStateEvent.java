package org.vaadin.miki.touchee.views.list;

import com.vaadin.data.Item;

/**
 * An event that happens with items in list view.
 * @author miki
 *
 */
public class ItemStateEvent {

	private final Object itemId;
	
	private final Item item;
	
	private final boolean selected;
	
	public ItemStateEvent(Object itemId, Item item, boolean selected) {
		this.item = item;
		this.itemId = itemId;
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public Item getItem() {
		return item;
	}

	public Object getItemId() {
		return itemId;
	}
	
}
