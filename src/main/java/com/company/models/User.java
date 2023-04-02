package com.company.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";
	public static final String PASSWORD = "password";

	private int id;
	private String firstName;
	private String lastName;
	private String phone;
	private String password;
	private String address;
	
    public User() {}

    public User(String firstname, String lastname, String phone, String address, String password) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

	public static User resultSetToUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setFirstName(resultSet.getString(User.FIRSTNAME));
		user.setLastName(resultSet.getString(User.LASTNAME));
		user.setPhone(resultSet.getString(User.PHONE));
		user.setPassword(resultSet.getString(User.PASSWORD));
		user.setAddress(resultSet.getString(User.ADDRESS));
		return user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
