package org.vaadin.miki.form.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.vaadin.miki.form.FieldDefinition;
import org.vaadin.miki.form.FormBuilder;
import org.vaadin.miki.form.FormBuilderFieldFactory;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Field;

/**
 * Form builder that generates a {@link FieldGroup} with fields of corresponding
 * type, based on a given {@link FieldDefinition}s and
 * {@link FormBuilderFieldFactory}. Fields can optionally be bound to an item
 * data source.
 * 
 * @author miki
 *
 */
public class FieldDefinitionFormBuilder implements FormBuilder {

	private static final long serialVersionUID = 20140924;

	private Item dataSource;

	private final ArrayList<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>();

	private FormBuilderFieldFactory fieldFactory = new DefaultFormBuilderFieldFactory();

	public FieldDefinitionFormBuilder() {
		super();
	}
	
	public FieldDefinitionFormBuilder(Item item, FieldDefinition... definitions) {
		this();
		this.setFieldDefinitions(Arrays.asList(definitions));
		this.setItemDataSource(item);
	}
	
	@Override
	public FieldGroup buildFieldGroup() {
		FieldGroup result = new FieldGroup(this.getItemDataSource());
		for (FieldDefinition definition : this.getFieldDefinitions()) {
			Field<?> field = this.getFieldFactory().buildField(definition.getValueType(), definition.getIdentifier(), definition.getFieldCaption(), definition.getAdditionalInformation().toArray());
			result.bind(field, definition.getPropertyId());
		}
		return result;
	}

	@Override
	public void setItemDataSource(Item newDataSource) {
		this.dataSource = newDataSource;
	}

	@Override
	public Item getItemDataSource() {
		return this.dataSource;
	}

	@Override
	public FormBuilderFieldFactory getFieldFactory() {
		return fieldFactory;
	}

	@Override
	public void setFieldFactory(FormBuilderFieldFactory fieldFactory) {
		this.fieldFactory = fieldFactory;
	}

	public List<FieldDefinition> getFieldDefinitions() {
		return Collections.unmodifiableList(this.fieldDefinitions);
	}

	public void setFieldDefinitions(Collection<FieldDefinition> fieldDefinitions) {
		this.fieldDefinitions.clear();
		if (fieldDefinitions != null)
			this.fieldDefinitions.addAll(fieldDefinitions);
	}

	public void addFieldDefinition(FieldDefinition... definitions) {
		for (FieldDefinition definition : definitions)
			this.fieldDefinitions.add(definition);
	}

	public void removeFieldDefinition(FieldDefinition... definitions) {
		for (FieldDefinition definition : definitions)
			this.fieldDefinitions.remove(definition);
	}

	public void clearFieldDefinitions() {
		this.fieldDefinitions.clear();
	}

}
