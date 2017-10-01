package ua.application.pages.user_manage;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import ua.application.forms.UserManageForm;

public class UserManagePage extends WebPage {

	private static final long serialVersionUID = -1491789645267539639L;

	public UserManagePage(WebPage webPage, ModalWindow modalwindow, String purpose) {
		// Setting the title of form according to purpose
		add(new Label("title", purpose.substring(0, 1).toUpperCase() + purpose.substring(1)));
		
		// Creating form according to purpose
		if (purpose.equals("edit"))
			add(new UserManageForm("userManageForm", webPage, modalwindow, "edit"));
		if (purpose.equals("add"))
			add(new UserManageForm("userManageForm", webPage, modalwindow, "add"));
		if (purpose.equals("registration"))
			add(new UserManageForm("userManageForm", webPage, modalwindow, "registration"));
	}

}
