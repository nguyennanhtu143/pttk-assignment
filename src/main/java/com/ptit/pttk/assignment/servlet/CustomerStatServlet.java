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
			
			if (startDateStr == null || endDateStr == null || startDateStr.isEmpty() || endDateStr.isEmpty()) {
				request.setAttribute("error", "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc!");
				request.getRequestDispatcher("/customerStatInterface.jsp").forward(request, response);
				return;
			}
			
			Date startDate = Date.valueOf(startDateStr);
			Date endDate = Date.valueOf(endDateStr);
			
			List<CustomerStat> customerStats = customerStatDAO.getCustomerStats(startDate, endDate);
			request.setAttribute("customerStats", customerStats);
			request.setAttribute("startDate", startDateStr);
			request.setAttribute("endDate", endDateStr);
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Có lỗi xảy ra khi lấy thống kê. Vui lòng thử lại!");
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", "Định dạng ngày không hợp lệ!");
		}
		
		request.getRequestDispatcher("/customerStatInterface.jsp").forward(request, response);
	}
}




