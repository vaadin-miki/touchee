package org.vaadin.miki.form.annotations;

import com.vaadin.data.Validator;

/**
 * Marker annotation for information about validators.
 * 
 * @author miki
 *
 */
public @interface ValidateWith {

  /**
   * Class of the validator to use.
   */
  public Class<? extends Validator> validator();

  /**
   * Parameters to pass to the validator's constructor.
   */
  public String[] parameters() default {};

  /**
   * Types of parameters to use. A static method .valueOf(String) <b>must exist</b> in that class for this type information to work.
   */
  public Class<?>[] parameterTypes() default {};

}
