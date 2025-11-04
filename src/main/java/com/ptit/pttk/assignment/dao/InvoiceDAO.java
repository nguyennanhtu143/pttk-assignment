package com.ptit.pttk.assignment.dao;

import com.ptit.pttk.assignment.model.Invoice;
import com.ptit.pttk.assignment.model.InvoiceDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceDAO {
	private final DataSource dataSource = new DataSource();

	private static final String SELECT_BY_CUSTOMER_SQL =
				"SELECT id, tbl_customer_id, invoice_date, total_amount " +
							"FROM public.\"tblInvoice\" " +
							"WHERE tbl_customer_id = ? AND invoice_date BETWEEN ? AND ? " +
							"ORDER BY invoice_date DESC";

	private static final String SELECT_INVOICE_TYPE_BY_ID_SQL = "SELECT type FROM public.\"tblInvoice\" WHERE id = ?";

	private static final String SELECT_ORDER_DETAIL_BY_INVOICE_ID_SQL =
				"""
							SELECT tid.id, p.name, tid.quantity, tid.unit_price, tid.quantity * tid.unit_price as total \s
							FROM public."tblOrderDetail" tid \s
							LEFT JOIN public."tblInvoice" i ON tid.tbl_order_id = i.tbl_order_id\s
							LEFT JOIN public."tblProduct" p ON tid.tbl_product_id = p.id
							WHERE i.id = ?
							""";

	private static final String SELECT_BY_INVOICE_ID_SQL =
				"SELECT tid.id, p.name, tid.quantity, tid.unit_price, tid.quantity * tid.unit_price as total " +
							"FROM public.\"tblInvoiceDetail\" tid " +
							"LEFT JOIN public.\"tblProduct\" p ON tid.tbl_product_id = p.id " +
							"WHERE tid.tbl_invoice_id = ?";

	public List<Invoice> getCustomerInvoices(Integer customerId, Date startDate, Date endDate) throws SQLException {
		Connection connection = null;
		List<Invoice> invoices = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
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
		} finally {
			dataSource.closeResources(connection);
		}
		return invoices;
	}

	public List<InvoiceDetail> getInvoiceDetails(Integer invoiceId) {
		Connection connection = null;
		List<InvoiceDetail> invoiceDetails = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_INVOICE_TYPE_BY_ID_SQL);
			ps.setInt(1, invoiceId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getInt(1) == 1) {
					ps = connection.prepareStatement(SELECT_BY_INVOICE_ID_SQL);
				} else {
					ps = connection.prepareStatement(SELECT_ORDER_DETAIL_BY_INVOICE_ID_SQL);
				}

				ps.setInt(1, invoiceId);
				rs = ps.executeQuery();
				while (rs.next()) {
					invoiceDetails.add(new InvoiceDetail(
								rs.getInt(1),
								rs.getString(2),
								rs.getInt(3),
								rs.getDouble(4),
								rs.getDouble(5)
					));
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dataSource.closeResources(connection);
		}

		return invoiceDetails;
	}
}



