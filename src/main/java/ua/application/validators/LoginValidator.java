package ua.application.validators;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

import ua.application.dao.impl.PostgresDAO;

public class LoginValidator implements IValidator<String> {

	private static final long serialVersionUID = -7872170152752488276L;

	@SpringBean
	private PostgresDAO postgresDAO;

	public LoginValidator(String defaultModelObject) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(IValidatable<String> validatable) {

	}

}
