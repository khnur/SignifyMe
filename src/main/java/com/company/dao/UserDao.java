package com.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.company.models.User;

public class UserDao extends DAO {

	private UserDao(Connection connection) {
		super(connection, User.class.getSimpleName().toLowerCase() + 's');
	}

	public static UserDao getInstance(Connection connection) {
		return new UserDao(connection);
	}

	@Override
	public boolean createTable() throws SQLException {
		if (!super.tableExists()) {
			return false;
		}

		PreparedStatement statement = connection.prepareStatement(
				"CREATE TABLE " + table + "(" + "id SERIAL PRIMARY KEY," + User.FIRSTNAME + " varchar(30) NOT NULL,"
						+ User.LASTNAME + " varchar(30) NOT NULL," + User.PHONE + " varchar(30) NOT NULL,"
						+ User.ADDRESS + " varchar(50) NOT NULL," + User.PASSWORD + " varchar(150) NOT NULL" + ");");

		return statement.execute();
	}

	public void addUser(User user) throws SQLException {
//		createTable();

		PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO " + table + "(" + User.FIRSTNAME + ", " + User.LASTNAME + ", "
						+ User.PHONE + ", " + User.PASSWORD + ", " + User.ADDRESS + ") " + "VALUES (?, ?, ?, ?, ?)");

		preparedStatement.setString(1, user.getFirstName());
		preparedStatement.setString(2, user.getLastName());
		preparedStatement.setString(3, user.getPhone());
		preparedStatement.setString(4, user.getPassword());
		preparedStatement.setString(5, user.getAddress());

		preparedStatement.executeUpdate();
	}

	public List<User> getAllUsers() throws SQLException {
		ResultSet resultSet = super.getAll();
		List<User> users = new ArrayList<>();
		if (!resultSet.next())
			throw new RuntimeException("There is no user");

		do {
			users.add(User.resultSetToUser(resultSet));
		} while (resultSet.next());

		return users;
	}

	public User getUserById(int id) throws SQLException {
		ResultSet resultSet = super.getById(id);
		if (!resultSet.next())
			throw new RuntimeException("User does NOT exist");
		return User.resultSetToUser(resultSet);
	}

	public User auth(String phone, String password) throws SQLException {
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM " + table + " WHERE " + User.PHONE + " = ? AND " + User.PASSWORD + " = ?");
        preparedStatement.setString(1, phone);
        preparedStatement.setString(2, password);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			return User.resultSetToUser(resultSet);
		}
		return null;
	}

	public void updateUser(User user) throws SQLException {
		int id = user.getId();
		if (user.getFirstName() != null && user.getFirstName().length() > 0) {
			super.updateById(id, User.FIRSTNAME, user.getFirstName());
		}
		if (user.getLastName() != null && user.getLastName().length() > 0) {
			super.updateById(id, User.LASTNAME, user.getLastName());
		}
		if (user.getPhone() != null && user.getPhone().length() > 0) {
			super.updateById(id, User.PHONE, user.getPhone());
		}
		if (user.getAddress() != null && user.getAddress().length() > 0) {
			super.updateById(id, User.ADDRESS, user.getAddress());
		}
		if (user.getPassword() != null && user.getPassword().length() > 0) {
			super.updateById(id, User.PASSWORD, user.getPassword());
		}
	}

}