package ua.application.dao.interfaces;

import java.util.List;

import ua.application.users.Users;

public interface UserDAO {

	Users find(String login);

	void insert(Users user);

	void delete(int id);

	void rewrite(int id, Users updatedUser);

	List<Users> getUsersByName(String name);

	List<Users> getAllUsers();

}
