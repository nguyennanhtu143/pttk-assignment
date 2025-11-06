package com.ptit.pttk.assignment.dao;

import com.ptit.pttk.assignment.model.Member;

import java.sql.*;

public class MemberDAO extends DAO {
	public MemberDAO() {
		super();
	}

	private static final String INSERT_SQL = "INSERT INTO public.\"tblMember\" (username, password, fullname, date_of_birth, gender, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
  private static final String SELECT_BY_USERNAME_SQL = "SELECT * FROM public.\"tblMember\" WHERE username = ?";

	public int register(Member member) throws SQLException {
		int id = 0;
		try {
			if (this.existsUsername(member.getUsername())) {
				return 0;
			}
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);

			connection.setAutoCommit(false);
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
				connection.commit();
			} else {
				connection.rollback();
				return 0;
			}

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return id;
	}

	private boolean existsUsername(String username) throws SQLException {
		boolean exists = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME_SQL);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}
}
