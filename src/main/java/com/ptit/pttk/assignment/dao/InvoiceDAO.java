package com.ptit.pttk.assignment.dao;

import com.ptit.pttk.assignment.model.Invoice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceDAO extends DAO {
	public InvoiceDAO() {
		super();
	}

	private static final String SELECT_BY_CUSTOMER_SQL =
				"SELECT id, tbl_customer_id, invoice_date, total_amount " +
							"FROM public.\"tblInvoice\" " +
							"WHERE tbl_customer_id = ? AND invoice_date BETWEEN ? AND ? " +
							"ORDER BY invoice_date DESC";

	public List<Invoice> getCustomerInvoices(Integer customerId, Date startDate, Date endDate) throws SQLException {
		List<Invoice> invoices = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_BY_CUSTOMER_SQL);
			ps.setInt(1, customerId);
			ps.setDate(2, new java.sql.Date(startDate.getTime()));
			ps.setDate(3, new java.sql.Date(endDate.getTime()));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				invoices.add(new Invoice(
							rs.getInt(1),
							rs.getInt(2),
							rs.getDate(3),
							rs.getDouble(4)
				));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return invoices;
	}
}




