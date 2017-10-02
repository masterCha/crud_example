package ua.application.users;

import java.io.Serializable;

public class Users implements Serializable {

	private static final long serialVersionUID = 8400169565888678004L;

	private int id;
	private String login;
	private String password;
	private String name;
	private int age;

	public Users() {
		super();
	}

	public Users(int id, String login, String password, String name, int age) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.age = age;
	}

	/* GETTERS AND SETTERS */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
