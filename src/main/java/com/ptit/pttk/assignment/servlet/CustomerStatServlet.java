package com.ptit.pttk.assignment.servlet;

import com.ptit.pttk.assignment.dao.CustomerStatDAO;
import com.ptit.pttk.assignment.model.CustomerStat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/customer-stat")
public class CustomerStatServlet extends HttpServlet {
	private CustomerStatDAO customerStatDAO;

	public void init() {
		customerStatDAO = new CustomerStatDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/customerStatInterface.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			// Phân trang: page và pageSize
			int page = 1;
			int pageSize = 10;
			try {
				page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
			} catch (NumberFormatException ignore) { page = 1; }
			try {
				pageSize = request.getParameter("pageSize") != null ? Integer.parseInt(request.getParameter("pageSize")) : 10;
			} catch (NumberFormatException ignore) { pageSize = 10; }
			
			if (startDateStr == null || endDateStr == null || startDateStr.isEmpty() || endDateStr.isEmpty()) {
				request.setAttribute("error", "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc!");
				request.getRequestDispatcher("/customerStatInterface.jsp").forward(request, response);
				return;
			}
			
			Date startDate = Date.valueOf(startDateStr);
			Date endDate = Date.valueOf(endDateStr);
			
			List<CustomerStat> customerStats = customerStatDAO.getCustomerStats(startDate, endDate);

			//phan trang
			int totalItems = customerStats != null ? customerStats.size() : 0;
			int totalPages = (int) Math.ceil(totalItems / (double) Math.max(pageSize, 1));
			if (totalPages == 0) totalPages = 1;
			if (page < 1) page = 1;
			if (page > totalPages) page = totalPages;
			int fromIndex = Math.max(0, (page - 1) * Math.max(pageSize, 1));
			int toIndex = Math.min(totalItems, fromIndex + Math.max(pageSize, 1));
			List<CustomerStat> pagedStats = customerStats != null ? customerStats.subList(fromIndex, toIndex) : customerStats;

			request.setAttribute("customerStats", pagedStats);
			request.setAttribute("startDate", startDateStr);
			request.setAttribute("endDate", endDateStr);
			request.setAttribute("page", page);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("totalItems", totalItems);
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Có lỗi xảy ra khi lấy thống kê. Vui lòng thử lại!");
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", "Định dạng ngày không hợp lệ!");
		}
		
		request.getRequestDispatcher("/customerStatInterface.jsp").forward(request, response);
	}
}




