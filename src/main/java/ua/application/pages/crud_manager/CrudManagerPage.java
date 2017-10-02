package ua.application.pages.crud_manager;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ua.application.dao.impl.PostgresDAO;
import ua.application.forms.SearchForm;
import ua.application.pages.index.AuthenticationPage;
import ua.application.pages.user_manage.UserManagePage;
import ua.application.session.UserSession;
import ua.application.users.Users;

public class CrudManagerPage extends WebPage {
	private static final long serialVersionUID = 3L;

	// Injecting my implementation of PostgreSQL DAO
	@SpringBean
	PostgresDAO postgresDAO;

	final ModalWindow msgDialog;
	List<Users> users;

	public CrudManagerPage(final PageParameters parameters) {

		super(parameters);

		// Checking for authentication
		if (!UserSession.getInstance().isSignedIn())
			setResponsePage(AuthenticationPage.class);

		// Creating elements
		if (UserSession.getInstance().isSignedIn())
			add(new Label("currentUser", "Welcome, " + UserSession.getInstance().getUser().getName()));
		add(new Link<Void>("exit") {

			private static final long serialVersionUID = -1012829532089574231L;

			@Override
			public void onClick() {
				UserSession.getInstance().signOut();
			}
		});
		// Search form
		add(new SearchForm("searchForm"));

		// Modal window for user management
		add(msgDialog = new ModalWindow("msgDialog"));

		// Add button
		add(new AjaxLink<Void>("addUser") {

			private static final long serialVersionUID = -4020302408974397880L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				addMsgDialog("add");
				msgDialog.show(target);

			}
		});

		//
		// List of users

		// Checking the search result
		if (!parameters.isEmpty())
			users = postgresDAO.getUsersByName(parameters.get("searchResult").toString());
		else
			users = postgresDAO.getAllUsers();
		ListDataProvider<Users> listDataProvider = new ListDataProvider<Users>(users);

		DataView<Users> dataView = new DataView<Users>("rows", listDataProvider) {

			private static final long serialVersionUID = 6191971221859355690L;

			@Override
			protected void populateItem(Item<Users> item) {

				// Displaying users data
				Users user = item.getModelObject();
				RepeatingView repeatingView = new RepeatingView("dataRow");
				repeatingView.add(new Label(repeatingView.newChildId(), user.getId()));
				repeatingView.add(new Label(repeatingView.newChildId(), user.getLogin()));
				repeatingView.add(new Label(repeatingView.newChildId(), user.getPassword()));
				repeatingView.add(new Label(repeatingView.newChildId(), user.getName()));
				repeatingView.add(new Label(repeatingView.newChildId(), user.getAge()));

				// Displaying delete links
				RepeatingView deleteLinksView = new RepeatingView("deleteLink");
				deleteLinksView.add(new Link<Void>(deleteLinksView.newChildId()) {

					private static final long serialVersionUID = -852472863988962838L;

					@Override
					public void onClick() {
						postgresDAO.delete(user.getId());
						setResponsePage(CrudManagerPage.class);
					}
				});

				// Displaying edit links
				RepeatingView editLinksView = new RepeatingView("editLink");
				editLinksView.add(new AjaxLink<Void>(editLinksView.newChildId()) {

					private static final long serialVersionUID = 78983174204705693L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						UserSession.getInstance().setUserToEditId(user.getId());
						addMsgDialog("edit");
						msgDialog.show(target);
					}
				});
				item.add(repeatingView, deleteLinksView, editLinksView);
			}

		};

		// Set 10 items per page
		dataView.setItemsPerPage(10);

		add(dataView);

		add(new PagingNavigator("pagingNavigator", dataView));
	}

	public void addMsgDialog(String purpose) {
		msgDialog.setPageCreator(new ModalWindow.PageCreator() {

			private static final long serialVersionUID = -7499737036197758387L;

			public Page createPage() {
				return new UserManagePage(CrudManagerPage.this, msgDialog, purpose);
			}
		});
	}
}
