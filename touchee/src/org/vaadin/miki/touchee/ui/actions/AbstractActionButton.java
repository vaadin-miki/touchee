package org.vaadin.miki.touchee.ui.actions;

import com.vaadin.event.Action;
import com.vaadin.ui.Button;

/**
 * A base class for action buttons. Encapsulates handler, sender and target, leaving actions to the subclasses. This class itself is registered as its own
 * button click listener.
 *
 * @author miki
 *
 */
public abstract class AbstractActionButton extends Button implements Button.ClickListener {

  private static final long serialVersionUID = 20141002;

  private Action.Handler handler;
  private Object sender;
  private Object target;

  /**
   * Constructs the button with no properties defined.
   */
  public AbstractActionButton() {
    super();
    this.addClickListener(this);
  }

  /**
   * Returns the current handler of actions invoked by the button.
   *
   * @return Current handler of actions.
   */
  public Action.Handler getHandler() {
    return this.handler;
  }

  /**
   * Sets the handler of the actions invoked by the button.
   *
   * @param handler
   *          New handler of actions.
   */
  public void setHandler(Action.Handler handler) {
    this.handler = handler;
  }

  /**
   * Returns current sender of actions. When this is <b>null</b>, this button will be used as a sender in broadcasting the action.
   *
   * @return Current sender of actions.
   */
  public Object getSender() {
    return this.sender;
  }

  /**
   * Sets the new sender of actions. When this is <b>null</b>, this button will be used as a sender in broadcasting the action.
   *
   * @param sender
   *          New sender of actions.
   */
  public void setSender(Object sender) {
    this.sender = sender;
  }

  /**
   * Gets the target of actions.
   *
   * @return Target of actions.
   */
  public Object getTarget() {
    return this.target;
  }

  /**
   * Sets the new target of actions.
   *
   * @param target
   *          New target of actions.
   */
  public void setTarget(Object target) {
    this.target = target;
  }

}
