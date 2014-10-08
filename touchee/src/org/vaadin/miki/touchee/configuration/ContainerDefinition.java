package org.vaadin.miki.touchee.configuration;

import org.vaadin.miki.touchee.views.ToucheeController;

import com.vaadin.data.Container;

/**
 * A definition (configuration information) of a container, to be included and navigated from in a {@link ToucheeController}.
 *
 * @author miki
 *
 */
public interface ContainerDefinition {

  /**
   * Returns the container with the data.
   *
   * @return Container.
   */
  public Container getContainer();

  /**
   * Returns the title of the container, to be displayed in the overview.
   *
   * @return Title of the container.
   */
  public String getContainerTitle();

  /**
   * Whether or not adding new items is allowed.
   *
   * @return <b>true</b> when adding items is allowed, <b>false</b> otherwise.
   */
  public boolean isAddingAllowed();

  /**
   * Whether or not an item with the given id can be modified.
   *
   * @return <b>true</b> when the item with the given id can be modified, <b>false</b> otherwise.
   */
  public boolean isEditingAllowed();

  /**
   * Whether or not an item with the given id can be deleted.
   *
   * @return <b>true</b> when the item with the given id can be modified, <b>false</b> otherwise.
   */
  public boolean isDeletingAllowed();

}
