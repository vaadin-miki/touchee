package org.vaadin.miki.touchee.ui.actions;

import com.vaadin.event.Action;
import com.vaadin.ui.Button;

/**
 * A button that, when clicked, invokes a given action on a passed handler. Unless associated with the button at construction time, the target of the action is
 * undefined and the sender is the button itself.
 *
 * @author miki
 *
 */
public class SingleActionButton extends AbstractActionButton {

  private static final long serialVersionUID = 20141002;

  private Action action;

  /**
   * Constructs the button with no properties defined.
   */
  public SingleActionButton() {
    super();
  }

  /**
   * Constructs the button for given action and handler. Caption and icon of the button will be copied from the action.
   *
   * @param action
   *          Action to perform when clicked.
   * @param handler
   *          Handler to notify about the click.
   */
  public SingleActionButton(Action action, Action.Handler handler) {
    this();
    this.setAction(action);
    this.setHandler(handler);

    this.setCaption(action.getCaption());
    this.setIcon(action.getIcon());

  }

  /**
   * Constructs the button for given action, handler and sender. Caption and icon of the button will be copied from the action.
   *
   * @param action
   *          Action to perform when clicked.
   * @param handler
   *          Handler to notify about the click.
   * @param sender
   *          Sender of the action, to be passed to the handler.
   */
  public SingleActionButton(Action action, Action.Handler handler, Object sender) {
    this(action, handler);
    this.setSender(sender);
  }

  /**
   * Constructs the button for given action, handler, sender and target. Caption and icon of the button will be copied from the action.
   *
   * @param action
   *          Action to perform when clicked.
   * @param handler
   *          Handler to notify about the click.
   * @param sender
   *          Sender of the action, to be passed to the handler.
   * @param target
   *          Target of the action, to be passed to the handler.
   */
  public SingleActionButton(Action action, Action.Handler handler, Object sender, Object target) {
    this(action, handler, sender);
    this.setTarget(target);
  }

  /**
   * Returns the action associated with the button.
   *
   * @return Action of the button.
   */
  public Action getAction() {
    return this.action;
  }

  /**
   * Sets the action associated with the button.
   *
   * @param action
   *          New action of the button.
   */
  public void setAction(Action action) {
    this.action = action;
  }

  @Override
  public void buttonClick(Button.ClickEvent event) {
    if(this.getHandler() != null && this.getAction() != null) this.getHandler()
        .handleAction(this.getAction(), this.getSender() == null ? this : this.getSender(), this.getTarget());
  }

}
