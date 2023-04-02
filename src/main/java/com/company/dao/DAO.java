package com.company.dao;

import java.sql.*;

public abstract class DAO {
	protected final String table;
	protected final Connection connection;

	public abstract boolean createTable() throws SQLException;
	protected boolean tableExists() throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT EXISTS ("
				+ "SELECT 1 FROM information_schema.tables "
				+ "WHERE  table_schema = 'public' "
				+ "AND    table_name   = '" + table + "'"
				+ ");");
		return ps.execute();
	}

	protected DAO(Connection connection, String table) {
		this.table = table;
		this.connection = connection;
	}

	protected ResultSet getAll() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table);
		return statement.executeQuery();
	}

	protected ResultSet getById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
		preparedStatement.setInt(1, id);
		return preparedStatement.executeQuery();
	}

	public void updateById(int id, String col, String val) throws SQLException {
		if (!existsById(id))
			throw new RuntimeException("There is nothing with such id");

		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE " + table + " SET " + col + " = ? WHERE id = ?");
		preparedStatement.setString(1, val);
		preparedStatement.setInt(2, id);

		preparedStatement.executeUpdate();
	}

	public boolean existsById(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM " + table + " WHERE id = ?");
		preparedStatement.setInt(1, id);
		return preparedStatement.executeQuery().next();
	}
	
	public void delete(int id) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + table + " WHERE id = ?");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
	}
}


