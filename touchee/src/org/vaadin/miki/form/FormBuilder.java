package org.vaadin.miki.form;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;

/**
 * Interface for form builders. In fact, they build {@link FieldGroup}s based on {@link FieldDefinition}s, with an option to bind those fields to the data
 * source by setting it through {@link com.vaadin.data.Item.Editor#setItemDataSource(Item)}.
 * 
 * @author miki
 *
 */
public interface FormBuilder extends Item.Editor {

  /**
   * Builds form components based on the current state of the builder. A field factory provided through {@link #setFieldFactory(FormBuilderFieldFactory)} will
   * be used to construct fields. If an item data source is set (through { {@link #setItemDataSource(Item)}), matching fields are bound to that item properties.
   * 
   * @return {@link FieldGroup} with fields.
   */
  public FieldGroup buildFieldGroup();

  /**
   * Gets the current field factory.
   * 
   * @return Current field factory.
   */
  public FormBuilderFieldFactory getFieldFactory();

  /**
   * Sets the field factory to be used for building fields.
   * 
   * @param fieldFactory
   *          New field factory.
   */
  public void setFieldFactory(FormBuilderFieldFactory fieldFactory);

}
