package com.ptit.pttk.assignment.servlet;

import com.ptit.pttk.assignment.dao.InvoiceDAO;
import com.ptit.pttk.assignment.model.Invoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/transactions-of-customer")
public class TransactionsOfCustomerServlet extends HttpServlet {
	private InvoiceDAO invoiceDAO;

	@Override
	public void init() {
		invoiceDAO = new InvoiceDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String customerIdStr = request.getParameter("customerId");
			String customerCode = request.getParameter("customerCode");
			String customerName = request.getParameter("customerName");
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			
			if (customerIdStr == null || customerIdStr.isEmpty()) {
				request.setAttribute("error", "Thiếu thông tin khách hàng!");
				request.getRequestDispatcher("/transactionsOfCustomerInterface.jsp").forward(request, response);
				return;
			}
			
			if (startDateStr == null || endDateStr == null || startDateStr.isEmpty() || endDateStr.isEmpty()) {
				request.setAttribute("error", "Thiếu thông tin ngày lọc!");
				request.getRequestDispatcher("/transactionsOfCustomerInterface.jsp").forward(request, response);
				return;
			}

			Integer customerId = Integer.parseInt(customerIdStr);
			Date startDate = Date.valueOf(startDateStr);
			Date endDate = Date.valueOf(endDateStr);
			
			List<Invoice> invoices = invoiceDAO.getCustomerInvoices(customerId, startDate, endDate);
			
			request.setAttribute("invoices", invoices);
			request.setAttribute("customerId", customerId);
			request.setAttribute("startDate", startDateStr);
			request.setAttribute("endDate", endDateStr);
			request.setAttribute("customerCode", customerCode);
			request.setAttribute("customerName", customerName);
		} catch (NumberFormatException e) {
			request.setAttribute("error", "ID khách hàng hoặc định dạng ngày không hợp lệ!");
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", "Định dạng ngày không hợp lệ!");
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Có lỗi xảy ra khi lấy danh sách hóa đơn. Vui lòng thử lại!");
		}
		
		request.getRequestDispatcher("/transactionsOfCustomerInterface.jsp").forward(request, response);
	}
}

