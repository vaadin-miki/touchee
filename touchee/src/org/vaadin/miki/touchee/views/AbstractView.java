package org.vaadin.miki.touchee.views;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.ui.Button;

/**
 * Base class for all views. Abstracts the logic of buttons and button clicks in the bottom toolbar.
 * 
 * @author miki
 *
 */
public class AbstractView extends NavigationView {

  private static final long serialVersionUID = 20140930L;

  private final Toolbar toolbar = new Toolbar();

  /**
   * Constructs the view and initialises the toolbar.
   */
  public AbstractView() {
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

}
