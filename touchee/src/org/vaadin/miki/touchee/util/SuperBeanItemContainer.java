package org.vaadin.miki.touchee.util;

import com.vaadin.data.util.BeanItemContainer;

/**
 * Bean item container that supports adding items.
 * 
 * @author miki
 * @param <BEANTYPE>
 *          Type of bean.
 *
 */
public class SuperBeanItemContainer<BEANTYPE> extends BeanItemContainer<BEANTYPE> {

  private static final long serialVersionUID = 20141003;

  /**
   * Constructs the container with a given item type.
   * 
   * @param type
   *          Type of bean.
   * @throws IllegalArgumentException
   *           When something goes wrong.
   */
  public SuperBeanItemContainer(Class<? super BEANTYPE> type) throws IllegalArgumentException {
    super(type);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object addItem() throws UnsupportedOperationException {
    try {
      Object bean = this.getBeanType().newInstance();
      this.addBean((BEANTYPE)bean);
      return bean;
    }
    catch(InstantiationException | IllegalAccessException e) {
      throw new UnsupportedOperationException(e);
    }
  }

}
