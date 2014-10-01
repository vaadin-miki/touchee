package org.vaadin.miki.touchee.operations;

/**
 * Interface for operations to be broadcasted by the view.
 *
 * @author miki
 *
 */
public interface Operation {

  /**
   * Operation was triggered.
   *
   */
  public void perform();
}