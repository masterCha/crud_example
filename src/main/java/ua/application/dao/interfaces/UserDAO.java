package ua.application.dao.interfaces;

import java.util.List;

import ua.application.users.Users;

public interface UserDAO {
	// Find user by login
	Users find(String login);
	// Insert new user to database
	void insert(Users user);
	// Delete user from database
	void delete(int id);
	// Rewrite user data in database
	void rewrite(int id, Users updatedUser);
	// Get list of users by name
	List<Users> getUsersByName(String name);
	// Get list of all users
	List<Users> getAllUsers();

}
