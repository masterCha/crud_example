package ua.application.pages.index;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ua.application.dao.impl.PostgresDAO;
import ua.application.forms.LoginForm;
import ua.application.pages.user_manage.UserManagePage;

public class AuthenticationPage extends WebPage {
	private static final long serialVersionUID = 1L;

	// Injecting implementation of PostgreSQL DAO
	@SpringBean
	PostgresDAO postgresDAO;

	final ModalWindow msgDialog;

	public AuthenticationPage(final PageParameters parameters) {
		super(parameters);
		
		add(new LoginForm("loginForm"));
		// Creating modal window for user regestration
		add(msgDialog = new ModalWindow("msgDialog"));

		msgDialog.setPageCreator(new ModalWindow.PageCreator() {

			private static final long serialVersionUID = -2761676118492359446L;

			public Page createPage() {
				return new UserManagePage(AuthenticationPage.this, msgDialog, "registration");
			}
		});
		
		// Creating link for user regestration
		add(new AjaxLink<Void>("newUserReg") {

			private static final long serialVersionUID = -1035900408251123913L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				msgDialog.show(target);
			}
		});
	}
}
