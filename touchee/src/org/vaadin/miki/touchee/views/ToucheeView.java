package org.vaadin.miki.touchee.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.miki.touchee.ui.actions.SequenceActionButton;
import org.vaadin.miki.touchee.ui.actions.SingleActionButton;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.event.Action;
import com.vaadin.ui.Button;

/**
 * Base class for all views. Abstracts the logic of buttons and button clicks in the toolbar.<br/>
 * It is an {@link com.vaadin.event.Action.Container} because it allows adding handlers to capture actions. It is an {@link com.vaadin.event.Action.Handler}
 * because it handles actions by forwarding them to registered handlers.<br/>
 * Action handlers will be called when an action happens. Source of an action is always this view, whereas target is always implementation- and action-specific
 * and defined by a view.
 *
 * @author miki
 *
 */
public abstract class ToucheeView extends NavigationView implements Action.Container, Action.Handler {

  private static final long serialVersionUID = 20140930L;

  private final Toolbar toolbar = new Toolbar();

  private final ArrayList<Action.Handler> actionHandlers = new ArrayList<Action.Handler>();

  private final Map<Action, Button> actionButtons = new HashMap<Action, Button>();

  private final Map<String, Button> sequenceButtons = new HashMap<String, Button>();

  /**
   * Constructs the view and initialises the toolbar.
   */
  public ToucheeView() {
    super();

    this.setToolbar(this.toolbar);
  }

  /**
   * Adds buttons to the toolbar.
   *
   * @param buttons
   *          Buttons to add to the toolbar.
   */
  public void addButton(Button... buttons) {
    this.toolbar.addComponents(buttons);
  }

  /**
   * Removes buttons from the toolbar.
   *
   * @param buttons
   *          Buttons to remove from the toolbar.
   */
  public void removeButton(Button... buttons) {
    for(Button b: buttons)
      this.toolbar.removeComponent(b);
  }

  /**
   * Adds one or more actions. Those are represented as buttons on the toolbar and are invoked when a button is clicked.
   *
   * @param actions
   *          Actions to add.
   */
  public void addAction(Action... actions) {
    for(Action action: actions) {
      SingleActionButton button = new SingleActionButton(action, this, this);
      this.addButton(button);
      this.actionButtons.put(action, button);
    }
  }

  /**
   * Adds a named sequence of actions. Those are represented as a single button on the toolbar. Each of them is invoked in the order given when the button is
   * clicked.
   *
   * @param name
   *          Name to associate the sequence with, and to be used as a caption of the button.
   * @param actions
   *          Actions to be associated with the button.
   */
  public void addAction(String name, Action... actions) {
    SequenceActionButton button = new SequenceActionButton(name, actions);
    this.addButton(button);
    this.sequenceButtons.put(name, button);
  }

  /**
   * Removes one or more actions. A corresponding button is removed as well.
   *
   * @param actions
   *          Actions to remove.
   */
  public void removeAction(Action... actions) {
    for(Action action: actions)
      if(this.actionButtons.containsKey(action))
        this.removeButton(this.actionButtons.get(action));
  }

  /**
   * Removes one or more action sequence buttons.
   *
   * @param actions
   *          Names of sequences to remove.
   */
  public void removeAction(String... actions) {
    for(String action: actions)
      if(this.sequenceButtons.containsKey(action))
        this.removeButton(this.sequenceButtons.get(action));
  }

  @Override
  public void addActionHandler(Action.Handler actionHandler) {
    this.actionHandlers.add(actionHandler);
  }

  @Override
  public void removeActionHandler(Action.Handler actionHandler) {
    this.actionHandlers.remove(actionHandler);
  }

  @Override
  public void handleAction(Action action, Object sender, Object target) {
    for(Action.Handler handler: this.actionHandlers)
      handler.handleAction(action, sender, target);
  }

}
