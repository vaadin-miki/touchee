package org.vaadin.miki.touchee.views.list;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.vaadin.miki.touchee.views.ToucheeView;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.PropertySetChangeEvent;
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

  private Container container;

  private Action markAction;

  private Action chooseAction;

  private ItemComponentGenerator itemComponentGenerator = new DefaultItemComponentGenerator();

  private final VerticalComponentGroup layout = new VerticalComponentGroup();

  private final Set<Object> selection = new LinkedHashSet<Object>();

  private final Container.ItemSetChangeListener itemListener = new Container.ItemSetChangeListener() {

    private static final long serialVersionUID = 20101003;

    @Override
    public void containerItemSetChange(ItemSetChangeEvent event) {
      rebuildList();
    }
  };

  private final Container.PropertySetChangeListener propertyListener = new Container.PropertySetChangeListener() {

    private static final long serialVersionUID = 20101003;

    @Override
    public void containerPropertySetChange(PropertySetChangeEvent event) {
      rebuildList();
    }
  };

  /**
   * Constructs the list.
   */
  public ListView() {
    super();

    this.setContent(this.layout);

  }

  @Override
  protected void onBecomingVisible() {
    super.onBecomingVisible();
    this.rebuildList();
  }

  /**
   * Rebuilds the list.
   */
  protected void rebuildList() {
    this.layout.removeAllComponents();

    if(this.container != null)
      for(Object itemId: this.container.getItemIds())
        this.layout.addComponent(this.getItemComponent(itemId, this.container.getItem(itemId)));
  }

  /**
   * Returns a component that represents an item. When {@link #getMarkAction()} is defined, a checkbox will be generated and that {@link Action} will be
   * broadcasted upon clicking it. When {@link #getChooseAction()} is defined, a button will be generated and that {@link Action} will be broadcasted upon
   * clicking it. In both cases the target of the action will be the id of an item it deals with.
   * 
   * @param itemId
   *          Id of an item to generate component for.
   * @param item
   *          Item with the data.
   * @return A component that represents the item, together with action-related components.
   */
  protected Component getItemComponent(final Object itemId, final Item item) {
    Toolbar row = new Toolbar();

    // marking button
    if(this.getMarkAction() != null) {
      CheckBox marked = new CheckBox();
      marked.setValue(this.selection.contains(itemId));
      marked.addValueChangeListener(new Property.ValueChangeListener() {

        private static final long serialVersionUID = 20140926;

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
          boolean selected = (Boolean)event.getProperty().getValue();
          // update selection
          if(selected)
            ListView.this.selection.add(itemId);
          else ListView.this.selection.remove(itemId);

          if(getMarkAction() != null)
            ListView.this.handleAction(getMarkAction(), ListView.this, itemId);

        }
      });
      row.addComponent(marked);
    }

    // main component
    Component main = this.getItemComponentGenerator().getComponent(itemId, item, false);
    main.setSizeFull();
    row.addComponent(main);

    // selection button
    if(this.getChooseAction() != null) {
      NavigationButton select = new NavigationButton();
      select.addClickListener(new NavigationButton.NavigationButtonClickListener() {

        private static final long serialVersionUID = 20140926;

        @Override
        public void buttonClick(NavigationButton.NavigationButtonClickEvent event) {

          if(getChooseAction() != null)
            ListView.this.handleAction(getChooseAction(), ListView.this, itemId);
        }
      });
      row.addComponent(select);
    }
    return row;
  }

  /**
   * Clears selection.
   */
  public void clearSelection() {

  }

  /**
   * Returns the set of currently selected item ids.
   * 
   * @return Currently selected item ids.
   */
  public Set<Object> getMarkedItems() {
    return Collections.unmodifiableSet(this.selection);
  }

  @Override
  public void setContainerDataSource(Container newDataSource) {
    // remove old listeners
    if(this.container != null) {
      if(this.container instanceof Container.ItemSetChangeNotifier)
        ((Container.ItemSetChangeNotifier)this.container).removeItemSetChangeListener(this.itemListener);
      if(this.container instanceof Container.PropertySetChangeNotifier)
        ((Container.PropertySetChangeNotifier)this.container).removePropertySetChangeListener(this.propertyListener);
    }

    this.container = newDataSource;

    // add listeners
    if(this.container != null) {
      if(this.container instanceof Container.ItemSetChangeNotifier)
        ((Container.ItemSetChangeNotifier)this.container).addItemSetChangeListener(this.itemListener);
      if(this.container instanceof Container.PropertySetChangeNotifier)
        ((Container.PropertySetChangeNotifier)this.container).addPropertySetChangeListener(this.propertyListener);

    }
    this.rebuildList();
  }

  @Override
  public Container getContainerDataSource() {
    return this.container;
  }

  /**
   * Returns currently used item component generator.
   * 
   * @return Currently used item component generator.
   */
  public ItemComponentGenerator getItemComponentGenerator() {
    return this.itemComponentGenerator;
  }

  /**
   * Sets the item component generator and rebuilds the list.
   * 
   * @param itemComponentGenerator
   *          New item component generator.
   */
  public void setItemComponentGenerator(ItemComponentGenerator itemComponentGenerator) {
    this.itemComponentGenerator = itemComponentGenerator;
    this.rebuildList();
  }

  @Override
  public Action[] getActions(Object target, Object sender) {
    if(this.getContainerDataSource() != null && this.getContainerDataSource().containsId(target))
      return new Action[]{this.getMarkAction(), this.getChooseAction()};
    else return new Action[0];
  }

  /**
   * Returns current action that happens when an item is marked.
   * 
   * @return Current marking action.
   */
  public Action getMarkAction() {
    return markAction;
  }

  /**
   * Sets the new marking action. Setting this to <b>null</b> disables marking.
   * 
   * @param markAction
   *          Action to broadcast when an item is marked.
   */
  public void setMarkAction(Action markAction) {
    Action old = this.markAction;
    this.markAction = markAction;
    this.rebuildIfNeeded(old, markAction);
  }

  /**
   * Checks whether or not rebuild is needed - and rebuilds the UI, if so.
   * 
   * @param oldAction
   *          Previously assigned action.
   * @param newAction
   *          Newly assigned action.
   * @return <b>true</b> when a rebuild was needed and done, i.e. the priviledges have changed (exactly one of the parameters is <b>null</b>); <b>false</b>
   *         otherwise.
   */
  private boolean rebuildIfNeeded(Action oldAction, Action newAction) {
    if(oldAction == null ^ newAction == null) {
      this.rebuildList();
      return true;
    }
    else return false;
  }

  /**
   * Returns current action that happens when an item is chosen (selected).
   * 
   * @return Current choosing action.
   */
  public Action getChooseAction() {
    return chooseAction;
  }

  /**
   * Sets the new marking action. Setting this to <b>null</b> disables choosing.
   * 
   * @param chooseAction
   *          Action to broadcast when an item is chosen.
   */
  public void setChooseAction(Action chooseAction) {
    Action old = this.chooseAction;
    this.chooseAction = chooseAction;
    this.rebuildIfNeeded(old, chooseAction);
  }

}
