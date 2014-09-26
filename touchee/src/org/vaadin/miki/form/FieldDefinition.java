package org.vaadin.miki.form;

import java.util.Collection;

/**
 * Contains a definition of a field to be displayed in a form.
 * 
 * @author miki
 *
 */
public interface FieldDefinition {

	/**
	 * Returns an identifier of a field. This is usually a property name that
	 * the field should be bound to.
	 * 
	 * @return Field identifier.
	 */
	public String getIdentifier();

	/**
	 * Returns a property name for a field. In most cases this should be the
	 * same as {@link #getIdentifier()}, but it is provided here for extra
	 * flexibility.
	 * 
	 * @return Id of a property the field should be bound to in a given form.
	 */
	public String getPropertyId();

	/**
	 * Returns the caption of the field.
	 * 
	 * @return Caption of the field.
	 */
	public String getFieldCaption();

	/**
	 * Returns the value type of the field.
	 * 
	 * @return Value type of the field.
	 */
	public Class<?> getValueType();

	/**
	 * Returns any additional information the field has and the field builder
	 * can use. This should never be <b>null</b>.
	 * 
	 * @return A collection of any additional information the field builder
	 *         should use when constructing this field.
	 */
	public Collection<?> getAdditionalInformation();
}
