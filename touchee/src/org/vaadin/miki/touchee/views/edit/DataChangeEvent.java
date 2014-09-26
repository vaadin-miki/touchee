package org.vaadin.miki.touchee.views.edit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.vaadin.data.Item;

/**
 * Event that goes with data changes. The data in the event can be accepted
 * (default) or rejected.
 * 
 * @author miki
 *
 */
public class DataChangeEvent {

	private final boolean committed;

	private final Map<Object, Object> values;

	private final Item item;

	private final ArrayList<String> rejectMessages = new ArrayList<String>();

	private boolean rejected = false;

	public DataChangeEvent(Map<Object, Object> values, Item item, boolean committed) {
		this.values = values;
		this.committed = committed;
		this.item = item;
	}

	public Collection<?> getPropertyIds() {
		return this.values.keySet();
	}

	public Object getValue(Object propertyId) {
		return this.values.get(propertyId);
	}

	public boolean isCommitted() {
		return committed;
	}

	public boolean isAccepted() {
		return !this.isRejected();
	}

	public boolean isRejected() {
		return rejected;
	}

	public void accept() {
		this.rejected = false;
	}

	public void reject() {
		this.rejected = true;
	}

	public void reject(String message) {
		this.reject();
		this.rejectMessages.add(message);
	}

	public Collection<String> getRejectMessages() {
		return this.rejectMessages;
	}

	public Item getItem() {
		return item;
	}
}
