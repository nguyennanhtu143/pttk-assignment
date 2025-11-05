package com.ptit.pttk.assignment.dao;

import com.ptit.pttk.assignment.model.InvoiceDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO extends DAO {
	public InvoiceDetailDAO() {
		super();
	}

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

	public List<InvoiceDetail> getInvoiceDetails(Integer invoiceId) throws SQLException {
		List<InvoiceDetail> invoiceDetails = new ArrayList<>();
		try {
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
		}

		return invoiceDetails;
	}
}
