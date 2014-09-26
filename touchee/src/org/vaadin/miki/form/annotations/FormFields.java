package org.vaadin.miki.form.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pre-Java8 annotation for a field that appears in multiple forms.
 * @author miki
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormFields {

	public FormField[] fields();
	
}
