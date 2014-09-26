package org.vaadin.miki.form.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a field in a class that is associated with a login credential
 * field.
 * 
 * @author miki
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {

	/**
	 * Identifier of the form this field applies to.
	 */
	public String form() default DEFAULT_FORM_ID;

	/**
	 * Identifier of the field. When set to default, name of the field will be
	 * used.
	 */
	public String identifier() default DEFAULT_FIELD_IDENTIFIER;

	/**
	 * Caption of the field. By default it is the same as the field name, with
	 * first letter capital.
	 */
	public String caption() default DEFAULT_FIELD_CAPTION;

	/**
	 * Information about the type of the field, if needed to be passed to the
	 * field builder.
	 */
	public String[] info() default {};

	/**
	 * Default form identifier.
	 */
	public static final String DEFAULT_FORM_ID = "";

	public static final String DEFAULT_FIELD_IDENTIFIER = "";

	// this is a zero-width space, meaning that there IS a default caption that
	// would just be rendered non-visible
	// this is to allow explicitly setting no caption with ""
	public static final String DEFAULT_FIELD_CAPTION = "\u200b";

}
