package ua.application.forms;

import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import ua.application.pages.crud_manager.CrudManagerPage;
import ua.application.users.Users;

public class SearchForm extends Form {

	private static final long serialVersionUID = -3777780775485362335L;

	String userName;

	List<Users> searchResult;

	public SearchForm(String id) {
		super(id);
		final TextField<String> nameField = new TextField<String>("username", new PropertyModel<String>(this, "userName"));

		add(nameField);
	}

	@Override
	public final void onSubmit() {
		// Transferring searched name to page using page parameters
		PageParameters parameters = new PageParameters();
		if (userName != null) {
			parameters.add("searchResult", userName);
		}
		setResponsePage(CrudManagerPage.class, parameters);

	}

}
