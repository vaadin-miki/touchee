package org.vaadin.miki.form.bean;

import org.vaadin.miki.form.annotations.AnnotationFormBuilder;
import org.vaadin.miki.form.annotations.FormField;

import com.vaadin.data.util.BeanItem;

/**
 * An extension of {@link AnnotationFormBuilder} that supports beans based on
 * {@link BeanItem}.
 * 
 * @author miki
 *
 */
public class BeanFormBuilder<TYPE> extends AnnotationFormBuilder {

	private static final long serialVersionUID = 20140924;

	public BeanFormBuilder(TYPE object) {
		this(object, FormField.DEFAULT_FORM_ID);
	}

	public BeanFormBuilder(TYPE object, String formId) {
		super();
		this.addClass(object.getClass(), formId);
		this.setItemDataSource(object);
	}

	public void setItemDataSource(TYPE object) {
		this.setItemDataSource(new BeanItem<TYPE>(object));
	}

	/**
	 * Returns the current item data source as a bean item.
	 * 
	 * @return Current item data source as a bean item. Will return <b>null</b>
	 *         if the source is not a {@link BeanItem}. May cause weird
	 *         behaviour if the bean item is of different type than the generic
	 *         one.
	 */
	@SuppressWarnings("unchecked")
	public BeanItem<TYPE> getBeanItemDataSource() {
		if (this.getItemDataSource() instanceof BeanItem)
			return (BeanItem<TYPE>) this.getItemDataSource();
		else
			return null;
	}

}
