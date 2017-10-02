package ua.application.forms;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ua.application.dao.impl.PostgresDAO;
import ua.application.pages.crud_manager.CrudManagerPage;
import ua.application.session.UserSession;

public class LoginForm extends Form {

	private static final long serialVersionUID = -2777204122537772509L;

	// Injecting my implementation of PostgreSQL DAO
	@SpringBean
	private PostgresDAO postgresDAO;

	String login;
	String password;

	public LoginForm(String id) {
		super(id);

		final TextField<String> loginField = new TextField<String>("login", new PropertyModel<String>(this, "login"));

		final PasswordTextField passwordField = new PasswordTextField("password", new PropertyModel<String>(this, "password"));

		add(loginField);
		add(passwordField);
	}

	public final void onSubmit() {

		UserSession.getInstance().setAllUsers(postgresDAO.getAllUsers());
		if (UserSession.getInstance().signIn(login, password))
			setResponsePage(CrudManagerPage.class);
	}

}
