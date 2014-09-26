package org.vaadin.miki.touchee;

import javax.servlet.annotation.WebServlet;

import org.vaadin.miki.touchee.views.ToucheeController;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("touchcrud")
public class TouchKitCrudUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = TouchKitCrudUI.class, widgetset = "org.vaadin.miki.touchee.widgetset.ToucheeWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		ToucheeController controller = new ToucheeController(this);
		controller.configure();
		controller.navigateToStart();
		
	}

}