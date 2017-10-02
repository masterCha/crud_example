package ua.application.forms;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import ua.application.dao.impl.PostgresDAO;
import ua.application.pages.crud_manager.CrudManagerPage;
import ua.application.session.UserSession;
import ua.application.users.Users;

public class UserManageForm extends Form {

	private static final long serialVersionUID = -4785626492763637890L;

	// Injecting my implementation of PostgreSQL DAO
	@SpringBean
	private PostgresDAO postgresDAO;

	Users user;

	public UserManageForm(String id, WebPage webPage, ModalWindow modalwindow, String purpose) {
		super(id);

		user = new Users();
		// Creating input fields and setting validators
		final TextField<String> loginField = new TextField<String>("login", new PropertyModel<String>(user, "login"));
		loginField.add(new StringValidator(3, 10));

		final PasswordTextField passwordField = new PasswordTextField("password", new PropertyModel<String>(user, "password"));
		passwordField.add(new StringValidator(3, 15));

		final TextField<String> nameField = new TextField<String>("name", new PropertyModel<String>(user, "name"));
		nameField.add(new StringValidator(3, 60));

		final TextField<Integer> ageField = new TextField<Integer>("age", new PropertyModel<Integer>(user, "age"));
		// age.add(new RangeValidator<Integer>(1, 100));

		// Creating button for submit
		AjaxButton submit = new AjaxButton("submit") { // the model here is used to retrieve the button's text afterward

			private static final long serialVersionUID = 5045868537734735306L;

			@Override
			public void onSubmit(AjaxRequestTarget target) {

				if (purpose.equals("edit")) {
					int oldUserId = UserSession.getInstance().getUserToEditId();
					postgresDAO.rewrite(oldUserId, user);
				}

				if (purpose.equals("add")) {
					if (!postgresDAO.isUserExist(user.getLogin()))
						postgresDAO.insert(user);
					else
						return;
				}

				if (purpose.equals("registration")) {
					if (!postgresDAO.isUserExist(user.getLogin())) {
						postgresDAO.insert(user);
						UserSession.getInstance().setUser(user);
						UserSession.getInstance().setSignIn(true);
					} else
						return;
				}

				modalwindow.close(target);
			}
		};

		// Creating button for cancel operation
		AjaxButton cancelSubmit = new AjaxButton("cancelSubmit") {

			private static final long serialVersionUID = -4020302408974397880L;

			@Override
			public void onSubmit(AjaxRequestTarget target) {

				modalwindow.close(target);
			}
		};
		// Bypassing form update with cancelSubmit button
		cancelSubmit.setDefaultFormProcessing(false);

		// Setting default window close operation
		modalwindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			private static final long serialVersionUID = 8540350710143732462L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				if (UserSession.getInstance().isSignedIn())
					webPage.getPage().setResponsePage(CrudManagerPage.class);
				else
					webPage.getPage().setResponsePage(webPage.getClass());

			}
		});

		// Adding components to form
		add(loginField);
		add(passwordField);
		add(nameField);
		add(ageField);
		add(submit);
		add(cancelSubmit);

	}

}
