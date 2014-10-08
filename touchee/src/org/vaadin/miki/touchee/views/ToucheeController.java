package org.vaadin.miki.touchee.views;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.miki.form.annotations.AnnotationFormBuilder;
import org.vaadin.miki.touchee.configuration.ContainerDefinition;
import org.vaadin.miki.touchee.data.User;
import org.vaadin.miki.touchee.views.edit.EditView;
import org.vaadin.miki.touchee.views.list.ListView;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * Controls mobile navigation.
 *
 * @author miki
 *
 */
public class ToucheeController implements Action.Handler {

  private static final long serialVersionUID = 20141002;

  private static final Action LOG_IN = new Action("Log in");
  private static final Action CLEAR = new Action("Clear");
  private static final Action COMMIT = new Action("Save");
  private static final Action RESTORE = new Action("Undo");
  private static final Action CLOSE = new Action("Leave");
  private static final Action NEW_ITEM = new Action("New");
  private static final Action DELETE = new Action("Delete marked");
  private static final Action MARK_ITEM_ACTION = new Action("!Select");
  private static final Action CHOOSE_ITEM_ACTION = new Action("!Edit");
  private static final Action CHOOSE_CONTAINER_ACTION = new Action("!List");

  private static final String DEFINITION_PROPERTY = "definition";

  private final NavigationManager manager = new NavigationManager();

  private final Map<Action, Action.Handler> actionHandlers = new HashMap<Action, Action.Handler>();

  private final IndexedContainer containers = new IndexedContainer();

  private boolean ignoringUnregisteredActions = true;

  /**
   * Constructs the controller for given UI.
   *
   * @param ui
   *          UI.
   */
  public ToucheeController(UI ui) {
    super();
    ui.setContent(this.manager);

    this.containers.addContainerProperty(DEFINITION_PROPERTY, ContainerDefinition.class, null);

    this.actionHandlers.put(LOG_IN, new Action.Handler() {

      private static final long serialVersionUID = 20141003;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        Map<?, ?> map = (Map<?, ?>)target;
        if("test".equals(map.get("username")) && "test".equals(map.get("password")))
          ToucheeController.this.manager.navigateTo(ToucheeController.this.createOverview());
        else Notification.show("Invalid credentials", "Please log in as test/test", Notification.Type.WARNING_MESSAGE);
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{LOG_IN};
      }
    });

    this.actionHandlers.put(CLEAR, new Action.Handler() {
      private static final long serialVersionUID = 20141003;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        ((EditView)sender).clear();
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{CLEAR};
      }
    });

    this.actionHandlers.put(CLOSE, new Action.Handler() {
      private static final long serialVersionUID = 20141003;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        ToucheeController.this.manager.navigateBack();
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{CLOSE};
      }
    });

    this.actionHandlers.put(DELETE, new Action.Handler() {

      private static final long serialVersionUID = 20141003;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        ListView view = (ListView)sender;
        for(Object itemId: view.getMarkedItems())
          view.getContainerDataSource().removeItem(itemId);
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{DELETE};
      }
    });

    this.actionHandlers.put(COMMIT, new Action.Handler() {

      private static final long serialVersionUID = 20141003;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        ((EditView)sender).commit();
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{COMMIT};
      }
    });

    this.actionHandlers.put(RESTORE, new Action.Handler() {

      private static final long serialVersionUID = 20141003;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        ((EditView)sender).discard();
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{RESTORE};
      }
    });

    this.actionHandlers.put(CHOOSE_ITEM_ACTION, new Action.Handler() {
      private static final long serialVersionUID = 20141003;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        ListView view = (ListView)sender;
        BeanItem<?> item = (BeanItem<?>)view.getContainerDataSource().getItem(target);
        ToucheeController.this.manager.navigateTo(ToucheeController.this.createEditView(item));
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{CHOOSE_ITEM_ACTION};
      }
    });

    this.actionHandlers.put(NEW_ITEM, new Action.Handler() {
      private static final long serialVersionUID = 20141003;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        ListView list = (ListView)sender;
        Object itemId = list.getContainerDataSource().addItem();
        ToucheeController.this.manager.navigateTo(ToucheeController.this.createEditView((BeanItem<?>)list.getContainerDataSource().getItem(itemId)));
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{NEW_ITEM};
      }
    });

    this.actionHandlers.put(CHOOSE_CONTAINER_ACTION, new Action.Handler() {

      private static final long serialVersionUID = 20141005;

      @Override
      public void handleAction(Action action, Object sender, Object target) {
        ContainerDefinition def = ContainerDefinition.class.cast(ToucheeController.this.containers.getContainerProperty(target, DEFINITION_PROPERTY).getValue());

        ToucheeController.this.manager.navigateTo(ToucheeController.this.createListView(def));
      }

      @Override
      public Action[] getActions(Object target, Object sender) {
        return new Action[]{CHOOSE_CONTAINER_ACTION};
      }
    });
  }

  /**
   * Configures the controller to support navigation from there.
   *
   * @param definitions
   *          Container definitions.
   */
  @SuppressWarnings("unchecked")
  public void configure(ContainerDefinition... definitions) {
    this.containers.removeAllItems();
    for(ContainerDefinition definition: definitions) {
      Object id = this.containers.addItem();
      this.containers.getContainerProperty(id, DEFINITION_PROPERTY).setValue(definition);
    }

  }

  private Component createOverview() {
    ListView view = new ListView();
    view.setCaption("Overview");
    view.setMarkAction(null);
    view.setChooseAction(CHOOSE_CONTAINER_ACTION);
    view.addActionHandler(this);
    view.setContainerDataSource(this.containers);
    return view;
  }

  private Component createListView(ContainerDefinition metaData) {
    ListView view = new ListView();
    if(metaData.isEditingAllowed()) {
      view.setMarkAction(MARK_ITEM_ACTION);
      view.setChooseAction(CHOOSE_ITEM_ACTION);
    }
    if(metaData.isAddingAllowed())
      view.addAction(NEW_ITEM);
    if(metaData.isDeletingAllowed())
      view.addAction(DELETE);
    view.setCaption(metaData.getContainerTitle());
    view.addActionHandler(this);
    view.setContainerDataSource(metaData.getContainer());

    return view;
  }

  private Component createEditView(BeanItem<?> item) {

    Class<?> backend = item.getBean().getClass();

    AnnotationFormBuilder builder = new AnnotationFormBuilder();
    builder.addClass(backend, "edit");

    EditView view = new EditView(new AnnotationFormBuilder(backend, "edit"));
    view.setItemDataSource(item);

    view.addAction("Save", COMMIT, CLOSE);
    view.addAction(RESTORE, CLOSE);
    view.addActionHandler(this);
    return view;
  }

  private Component createLoginView() {
    EditView login = new EditView(new AnnotationFormBuilder(User.class, "login"));

    login.setCaption("Log in");

    login.addActionHandler(this);
    login.addAction(LOG_IN, CLEAR);

    return login;
  }

  /**
   * Navigates to the start view. If a user is already logged in, it is the overview. Otherwise, it is the login screen.
   */
  public void navigateToStart() {
    if(VaadinSession.getCurrent().getAttribute(User.class) == null)
      this.manager.navigateTo(this.createLoginView());
    else this.manager.navigateTo(this.createOverview());

  }

  @Override
  public Action[] getActions(Object target, Object sender) {
    if(this.manager.getCurrentComponent() instanceof Action.Handler)
      return ((Action.Handler)this.manager.getCurrentComponent()).getActions(target, sender);
    else return new Action[0];
  }

  /**
   * Sets an action handler for a given action.
   *
   * @param action
   *          Action to handle.
   * @param handler
   *          Handler to forward the action to.
   */
  public void setActionHandler(Action action, Action.Handler handler) {
    this.actionHandlers.put(action, handler);
  }

  /**
   * Removes handler for a given action.
   *
   * @param action
   *          Action to remove handler for.
   */
  public void removeActionHandler(Action action) {
    this.actionHandlers.remove(action);
  }

  @Override
  public void handleAction(Action action, Object sender, Object target) {
    if(this.actionHandlers.containsKey(action))
      this.actionHandlers.get(action).handleAction(action, sender, target);
    else if(!this.isIgnoringUnregisteredActions())
      Notification.show("Cannot perform action", "This action (" + action.getCaption()
          + ") cannot be performed, because no handler was registered for it in the controller, and there is no default handler.", Notification.Type.ERROR_MESSAGE);
  }

  /**
   * Checks whether or not unregistered actions will do nothing (<b>true</b>) or throw an exception (<b>false</b>).
   *
   * @return Whether or not unregistered actions are ignored.
   */
  public boolean isIgnoringUnregisteredActions() {
    return this.ignoringUnregisteredActions;
  }

  /**
   * Sets the way unregistered actions are handled - ignored (<b>true</b>, default) or not (<b>false</b>).
   *
   * @param ignoringUnregisteredActions
   *          Whether or not to ignore unregistered actions.
   */
  public void setIgnoringUnregisteredActions(boolean ignoringUnregisteredActions) {
    this.ignoringUnregisteredActions = ignoringUnregisteredActions;
  }
}
