package org.vaadin.miki.form.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.vaadin.miki.form.FieldDefinition;
import org.vaadin.miki.form.FormBuilder;
import org.vaadin.miki.form.FormBuilderFieldFactory;

import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Field;

/**
 * Form builder that generates a {@link FieldGroup} with fields of corresponding type, based on a given {@link FieldDefinition}s and
 * {@link FormBuilderFieldFactory}.
 * 
 * @author miki
 *
 */
public class FieldDefinitionFormBuilder implements FormBuilder {

  private final ArrayList<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>();

  private FormBuilderFieldFactory fieldFactory = new DefaultFormBuilderFieldFactory();

  /**
   * Constructs the form builder.
   */
  public FieldDefinitionFormBuilder() {
    super();
  }

  /**
   * Constructs the form builder with given definitions.
   * 
   * @param definitions
   *          Field definitions.
   */
  public FieldDefinitionFormBuilder(FieldDefinition... definitions) {
    this();
    this.setFieldDefinitions(Arrays.asList(definitions));
  }

  @Override
  public FieldGroup buildFieldGroup() {
    FieldGroup result = new FieldGroup();
    for(FieldDefinition definition: this.getFieldDefinitions()) {
      Field<?> field = this.getFieldFactory().buildField(definition.getValueType(), definition.getIdentifier(), definition.getFieldCaption(),
          definition.getAdditionalInformation().toArray());
      result.bind(field, definition.getPropertyId());
      for(Validator validator: definition.getValidators())
        field.addValidator(validator);
    }
    return result;
  }

  @Override
  public FormBuilderFieldFactory getFieldFactory() {
    return fieldFactory;
  }

  @Override
  public void setFieldFactory(FormBuilderFieldFactory fieldFactory) {
    this.fieldFactory = fieldFactory;
  }

  /**
   * Returns currently known field definitions.
   * 
   * @return An unmodifiable list of field definitions.
   */
  public List<FieldDefinition> getFieldDefinitions() {
    return Collections.unmodifiableList(this.fieldDefinitions);
  }

  /**
   * Sets the new field definitions. This overwrites the previous definitions. The elements of the passed collection are copied, so changes to it do not affect
   * this class.
   * 
   * @param fieldDefinitions
   *          New field definitions.
   */
  public void setFieldDefinitions(Collection<FieldDefinition> fieldDefinitions) {
    this.clearFieldDefinitions();
    if(fieldDefinitions != null)
      this.fieldDefinitions.addAll(fieldDefinitions);
  }

  /**
   * Adds one or more field definitions.
   * 
   * @param definitions
   *          Definitions to add.
   */
  public void addFieldDefinition(FieldDefinition... definitions) {
    for(FieldDefinition definition: definitions)
      this.fieldDefinitions.add(definition);
  }

  /**
   * Removes one or more field definitions.
   * 
   * @param definitions
   *          Definitions to remove.
   */
  public void removeFieldDefinition(FieldDefinition... definitions) {
    for(FieldDefinition definition: definitions)
      this.fieldDefinitions.remove(definition);
  }

  /**
   * Removes all known field definitions.
   */
  public void clearFieldDefinitions() {
    this.fieldDefinitions.clear();
  }

}
