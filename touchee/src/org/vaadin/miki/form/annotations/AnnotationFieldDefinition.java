package org.vaadin.miki.form.annotations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.vaadin.miki.form.FieldDefinition;

import com.vaadin.data.Validator;

/**
 * Implementation of {@link FieldDefinition} that reads its contents from a given {@link FormField} annotation. The values are copied, meaning that the
 * annotation object is no longer needed.
 * 
 * @author miki
 *
 */
public class AnnotationFieldDefinition implements FieldDefinition {

  /**
   * Defines the default type of a parameter for validator construction.
   */
  public static final Class<?> DEFAULT_VALIDATOR_PARAMETER_TYPE = String.class;

  /**
   * Public static method that is needed for typecasting.
   */
  public static final String TYPECASTING_METHOD_NAME = "valueOf";

  private final String identifier;

  private final String propertyId;

  private final Class<?> valueType;

  private final String fieldCaption;

  private final Collection<?> additionalInformation;

  private final Collection<Validator> validators = new ArrayList<Validator>();

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

    // obtain validators
    if(annotation.validate() != null)
      for(ValidateWith validateWith: annotation.validate()) {
        try {
          this.validators.add(this.getValidator(validateWith));
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
          e.printStackTrace();
        }
      }

  }

  /**
   * Constructs an object of given type, based on passed parameters and their types.
   * 
   * @param type
   *          Type of an object to create.
   * @param parameters
   *          Parameters to pass to the constructor.
   * @param parameterTypes
   *          Parameter types.
   * @return Created object.
   * @throws InstantiationException
   *           When an object cannot be created via reflection.
   * @throws IllegalAccessException
   *           When an object cannot be created via reflection.
   * @throws IllegalArgumentException
   *           When an object cannot be created via reflection.
   * @throws InvocationTargetException
   *           When an object cannot be created via reflection.
   * @throws NoSuchMethodException
   *           When an object cannot be created via reflection.
   * @throws SecurityException
   *           When an object cannot be created via reflection.
   */
  protected <TYPE> TYPE construct(Class<? extends TYPE> type, String[] parameters, Class<?>[] parameterTypes) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    final TYPE result;
    if(parameters.length == 0)
      result = type.newInstance();
    // parameters are defined, which means they may not be strings
    else {
      Class<?>[] types = new Class<?>[parameters.length];
      Object[] values = new Object[parameters.length];
      // go through types and typecast as needed
      for(int zmp1 = 0; zmp1 < types.length; zmp1++) {
        // set the type
        if(zmp1 < parameterTypes.length)
          types[zmp1] = parameterTypes[zmp1];
        else types[zmp1] = DEFAULT_VALIDATOR_PARAMETER_TYPE;
        // attempt to typecast the value
        String string = parameters[zmp1];
        if(types[zmp1] != string.getClass())
          values[zmp1] = types[zmp1].getMethod(TYPECASTING_METHOD_NAME, DEFAULT_VALIDATOR_PARAMETER_TYPE).invoke(null, string);
        else values[zmp1] = string;
      }
      // now parameters are ready, find a constructor
      result = type.getConstructor(types).newInstance(values);
    }
    return result;

  }

  /**
   * Constructs the validator from a given validator information.
   * 
   * @param annotation
   *          Annotation with information.
   * @return A validator, if one can be successfully created; <b>null</b> otherwise. Exceptions are supposed to be handled silently.
   * @throws IllegalAccessException
   *           When constructing the validator via reflection failed.
   * @throws InstantiationException
   *           When constructing the validator via reflection failed.
   * @throws SecurityException
   *           When constructing the validator via reflection failed.
   * @throws NoSuchMethodException
   *           When constructing the validator via reflection failed.
   * @throws InvocationTargetException
   *           When constructing the validator via reflection failed.
   * @throws IllegalArgumentException
   *           When constructing the validator via reflection failed.
   */
  protected Validator getValidator(ValidateWith annotation) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
    Class<? extends Validator> validatorClass = annotation.validator();
    return this.construct(validatorClass, annotation.parameters(), annotation.parameterTypes());
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

  @Override
  public Collection<Validator> getValidators() {
    return this.validators;
  }

}
