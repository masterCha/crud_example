package ua.application.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import ua.application.dao.interfaces.UserDAO;
import ua.application.users.Users;

@Component("postgresDAO")
public class PostgresDAO implements UserDAO {

	/**
	 * JDBCTemplate object for accessing database
	 */

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// Find user by Login
	@Override
	public Users find(String login) {
		String sql = "SELECT id, login, password, name, age FROM users WHERE UPPER(login) LIKE :login";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("login", "%" + login.toUpperCase() + "%");

		// If the user not found return null
		try {
			return (Users) jdbcTemplate.queryForObject(sql, params, new RowMapper());
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Data not found!");
			return null;
		}
	}

	// Insert new user
	@Override
	public void insert(Users user) {
		String sql = "INSERT INTO users (login, password, name, age) VALUES (:login, :password, :name, :age)";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("login", user.getLogin());
		params.addValue("password", user.getPassword());
		params.addValue("name", user.getName());
		params.addValue("age", user.getAge());

		// Handling data access exception
		try {
			jdbcTemplate.update(sql, params);
		} catch (DataAccessException e) {
			System.out.println("Insert error. Data not found!");
		}
	}

	// Delete selected user
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM users WHERE id = :id";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);

		// Handling data access exception
		try {
			jdbcTemplate.update(sql, params);
		} catch (DataAccessException e) {
			System.out.println("Delete error. Data not found!");
		}
	}

	// Rewrite selected user
	@Override
	public void rewrite(int id, Users updatedUser) {
		String sql = "UPDATE users SET login = :login, password = :password, name = :name, age = :age WHERE id = :id";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		params.addValue("login", updatedUser.getLogin());
		params.addValue("password", updatedUser.getPassword());
		params.addValue("name", updatedUser.getName());
		params.addValue("age", updatedUser.getAge());

		// Handling data access exception
		try {
			jdbcTemplate.update(sql, params);
		} catch (DataAccessException e) {
			System.out.println("Rewrite error. Data not found!");
		}
	}

	// Get all users from database by name
	@Override
	public List<Users> getUsersByName(String name) {
		String sql = "SELECT * FROM users WHERE UPPER(name) LIKE :name";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", "%" + name.toUpperCase() + "%");

		// Handling data access exception
		try {
			// Return sorted list of users
			return sort(jdbcTemplate.query(sql, params, new RowMapper()));
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Data not found!");
			return null;
		}
	}

	// Get all users from database
	@Override
	public List<Users> getAllUsers() {
		String sql = "SELECT * FROM users";

		// If the data is not found return null
		try {
			// Return sorted list of users
			return sort(jdbcTemplate.query(sql, new RowMapper()));
		} catch (DataAccessException e) {
			System.out.println("Data not found!");
			return null;
		}
	}

	// Checking user exist or not
	public boolean isUserExist(String login) {
		if (find(login) != null)
			return true;
		else
			return false;
	}

	// Sorting search result
	public List<Users> sort(List<Users> userList) {
		// Creating comparator for user id compare
		Comparator<Users> comparator = new Comparator<Users>() {
			@Override
			public int compare(Users left, Users right) {
				return left.getId() - right.getId();
			}
		};
		// Sorting list of users
		Collections.sort(userList, comparator);

		return userList;
	}

	private static final class RowMapper implements org.springframework.jdbc.core.RowMapper<Users> {

		@Override
		public Users mapRow(ResultSet rs, int rowNum) throws SQLException {

			Users user = new Users();
			user.setId(rs.getInt("id"));
			user.setLogin(rs.getString("login"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setAge(rs.getByte("age"));

			return user;
		}
	}
}
