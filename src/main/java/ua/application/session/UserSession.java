/**
 * 
 */
package ua.application.session;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import ua.application.users.Users;

public class UserSession extends WebSession {

	private static final long serialVersionUID = 849969037186374393L;
	// authenticated user
	Users user;
	// list of all users
	List<Users> allUsers;
	boolean signIn;

	// Specifying user for editing
	int userToEditId;

	public UserSession(Request request) {
		super(request);

	}
	// Getting current session
	public static UserSession getInstance() {
		return (UserSession) Session.get();
	}
	// Sign in
	public boolean signIn(String login, String password) {
		user = findUser(login);
		if (user != null && (user.getPassword().trim().toUpperCase()).equals(password.toUpperCase())) {
			signIn = true;
			return true;
		}
		return false;
	}
	// Sign out
	public void signOut() {
		signIn = false;
		this.invalidate();

	}

	// Find user by login from list of all users
	public Users findUser(String login) {
		for (Users user : allUsers) {
			if ((user.getLogin().trim().toUpperCase()).equals(login.toUpperCase()))
				return user;
		}
		return null;
	}

	// Getters and setters
	public int getUserToEditId() {
		return userToEditId;
	}

	public void setUserToEditId(int userToEdit) {
		this.userToEditId = userToEdit;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public void setAllUsers(List<Users> allUsers) {
		this.allUsers = allUsers;
	}

	public boolean isSignedIn() {
		if (signIn == true)
			return true;
		return false;
	}

	public void setSignIn(boolean signIn) {
		this.signIn = signIn;
	}

}
