package com.revature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		System.out.println("Starting up");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("Hitting Login Servlet");
		String user = (String) session.getAttribute("user");
		String password = (String) session.getAttribute("password");
		String id = (String) session.getAttribute("id");
		Integer idInt = 0;
		String check = (String) session.getAttribute("check");

		if (user == null) {
			id = request.getParameter("user_id");
			idInt = Integer.parseInt(id);
			user = request.getParameter("user");
			password = request.getParameter("password");
			session.setAttribute("id", id);
			session.setAttribute("user", user);
			session.setAttribute("password", password);
			check = Tempuser.Rankcheck(user, password, idInt);
			session.setAttribute("check", check);
			System.out.println(check);
		}
		String url = "jdbc:postgresql://localhost:5432/project1?currentSchema=reimbursement";
		String username = "postgres";
		String pass = "Xgen@5117";

		if (check == null) {
			response.sendRedirect("./errorPage.html");
		} else {
			switch (check) {
			case "employee":
				try (Connection connection = DriverManager.getConnection(url, username, pass)) {
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(
							"SELECT employees.first_name, employees.last_name\r\n" + "			FROM employees\r\n"
									+ "				JOIN login_data ON employees.id = login_data.id\r\n"
									+ "			WHERE login_data.id = " + session.getAttribute("id"));
					rs.next();
					String empName = rs.getString("first_name") + ' ' + rs.getString("last_name");
					System.out.println(empName);
					session.setAttribute("userName", empName);
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher("./employeePage.html").forward(request, response);
				break;
			case "manager":
				try (Connection connection = DriverManager.getConnection(url, username, pass)) {
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(
							"SELECT managers.first_name, managers.last_name\r\n" + "			FROM managers\r\n"
									+ "				JOIN login_data ON managers.id = login_data.id\r\n"
									+ "			WHERE login_data.id = " + session.getAttribute("id"));
					rs.next();
					String manName = rs.getString("first_name") + ' ' + rs.getString("last_name");
					System.out.println(manName);
					session.setAttribute("userName", manName);
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher("./managerPage.html").forward(request, response);
				break;
			default:
				response.sendRedirect("./errorPage.html");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("Shutting down");
	}
}

//if (user.equals("Mehrab")) {
//request.getRequestDispatcher("WEB-INF/secret.html").forward(request, response);
//}
