package com.example.tms.dao.mysql.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPool {
	private static final String DB_NAME_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/tms_db?useSSL=false";
	private static final String DB_LOGIN = "user";
	private static final String DB_PASSWORD = "user";

	static {
		try {
			Class.forName(DB_NAME_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private ConnectionPool() {
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static class PoolHolder {
		private static final ConnectionPool POOL = new ConnectionPool();
	}

	public static ConnectionPool getPool() {
		return PoolHolder.POOL;
	}

	public void closeDbResources(Connection connection) {
		closeDbResources(connection, null);
	}

	public void closeDbResources(Connection connection, PreparedStatement statement) {
		closeDbResources(connection, statement, null);
	}

	public void closeDbResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
		closeResultSet(resultSet);
		closeStatement(statement);
		closeConnection(connection);
	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ignored) {
			}
		}
	}

	private void closeStatement(PreparedStatement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ignored) {
			}
		}
	}

	private void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException ignored) {
			}
		}
	}
}
