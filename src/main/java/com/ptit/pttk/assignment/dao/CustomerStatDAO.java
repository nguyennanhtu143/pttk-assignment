package com.ptit.pttk.assignment.dao;

import com.ptit.pttk.assignment.model.CustomerStat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerStatDAO extends DAO {
	public CustomerStatDAO() {
		super();
	}

	private static final String SELECT_SQL =
				"SELECT m.id, m.fullname, c.customer_code, " +
							"COUNT(i.id) AS invoice_count, " +
							"COALESCE(SUM(i.total_amount), 0) AS total_spent " +
							"FROM public.\"tblCustomer\" c " +
							"JOIN public.\"tblMember\" m ON m.id = c.tbl_member_id " +
							"LEFT JOIN public.\"tblInvoice\" i ON i.tbl_customer_id = c.tbl_member_id " +  // LEFT JOIN để lấy cả khách chưa mua
							"WHERE i.invoice_date BETWEEN ? AND ? " +
							"   OR i.invoice_date IS NULL " +  // Cho LEFT JOIN, tránh loại khách chưa có hóa đơn
							"GROUP BY m.id, m.fullname, c.customer_code " +
							"ORDER BY m.id ASC";

	public List<CustomerStat> getCustomerStats(Date startDate, Date endDate) throws SQLException {
		List<CustomerStat> customerStats = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
			ps.setDate(1, (java.sql.Date) startDate);
			ps.setDate(2, (java.sql.Date) endDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				customerStats.add(new CustomerStat(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getInt(4),
							rs.getDouble(5)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customerStats;
	}
}
