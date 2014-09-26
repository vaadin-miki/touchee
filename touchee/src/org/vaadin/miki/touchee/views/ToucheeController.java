package org.vaadin.miki.touchee.views;

import org.vaadin.miki.form.bean.BeanFormBuilder;
import org.vaadin.miki.touchee.data.User;
import org.vaadin.miki.touchee.views.edit.DataChangeEvent;
import org.vaadin.miki.touchee.views.edit.DataChangeListener;
import org.vaadin.miki.touchee.views.edit.EditView;
import org.vaadin.miki.touchee.views.list.ItemStateEvent;
import org.vaadin.miki.touchee.views.list.ItemStateListener;
import org.vaadin.miki.touchee.views.list.ListView;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

/**
 * Controls mobile navigation.
 * 
 * @author miki
 *
 */
public class ToucheeController {

	private final NavigationManager manager = new NavigationManager();

	private final Container usersContainer = this.getUsersContainer();
	
	public ToucheeController(UI ui) {
		super();
		ui.setContent(this.manager);
	}

	private Container getUsersContainer() {
		BeanItemContainer<User> container = new BeanItemContainer<User>(User.class);
		String[] fakeUserData = new String[]{"foo@vaadin.com", "miki@vaadin.com", "vaadin@example.org"};
		for(String string: fakeUserData) {
			User user = new User();
			String[] split = string.split("@");
			user.setUsername(split[0]);
			user.setEmail(split[0]+"@"+split[1]);
			container.addBean(user);
		}
		return container;
	}
	
	// TODO this should probably take classes as parameters to display them in
	// the list view (to be done)
	public void configure() {
	}

	private Component createListView() {
		ListView view = new ListView();
		view.setCaption("Listing users");
		
		view.addItemSelectListener(new ItemStateListener() {
			
			@Override
			public void itemStateChanged(ItemStateEvent event) {
				manager.navigateTo(createEditView(((BeanItem<?>)event.getItem()).getBean()));
			}
		});
		
		view.setContainerDataSource(this.usersContainer);
		
		return view;
	}
	
	private Component createEditView(Object object) {
		EditView view = new EditView(new BeanFormBuilder<Object>(object, "edit"));
		
		view.addDiscardChangeListener(new DataChangeListener() {
			
			@Override
			public void dataChanged(DataChangeEvent event) {
				manager.navigateBack();
			}
		});
		
		return view;
	}
	
	private Component createLoginView() {
		EditView login = new EditView(new BeanFormBuilder<User>(new User(), "login"));
		login.setCaption("Log in");
		login.setControlCaptions("Log in", "Register");
		login.addAfterChangeListener(new DataChangeListener() {

			@Override
			public void dataChanged(DataChangeEvent event) {
				@SuppressWarnings("unchecked")
				User data = ((BeanItem<User>) event.getItem()).getBean();
				if (!"test".equals(data.getUsername()) || !"test".equals(data.getPassword()))
					event.reject("Please login as test/test.");
				else {
					VaadinSession.getCurrent().setAttribute(User.class, data);
					manager.navigateTo(createListView());
				}
			}
		});
		return login;
	}

	public void navigateToStart() {
		if (VaadinSession.getCurrent().getAttribute(User.class) == null)
			this.manager.navigateTo(this.createLoginView());
		else
			this.manager.navigateTo(this.createListView());

	}

}
