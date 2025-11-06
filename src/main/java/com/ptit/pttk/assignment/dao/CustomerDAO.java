package com.ptit.pttk.assignment.dao;

import com.ptit.pttk.assignment.model.Customer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO extends DAO {
	public CustomerDAO() {
		super();
	}

	private static final String INSERT_SQL = "INSERT INTO public.\"tblCustomer\" (tbl_member_id, customer_code) VALUES (?, ?)";

	public boolean create(Integer memberId) throws SQLException {
		StringBuilder customerCode = new StringBuilder("CUST");
		while (customerCode.length() < 6 - String.valueOf(customerCode.length()).length()) {
			customerCode.append("0");
		}

		customerCode.append(memberId);
		boolean result = false;
		Customer customer = new Customer(memberId, customerCode.toString());
		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_SQL);

			connection.setAutoCommit(false);
			ps.setInt(1, memberId);
			ps.setString(2, customer.getCustomerCode());
			ps.executeUpdate();
			connection.commit();
			result = true;
		} catch (SQLException e) {
			if (connection != null) {
				connection.rollback();
			}
			e.printStackTrace();
		}

		return result;
	}
}
