package org.vaadin.miki.touchee.configuration.simple;

import org.vaadin.miki.touchee.configuration.ContainerDefinition;

import com.vaadin.data.Container;

/**
 * Reference implementation of container definition.
 * 
 * @author miki
 *
 */
public class SimpleContainerDefinition implements ContainerDefinition {

  private final Container container;
  private final String title;
  private final boolean adding;
  private final boolean editing;
  private final boolean deleting;

  /**
   * Constructs the definition.
   * 
   * @param container
   *          Container to define.
   * @param title
   *          Title of the container.
   * @param adding
   *          Whether or not adding new items is allowed.
   * @param editing
   *          Whether or not editing existing items is allowed.
   * @param deleting
   *          Whether or not deleting existing items is allowed.
   */
  public SimpleContainerDefinition(Container container, String title, boolean adding, boolean editing, boolean deleting) {
    this.container = container;
    // TODO Auto-generated constructor stub
    this.title = title;
    this.adding = adding;
    this.editing = editing;
    this.deleting = deleting;
  }

  @Override
  public Container getContainer() {
    return this.container;
  }

  @Override
  public String getContainerTitle() {
    return this.title;
  }

  @Override
  public boolean isAddingAllowed() {
    return this.adding;
  }

  @Override
  public boolean isEditingAllowed() {
    return this.editing;
  }

  @Override
  public boolean isDeletingAllowed() {
    return this.deleting;
  }

  @Override
  public String toString() {
    return this.getContainerTitle();
  }

}
