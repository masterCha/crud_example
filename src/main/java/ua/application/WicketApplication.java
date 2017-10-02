package ua.application;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;

import ua.application.pages.crud_manager.CrudManagerPage;
import ua.application.pages.index.AuthenticationPage;
import ua.application.session.UserSession;

@Component
public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends WebPage> getHomePage() {
		return AuthenticationPage.class;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new UserSession(request);
	}

	@Override
	public void init() {

		super.init();

		getComponentInstantiationListeners().add(new SpringComponentInjector(this));

		mountPage("/index", AuthenticationPage.class);
		mountPage("/crud-manager", CrudManagerPage.class);
	}
}
