package org.vaadin.miki.form.simple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.vaadin.miki.form.FieldInformation;
import org.vaadin.miki.form.FormBuilderFieldFactory;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;

/**
 * Default builder for field definitions. It uses a
 * {@link FieldGroupFieldFactory} as a source for building components, unless it
 * is a password or free text field.
 * 
 * @author miki
 *
 */
public class DefaultFormBuilderFieldFactory implements FormBuilderFieldFactory {

	private FieldGroupFieldFactory fieldFactory = new DefaultFieldGroupFieldFactory();

	@Override
	public Field<?> buildField(Class<?> fieldType, String fieldIdentifier, String fieldCaption, Object... additionalInformation) {

		if (additionalInformation == null)
			additionalInformation = new String[0];
		Collection<Object> info = Arrays.asList(additionalInformation);

		Field<?> result;

		if (info.contains(FieldInformation.PASSWORD))
			result = new PasswordField();
		else if (info.contains(FieldInformation.FREE_TEXT))
			result = new TextArea();
		else
			result = this.getFieldFactory().createField(fieldType, Field.class);

		// make sure that null representations are empty strings rather than
		// literal nulls
		try {
			Method m = result.getClass().getMethod("setNullRepresentation", String.class);
			m.invoke(result, this.getNullRepresentation(fieldType));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// ignore this exception
		}

		result.setCaption(fieldCaption);
		if (info.contains(FieldInformation.REQUIRED))
			result.setRequired(true);
		for (Object o : info)
			if (o instanceof FieldInformation.IsRequired) {
				result.setRequired(true);
				result.setRequiredError(((FieldInformation.IsRequired) o).getErrorMessage());
			}

		return result;
	}

	/**
	 * Returns a null representation for the given type.
	 * 
	 * @param fieldType
	 *            Type of field to give the null representation of.
	 * @return Null representation for the given type.
	 */
	// TODO it should probably be a separate class
	protected Object getNullRepresentation(Class<?> fieldType) {
		if (Arrays.asList(Integer.class, int.class, short.class, Short.class, Long.class, long.class).contains(fieldType))
			return 0;
		else if (Arrays.asList(Float.class, float.class, Double.class, Double.class).contains(fieldType))
			return 0.0f;
		else
			return "";
	}

	public FieldGroupFieldFactory getFieldFactory() {
		return fieldFactory;
	}

	public void setFieldFactory(FieldGroupFieldFactory fieldFactory) {
		this.fieldFactory = fieldFactory;
	}

}
