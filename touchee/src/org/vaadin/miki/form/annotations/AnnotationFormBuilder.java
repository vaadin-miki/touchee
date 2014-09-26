package org.vaadin.miki.form.annotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.vaadin.miki.form.FieldDefinition;
import org.vaadin.miki.form.FormBuilder;
import org.vaadin.miki.form.FormBuilderFieldFactory;
import org.vaadin.miki.form.simple.FieldDefinitionFormBuilder;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;

/**
 * Form builder that works on annotations from a given class.
 * @author miki
 *
 */
public class AnnotationFormBuilder implements FormBuilder {

	private static final long serialVersionUID = 20140924;

	private final FieldDefinitionFormBuilder builder = new FieldDefinitionFormBuilder();
	
	private final Map<Class<?>, Collection<FieldDefinition>> classes = new LinkedHashMap<Class<?>, Collection<FieldDefinition>>(); 
	
	public AnnotationFormBuilder() {
		super();
	}
	
	public AnnotationFormBuilder(Class<?> backend) {
		this();
		this.addClass(backend);
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		this.builder.setItemDataSource(newDataSource);
	}

	@Override
	public Item getItemDataSource() {
		return this.builder.getItemDataSource();
	}

	@Override
	public FieldGroup buildFieldGroup() {
		return this.builder.buildFieldGroup();
	}

	@Override
	public FormBuilderFieldFactory getFieldFactory() {
		return this.builder.getFieldFactory();
	}

	@Override
	public void setFieldFactory(FormBuilderFieldFactory fieldFactory) {
		this.builder.setFieldFactory(fieldFactory);
	}

	/**
	 * Adds fields from the definition of the class, based on annotations, with a default form name.
	 * Adding a class that already exists will overwrite current definitions.
	 * @param backend Backend class to scan for field definitions.
	 */
	public void addClass(Class<?> backend) {
		this.addClass(backend, FormField.DEFAULT_FORM_ID);
	}
	
	/**
	 * Adds fields from the definition of the class, based on annotations, with a specified form name.
	 * Adding a class that already exists will overwrite current definitions.
	 * @param backend Backend class to scan for field definitions.
	 * @param formId Id of the form to scan fields for.
	 */
	public void addClass(Class<?> backend, String formId) {
		// remove first
		if(this.classes.containsKey(backend))
			this.removeClass(backend);
		// put empty list that will be updated
		ArrayList<FieldDefinition> definitions = new ArrayList<FieldDefinition>();
		this.classes.put(backend, definitions);
		// scan fields of this class for annotations
		for(Field field: backend.getDeclaredFields()) {
			// obtain annotations for a field
			final FormFields manyFieldDefs = field.getAnnotation(FormFields.class);
			final FormField[] fieldDefs;
			if(manyFieldDefs == null) {
				FormField def = field.getAnnotation(FormField.class);
				if(def != null) fieldDefs = new FormField[] {def};
				else fieldDefs = new FormField[0];
			}
			else fieldDefs = manyFieldDefs.fields();
			
			// go through each for a matching annotation
			for(FormField fieldAnnotation: fieldDefs)
				// only if matching
				if(FormField.DEFAULT_FORM_ID.equals(fieldAnnotation.form()) || formId.equals(fieldAnnotation.form()))
					definitions.add(new AnnotationFieldDefinition(fieldAnnotation, field.getName(), field.getType()));
		}
		this.builder.addFieldDefinition(definitions.toArray(new FieldDefinition[0]));
	}
	
	public void removeClass(Class<?> backend) {
		if(this.classes.containsKey(backend))
			this.builder.removeFieldDefinition(this.classes.get(backend).toArray(new FieldDefinition[]{}));
			for(FieldDefinition definition: this.classes.get(backend))
				this.builder.removeFieldDefinition(definition);
	}
	
}
