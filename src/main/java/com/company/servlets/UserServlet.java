package com.company.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.models.User;
import com.company.util.DataBase;
import com.company.dao.UserDao;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/User")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		User user = null;

		if ("Sign Up".equals(action)) {
			String firstname = request.getParameter("firstName");
			String lastname = request.getParameter("lastName");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			String password = request.getParameter("password");

			user = new User(firstname, lastname, phone, address, password);
			try {
				UserDao userdao = UserDao.getInstance(DataBase.getConnection());
				userdao.addUser(user);
			} catch (SQLException e) {
				e.printStackTrace();
				out.write("Error: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if ("Sign In".equals(action)) {
//			String attribute = "";
//			request.setAttribute("attribute", attribute);
//			request.getRequestDispatcher("response.jsp").forward(request, response);

			String phone = request.getParameter("phone");
			String password = request.getParameter("password");
			try {
				UserDao userDao = UserDao.getInstance(DataBase.getConnection());
				user = userDao.auth(phone, password);
			} catch (SQLException e) {
				e.printStackTrace();
				out.write("Error: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		out.println("<html><body>");
		out.println("<h2>User:</h2>");
		out.println("First Name: " + user.getFirstName());
		out.println("Last Name: " + user.getLastName());
		out.println("Phone: " + user.getPhone());
		out.println("</body></html>");
		
		out.close();
	}

}
