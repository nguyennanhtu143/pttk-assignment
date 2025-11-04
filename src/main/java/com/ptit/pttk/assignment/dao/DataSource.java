package com.ptit.pttk.assignment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
	private static final String URL = "jdbc:postgresql://localhost:5432/supermarket-management";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void beginTransaction(Connection connection) throws SQLException {
		if (connection != null) {
			connection.setAutoCommit(false);
		}
	}

	public void commitTransaction(Connection connection) throws SQLException {
		if (connection != null) {
			connection.commit();
		}
	}

	public void rollbackTransaction(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				System.out.println("Error during transaction rollback: " + e.getMessage());
			}
		}
	}

	public void closeResources(Connection connection) {
		try {
			if (connection != null) connection.close();
		} catch (SQLException e) {
			System.out.println("Error closing resources: " + e.getMessage());
		}
	}
}
