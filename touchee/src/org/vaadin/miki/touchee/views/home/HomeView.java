package org.vaadin.miki.touchee.views.home;

import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

public class HomeView extends NavigationView {

	private static final long serialVersionUID = 20140923L;

	private Navigator navigator = null;
	
	public HomeView() {

		HorizontalButtonGroup buttons = new HorizontalButtonGroup("Go to: ");
		
		Button edit = new Button("Edit", new Button.ClickListener() {
			
			private static final long serialVersionUID = 20140923;

			@Override
			public void buttonClick(Button.ClickEvent event) {
				navigator.navigateTo("edit");
			}
		});
		edit.setCaption("Edit!");
		buttons.setSizeFull();
		
		buttons.addComponent(edit);
		
		this.setContent(buttons);
		
		
	}

}
