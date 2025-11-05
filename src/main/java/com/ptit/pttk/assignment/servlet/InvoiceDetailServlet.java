package com.ptit.pttk.assignment.servlet;

import com.ptit.pttk.assignment.dao.InvoiceDAO;
import com.ptit.pttk.assignment.dao.InvoiceDetailDAO;
import com.ptit.pttk.assignment.model.InvoiceDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/invoice-detail")
public class InvoiceDetailServlet extends HttpServlet {
	private InvoiceDetailDAO invoiceDetailDAO;

	@Override
	public void init() {
		invoiceDetailDAO = new InvoiceDetailDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String invoiceId = request.getParameter("invoiceId");
			String customerName = request.getParameter("customerName");
			String customerCode = request.getParameter("customerCode");
			if (invoiceId == null || invoiceId.isEmpty()) {
				request.setAttribute("error", "Thiếu thông tin khách hàng!");
				request.getRequestDispatcher("/invoiceDetailInterface.jsp").forward(request, response);
				return;
			}

			List<InvoiceDetail> invoiceDetails = invoiceDetailDAO.getInvoiceDetails(Integer.parseInt(invoiceId));
			request.setAttribute("invoiceDetails", invoiceDetails);
			request.setAttribute("customerName", customerName);
			request.setAttribute("customerCode", customerCode);
		} catch (NumberFormatException e) {
			request.setAttribute("error", "ID khách hàng hoặc định dạng ngày không hợp lệ!");
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", "Định dạng ngày không hợp lệ!");
		} catch (SQLException e) {
			request.setAttribute("error", "Lỗi truy xuất dữ liệu");
		}

		request.getRequestDispatcher("/invoiceDetailInterface.jsp").forward(request, response);
	}
}
