package org.vaadin.miki.touchee.views.list;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 * Default, reference implementation of {@link ItemComponentGenerator}. Produces a label with properties. 
 * @author miki
 *
 */
public class DefaultItemComponentGenerator implements ItemComponentGenerator {

	@Override
	public Component getComponent(Object itemId, Item item, boolean selected) {
		StringBuilder builder = new StringBuilder(selected ? ">> " : "");
		for(Object propId: item.getItemPropertyIds())
			builder.append(propId.toString()).append(": ").append(sanitise(item.getItemProperty(propId).getValue())).append(", ");
		
		// cut trailing ", ", if needed
		if(builder.length() > 0)
			builder.setLength(builder.length() - 2);
		
		Label result = new Label(builder.toString());
		return result;
	}

	private static final String sanitise(Object object) {
		return object == null ? "(nothing)" : object.toString();
	}
	
}
