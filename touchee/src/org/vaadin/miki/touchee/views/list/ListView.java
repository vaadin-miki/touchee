package org.vaadin.miki.touchee.views.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;

/**
 * A view that lists all objects in a given container. Allows callbacks for selecting an item, adding and removing button clicks.
 * @author miki
 *
 */
public class ListView extends NavigationView implements Container.Editor {

	private static final long serialVersionUID = 20140925;
	
	private Container container;
	
	private ItemComponentGenerator itemComponentGenerator = new DefaultItemComponentGenerator();
	
	private final ArrayList<ItemStateListener> itemMarkListeners = new ArrayList<ItemStateListener>();
	private final ArrayList<ItemStateListener> itemSelectListeners = new ArrayList<ItemStateListener>();
	
	private final VerticalComponentGroup layout = new VerticalComponentGroup(); 
	
	private final Set<Object> selection = new LinkedHashSet<Object>();
	
	private final Button add = new Button("Add", new Button.ClickListener() {
		
		private static final long serialVersionUID = 20140925;

		@Override
		public void buttonClick(Button.ClickEvent event) {
			addNewItem();
		}
	});
	
	private final Button remove = new Button("Remove", new Button.ClickListener() {
		
		private static final long serialVersionUID = 20140925;

		@Override
		public void buttonClick(Button.ClickEvent event) {
			removeMarkedItems();
		}
	});
	
	public ListView() {
		super();
		
		this.setContent(this.layout);
		
		Toolbar toolbar = new Toolbar();
		toolbar.addComponent(this.remove);
		toolbar.addComponent(this.add);
		toolbar.setSizeFull();
		this.setToolbar(toolbar);
	}
	
	@Override
	protected void onBecomingVisible() {
		super.onBecomingVisible();
		this.rebuildList();
	}
	
	protected void rebuildList() {
		this.layout.removeAllComponents();
		
		if(this.container != null)
			for(Object itemId: this.container.getItemIds())
				this.layout.addComponent(this.getItemRow(itemId, this.container.getItem(itemId)));
	}
	
	protected Component getItemRow(final Object itemId, final Item item) {
		CheckBox marked = new CheckBox();
		marked.setValue(this.selection.contains(itemId));
		marked.addValueChangeListener(new Property.ValueChangeListener() {
			
			private static final long serialVersionUID = 20140926;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				boolean selected = (Boolean)event.getProperty().getValue();
				broadcast(itemId, item, selected, itemMarkListeners);
				// update selection
				if(selected) selection.add(itemId);
				else selection.remove(itemId);
			}
		});
		
		Component main = this.getItemComponentGenerator().getComponent(itemId, item, false);
		main.setSizeFull();
		
		NavigationButton select = new NavigationButton();
		select.addClickListener(new NavigationButton.NavigationButtonClickListener() {
			
			private static final long serialVersionUID = 20140926;

			@Override
			public void buttonClick(NavigationButton.NavigationButtonClickEvent event) {
				broadcast(itemId, item, true, itemSelectListeners);
			}
		});
		
		Toolbar row = new Toolbar();
		row.addComponents(marked, main, select);
		return row;
	}
	
	/**
	 * Broadcasts the event to given listeners.
	 * @param itemId Id of an item.
	 * @param item Item.
	 * @param selected Whether or not item is selected.
	 * @param listeners Listeners to notify.
	 */
	protected void broadcast(Object itemId, Item item, boolean selected, Collection<ItemStateListener> listeners) {
		ItemStateEvent event = new ItemStateEvent(itemId, item, selected);
		for(ItemStateListener listener: listeners)
			listener.itemStateChanged(event);
	}
	
	protected void removeMarkedItems() {
		;
	}
	
	protected void addNewItem() {
		;
	}
	
	@Override
	public void setContainerDataSource(Container newDataSource) {
		this.container = newDataSource;
		this.rebuildList();
	}

	@Override
	public Container getContainerDataSource() {
		return this.container;
	}

	public ItemComponentGenerator getItemComponentGenerator() {
		return itemComponentGenerator;
	}

	public void setItemComponentGenerator(ItemComponentGenerator itemComponentGenerator) {
		this.itemComponentGenerator = itemComponentGenerator;
	}

	public void addItemMarkListener(ItemStateListener listener) {
		this.itemMarkListeners.add(listener);
	}
	
	public void addItemSelectListener(ItemStateListener listener) {
		this.itemSelectListeners.add(listener);
	}
	
	public void removeItemMarkListener(ItemStateListener listener) {
		this.itemMarkListeners.remove(listener);
	}
	
	public void removeItemSelectListener(ItemStateListener listener) {
		this.itemSelectListeners.remove(listener);
	}
	
}
