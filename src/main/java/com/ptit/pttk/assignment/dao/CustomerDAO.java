package com.ptit.pttk.assignment.dao;

import com.ptit.pttk.assignment.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {
	private final DataSource dataSource = new DataSource();

	private static final String INSERT_SQL = "INSERT INTO public.\"tblCustomer\" (member_id, customer_code) VALUES (?, ?)";

	public boolean create(Integer memberId) {
		StringBuilder customerCode = new StringBuilder("KH");
		while (customerCode.length() < 6 - String.valueOf(customerCode.length()).length()) {
			customerCode.append("0");
		}

		customerCode.append(memberId);
		boolean result = false;
		Customer customer = new Customer(memberId, customerCode.toString());
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_SQL);

			dataSource.beginTransaction(connection);
			ps.setInt(1, memberId);
			ps.setString(2, customer.getCustomerCode());
			ps.executeUpdate();
			dataSource.commitTransaction(connection);
			result = true;
		} catch (SQLException e) {
			dataSource.rollbackTransaction(connection);
			e.printStackTrace();
		} finally {
			dataSource.closeResources(connection);
		}

		return result;
	}
}
