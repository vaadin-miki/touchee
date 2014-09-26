package org.vaadin.miki.form;

import com.vaadin.ui.Field;

/**
 * Interface for field builders.
 * @author miki
 *
 */
public interface FormBuilderFieldFactory {

	/**
	 * Builds a field component for a given field of a backend class, with identifiers and type information found in annotations.
	 * @param fieldType Type of the field.
	 * @param fieldIdentifier Field identifier, as specified in the annotation. Otherwise null.
	 * @param fieldCaption Caption of the field.
	 * @param additionalInformation Any additional information specified in the field definition.
	 * @return A field component. Should throw runtime exceptions when things fail.
	 */
	public Field<?> buildField(Class<?> fieldType, String fieldIdentifier, String fieldCaption, Object... additionalInformation);
	
}
