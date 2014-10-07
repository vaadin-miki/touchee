package org.vaadin.miki.form.annotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.vaadin.miki.form.FieldDefinition;

/**
 * Implementation of {@link FieldDefinition} that reads its contents from a given {@link FormField} annotation. The values are copied, meaning that the
 * annotation object is no longer needed.
 * 
 * @author miki
 *
 */
public class AnnotationFieldDefinition implements FieldDefinition {

  private final String identifier;

  private final String propertyId;

  private final Class<?> valueType;

  private final String fieldCaption;

  private final Collection<?> additionalInformation;

  /**
   * Constructs the definition from a given annotation for a field and its type.
   * 
   * @param annotation
   *          Annotation.
   * @param fieldName
   *          Name of the field.
   * @param valueType
   *          Type of the field.
   */
  public AnnotationFieldDefinition(FormField annotation, String fieldName, Class<?> valueType) {
    this.valueType = valueType;

    // info is copied directly from the annotation
    this.additionalInformation = Collections.unmodifiableCollection(Arrays.asList(annotation.info()));

    if(FormField.DEFAULT_FIELD_IDENTIFIER.equals(annotation.identifier()))
      this.identifier = fieldName;
    else this.identifier = annotation.identifier();

    this.propertyId = fieldName;

    // default caption means field name upper-cased
    if(FormField.DEFAULT_FIELD_CAPTION.equals(annotation.caption()))
      this.fieldCaption = fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));
    else this.fieldCaption = annotation.caption();
  }

  @Override
  public String getIdentifier() {
    return this.identifier;
  }

  @Override
  public String getPropertyId() {
    return this.propertyId;
  }

  @Override
  public String getFieldCaption() {
    return this.fieldCaption;
  }

  @Override
  public Class<?> getValueType() {
    return this.valueType;
  }

  @Override
  public Collection<?> getAdditionalInformation() {
    return this.additionalInformation;
  }

}
