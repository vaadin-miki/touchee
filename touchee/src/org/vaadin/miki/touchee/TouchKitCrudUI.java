package org.vaadin.miki.touchee;

import javax.servlet.annotation.WebServlet;

import org.vaadin.miki.touchee.configuration.simple.SimpleContainerDefinition;
import org.vaadin.miki.touchee.data.Note;
import org.vaadin.miki.touchee.data.User;
import org.vaadin.miki.touchee.util.SuperBeanItemContainer;
import org.vaadin.miki.touchee.views.ToucheeController;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("touchcrud")
public class TouchKitCrudUI extends UI {

  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = TouchKitCrudUI.class, widgetset = "org.vaadin.miki.touchee.widgetset.ToucheeWidgetset")
  public static class Servlet extends VaadinServlet {}

  @Override
  protected void init(VaadinRequest request) {

    ToucheeController controller = new ToucheeController(this);
    controller.configure(new SimpleContainerDefinition(this.getUsersContainer(), "Users", true, true, true), new SimpleContainerDefinition(this.getNotesContainer(), "Notes", true,
        true, false));
    controller.navigateToStart();

  }

  private Container getUsersContainer() {
    BeanItemContainer<User> container = new SuperBeanItemContainer<User>(User.class);

    String[] fakeUserData = new String[]{"foo@vaadin.com", "miki@vaadin.com", "vaadin@example.org"};
    for(String string: fakeUserData) {
      User user = new User();
      String[] split = string.split("@");
      user.setUsername(split[0]);
      user.setEmail(split[0] + "@" + split[1]);
      container.addBean(user);
    }
    return container;
  }

  private Container getNotesContainer() {
    BeanItemContainer<Note> container = new SuperBeanItemContainer<Note>(Note.class);

    for(int zmp1 = 1; zmp1 <= 10; zmp1++) {
      Note note = new Note("Note no. " + zmp1, "This is the body of the note number " + zmp1 + ". All notes look very similar.");
      container.addBean(note);
    }

    return container;
  }

}
