package com.ptit.pttk.assignment.dao;

import com.ptit.pttk.assignment.model.Member;

import java.sql.*;

public class MemberDAO {
	private final DataSource dataSource = new DataSource();

	private static final String INSERT_SQL = "INSERT INTO public.\"tblMember\" (username, password, fullname, date_of_birth, gender, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
  private static final String SELECT_BY_USERNAME_SQL = "SELECT * FROM public.\"tblMember\" WHERE username = ?";

	public int register(Member member) {
		Connection connection = null;
		int id = 0;
		try {
			if (this.existsUsername(member.getUsername())) {
				return 0;
			}
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);

			dataSource.beginTransaction(connection);
			preparedStatement.setString(1, member.getUsername());
			preparedStatement.setString(2, member.getPassword());
			preparedStatement.setString(3, member.getFullname());
			preparedStatement.setDate(4, new java.sql.Date(member.getDateOfBirth().getTime()));
			preparedStatement.setInt(5, member.getGender());
			preparedStatement.setString(6, member.getPhone());
			preparedStatement.setString(7, member.getAddress());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
				dataSource.commitTransaction(connection);
			} else {
				dataSource.rollbackTransaction(connection);
				return 0;
			}
			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			dataSource.rollbackTransaction(connection);
			e.printStackTrace();
		} finally {
			dataSource.closeResources(connection);
		}

		return id;
	}

	private boolean existsUsername(String username) {
		Connection connection = null;
		boolean exists = false;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME_SQL);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeResources(connection);
		}

		return exists;
	}
}
