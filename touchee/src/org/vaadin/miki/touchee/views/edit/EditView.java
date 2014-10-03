package org.vaadin.miki.touchee.views.edit;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.vaadin.miki.form.FormBuilder;
import org.vaadin.miki.touchee.views.ToucheeView;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.Action;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;

/**
 * A view for editing an existing object. Any action initiated by this view will have the current state of the data (<tt>Map<String, Object></tt>) as a target.
 * The underlying item can be obtained through {@link #getItemDataSource()}.
 *
 * @author miki
 *
 */
public class EditView extends ToucheeView implements Item.Editor {

  private static final long serialVersionUID = 20140923;

  private FormBuilder formBuilder;

  private FieldGroup fieldGroup;

  /**
   * Constructs the view.
   */
  public EditView() {
    super();
    this.setSizeFull();
  }

  /**
   * Constructs the view and sets the given form builder.
   *
   * @param builder
   *          Form builder to use.
   */
  public EditView(FormBuilder builder) {
    this();
    this.setFormBuilder(builder);
  }

  /**
   * Repaints the form.
   */
  protected void rebuildForm() {
    this.fieldGroup = this.getFormBuilder().buildFieldGroup();

    // update contents
    FormLayout form = new FormLayout(this.fieldGroup.getFields().toArray(new Field<?>[0]));
    this.setContent(form);
  }

  /**
   * Gets a map of the data in the field group passed to it.
   *
   * @param fieldGroup
   *          Field group.
   * @return A map of value of fields, as currently in the field group.
   */
  protected Map<Object, Object> getMapOfData(FieldGroup fieldGroup) {
    LinkedHashMap<Object, Object> data = new LinkedHashMap<Object, Object>();
    for(Object propertyId: fieldGroup.getBoundPropertyIds())
      data.put(propertyId, fieldGroup.getField(propertyId).getValue());

    return Collections.unmodifiableMap(data);
  }

  /**
   * Commits the data currently in the field group.
   */
  public void commit() {
    if(this.fieldGroup != null)
      try {
        this.fieldGroup.commit();
      }
      catch(FieldGroup.CommitException e) {
        throw new RuntimeException(e);
      }
  }

  /**
   * Discards the data in the field group and synchronises them with the item.
   */
  public void discard() {
    this.fieldGroup.discard();
  }

  /**
   * Clears the data in the form.
   */
  public void clear() {
    if(this.fieldGroup != null) {
      for(Field<?> f: this.fieldGroup.getFields())
        f.setValue(null);
    }
  }

  /**
   * Returns currently used form builder.
   * 
   * @return Currently used form builder.
   */
  public FormBuilder getFormBuilder() {
    return this.formBuilder;
  }

  /**
   * Sets the new form builder and rebuilds the form.
   * 
   * @param formBuilder
   *          Form builder.
   */
  public void setFormBuilder(FormBuilder formBuilder) {
    this.formBuilder = formBuilder;
    this.rebuildForm();
  }

  @Override
  public Action[] getActions(Object target, Object sender) {
    return new Action[0];
  }

  @Override
  public void setItemDataSource(Item newDataSource) {
    if(this.fieldGroup != null)
      this.fieldGroup.setItemDataSource(newDataSource);
  }

  @Override
  public Item getItemDataSource() {
    return this.fieldGroup == null ? null : this.fieldGroup.getItemDataSource();
  }

  @Override
  public void handleAction(Action action, Object sender, Object target) {
    if(target == null)
      target = this.getMapOfData(this.fieldGroup);
    super.handleAction(action, sender, target);
  }

}
