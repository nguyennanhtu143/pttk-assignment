package com.ptit.pttk.assignment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	private static final String URL = "jdbc:postgresql://localhost:5432/supermarket-management";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";
	protected Connection connection;

	public DAO() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
