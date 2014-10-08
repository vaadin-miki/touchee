package org.vaadin.miki.form;

import com.vaadin.data.fieldgroup.FieldGroup;

/**
 * Interface for form builders. In fact, they build {@link FieldGroup}s based on {@link FieldDefinition}s. The resulting field group, obtainable with
 * {@link #buildFieldGroup()}, should be used to bind it to data.
 * 
 * @author miki
 *
 */
public interface FormBuilder {

  /**
   * Builds form components based on the current state of the builder. A field factory provided through {@link #setFieldFactory(FormBuilderFieldFactory)} will
   * be used to construct fields. Matching fields are unbound to any particular item, though they are bound to property ids.
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
