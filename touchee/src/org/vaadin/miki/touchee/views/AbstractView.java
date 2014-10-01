package org.vaadin.miki.touchee.views;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.ui.Button;

/**
 * Base class for all views. Abstracts the logic of buttons and button clicks in the toolbar.
 *
 * @author miki
 *
 */
public class AbstractView extends NavigationView {

  private static final long serialVersionUID = 20140930L;

  private final Toolbar toolbar = new Toolbar();

  /**
   * Interface for operations to be broadcasted by the view.
   *
   * @author miki
   *
   */
  public static interface Operation {

    /**
     * Operation was triggered.
     *
     */
    public void perform();
  }

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

  /**
   * Adds a named operation as a button in the toolbar.
   *
   * @param name
   *          Name.
   * @param operation
   *          Operation. Usually this is provided by the view itself.
   * @return Button that was added as a result of the operation.
   */
  public Button addOperation(final String name, final Operation operation) {
    Button button = new Button(name, new Button.ClickListener() {

      private static final long serialVersionUID = 20141001;

      @Override
      public void buttonClick(Button.ClickEvent event) {
        operation.perform();
      }
    });
    this.addButton(button);
    return button;
  }

}
