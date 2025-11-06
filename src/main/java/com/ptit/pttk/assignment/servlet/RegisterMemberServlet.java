package com.ptit.pttk.assignment.servlet;

import com.ptit.pttk.assignment.dao.CustomerDAO;
import com.ptit.pttk.assignment.dao.MemberDAO;
import com.ptit.pttk.assignment.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/register")
public class RegisterMemberServlet extends HttpServlet {
	private MemberDAO memberDAO;
	private CustomerDAO customerDAO;

	public void init() {
		memberDAO = new MemberDAO();
		customerDAO = new CustomerDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/registerMemberInterface.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
		Integer gender = request.getParameter("gender").equals("male") ? 0 : 1;
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		try {
			int id = memberDAO.register(new Member(username, password, fullname, dateOfBirth, gender, phone, address));
			if (id == 0) {
				request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
				request.getRequestDispatcher("/registerMemberInterface.jsp").forward(request, response);
				return;
			}

			boolean res = customerDAO.create(id);
			if (res) {
				request.setAttribute("success", "Đăng ký thành công!");
				request.getRequestDispatcher("/registerMemberInterface.jsp").forward(request, response);
				return;
			}

			request.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại.");
			request.getRequestDispatcher("/registerMemberInterface.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			request.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại.");
			request.getRequestDispatcher("/registerMemberInterface.jsp").forward(request, response);
			return;
		}
	}
}
