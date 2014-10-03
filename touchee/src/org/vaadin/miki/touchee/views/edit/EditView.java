package org.vaadin.miki.touchee.views.edit;

import java.util.ArrayList;
import java.util.Collection;
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
import com.vaadin.ui.Notification;

/**
 * A view for editing an existing object. Any action initiated by this view will have the current state of the data (<tt>Map<String, Object></tt>) as a target.
 *
 * @author miki
 *
 */
public class EditView extends ToucheeView implements Item.Editor {

  private static final long serialVersionUID = 20140923;

  private FormBuilder formBuilder;

  private FieldGroup fieldGroup;

  private final ArrayList<DataChangeListener> beforeChangeListeners = new ArrayList<DataChangeListener>();

  private final ArrayList<DataChangeListener> afterChangeListeners = new ArrayList<DataChangeListener>();

  private final ArrayList<DataChangeListener> discardChangeListener = new ArrayList<DataChangeListener>();

  // translates commit events from the field group into component-independent
  // data change listeners
  private final FieldGroup.CommitHandler commitHandler = new FieldGroup.CommitHandler() {

    private static final long serialVersionUID = 20140925;

    @Override
    public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
      EditView.this.broadcastEvent(commitEvent.getFieldBinder(), EditView.this.beforeChangeListeners, false);
    }

    @Override
    public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
      EditView.this.broadcastEvent(commitEvent.getFieldBinder(), EditView.this.afterChangeListeners, true);
    }
  };

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
    if(this.fieldGroup != null)
      this.fieldGroup.removeCommitHandler(this.commitHandler);

    // handle commit events
    this.fieldGroup = this.getFormBuilder().buildFieldGroup();
    this.fieldGroup.addCommitHandler(this.commitHandler);

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
   * Broadcasts an event to given listeners.
   *
   * @param fieldGroup
   *          Field group to take data from.
   * @param listeners
   *          Listeners to broadcast the event to.
   * @param committed
   *          Whether or not the data is committed to the data source.
   * @throws FieldGroup.CommitException
   *           when the data is rejected by the listeners.
   */
  protected void broadcastEvent(FieldGroup fieldGroup, Collection<DataChangeListener> listeners, boolean committed) throws FieldGroup.CommitException {
    DataChangeEvent event = new DataChangeEvent(this.getMapOfData(fieldGroup), fieldGroup.getItemDataSource(), committed);
    for(DataChangeListener listener: listeners)
      listener.dataChanged(event);
    if(event.isRejected()) {
      StringBuilder messageBuilder = new StringBuilder();
      // build message
      for(String message: event.getRejectMessages())
        messageBuilder.append(message).append(" ");
      throw new FieldGroup.CommitException(messageBuilder.toString());
    }
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
        Notification.show("Error", e.getMessage(), Notification.Type.WARNING_MESSAGE);
      }
  }

  /**
   * Discards the data in the field group and synchronises them with the item.
   */
  public void discard() {
    if(this.fieldGroup != null) {
      this.fieldGroup.discard();
      // field group does not do anything when the form is discarded, but
      // this view might want to have some listeners (e.g. to close it)
      try {
        this.broadcastEvent(this.fieldGroup, this.discardChangeListener, false);
      }
      catch(FieldGroup.CommitException e) {
        Notification.show("Error", e.getMessage(), Notification.Type.WARNING_MESSAGE);
      }
    }
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

  public FormBuilder getFormBuilder() {
    return this.formBuilder;
  }

  public void setFormBuilder(FormBuilder formBuilder) {
    this.formBuilder = formBuilder;
    this.rebuildForm();
  }

  public void addBeforeChangeListener(DataChangeListener listener) {
    this.beforeChangeListeners.add(listener);
  }

  public void addAfterChangeListener(DataChangeListener listener) {
    this.afterChangeListeners.add(listener);
  }

  public void removeBeforeChangeListener(DataChangeListener listener) {
    this.beforeChangeListeners.remove(listener);
  }

  public void removeAfterChangeListener(DataChangeListener listener) {
    this.afterChangeListeners.remove(listener);
  }

  public void addDiscardChangeListener(DataChangeListener listener) {
    this.discardChangeListener.add(listener);
  }

  public void removeDiscardChangeListener(DataChangeListener listener) {
    this.discardChangeListener.remove(listener);
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
