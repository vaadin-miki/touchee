package org.vaadin.miki.touchee.views.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.vaadin.miki.touchee.views.ToucheeView;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.Action;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;

/**
 * A view that lists all objects in a given container. Allows callbacks for selecting an item, adding and removing button clicks.
 *
 * @author miki
 *
 */
public class ListView extends ToucheeView implements Container.Editor {

  private static final long serialVersionUID = 20140925;

  /**
   * Action for selecting an item. This action is internally called by the view, do not add it.
   */
  public static final Action MARK_ITEM_ACTION = new Action("Select");
  /**
   * Action for choosing an item. This action is internally called by the view, do not add it.
   */
  public static final Action CHOOSE_ITEM_ACTION = new Action("Edit");

  private Container container;

  private ItemComponentGenerator itemComponentGenerator = new DefaultItemComponentGenerator();

  private final ArrayList<ItemStateListener> itemMarkListeners = new ArrayList<ItemStateListener>();
  private final ArrayList<ItemStateListener> itemSelectListeners = new ArrayList<ItemStateListener>();

  private final VerticalComponentGroup layout = new VerticalComponentGroup();

  private final Set<Object> selection = new LinkedHashSet<Object>();

  public ListView() {
    super();

    this.setContent(this.layout);

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
        ListView.this.broadcast(itemId, item, selected, ListView.this.itemMarkListeners);
        // update selection
        if(selected)
          ListView.this.selection.add(itemId);
        else ListView.this.selection.remove(itemId);

        ListView.this.handleAction(MARK_ITEM_ACTION, this, itemId);

      }
    });

    Component main = this.getItemComponentGenerator().getComponent(itemId, item, false);
    main.setSizeFull();

    NavigationButton select = new NavigationButton();
    select.addClickListener(new NavigationButton.NavigationButtonClickListener() {

      private static final long serialVersionUID = 20140926;

      @Override
      public void buttonClick(NavigationButton.NavigationButtonClickEvent event) {
        ListView.this.broadcast(itemId, item, true, ListView.this.itemSelectListeners);

        ListView.this.handleAction(CHOOSE_ITEM_ACTION, this, itemId);
      }
    });

    Toolbar row = new Toolbar();
    row.addComponents(marked, main, select);
    return row;
  }

  /**
   * Broadcasts the event to given listeners.
   *
   * @param itemId
   *          Id of an item.
   * @param item
   *          Item.
   * @param selected
   *          Whether or not item is selected.
   * @param listeners
   *          Listeners to notify.
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
    return this.itemComponentGenerator;
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

  @Override
  public Action[] getActions(Object target, Object sender) {
    if(this.getContainerDataSource() != null && this.getContainerDataSource().containsId(target))
      return new Action[]{MARK_ITEM_ACTION, CHOOSE_ITEM_ACTION};
    else return new Action[0];
  }

}
