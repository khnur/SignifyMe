package com.company.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String URL = "jdbc:postgresql://localhost:5432/e-shop";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "zxcv123";
    
    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
    	Class.forName("org.postgresql.Driver"); // To find a class
    	if (connection == null || connection.isClosed()) {
    		connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Singleton design
    	}
    	return connection;
    }
}
