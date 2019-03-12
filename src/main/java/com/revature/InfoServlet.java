package com.revature;

import java.io.IOException;
import java.io.PrintWriter;
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

public class InfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Hitting Info Servlet");
		HttpSession session = req.getSession();
		String url = "jdbc:postgresql://localhost:5432/project1?currentSchema=reimbursement";
		String username = "postgres";
		String password = "Xgen@5117";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT employees.first_name, employees.last_name,login_data.user_name,login_data.pass_word "
					+ "FROM employees JOIN login_data ON employees.id = login_data.id WHERE employees.id = "+ session.getAttribute("id"));
			PrintWriter out = resp.getWriter();
			out.println("<h1>User:" + ' ' + session.getAttribute("userName") + "</h1>");
			while (rs.next()) {
				out.println("Employees name: " + rs.getString("first_name") + ' ' + rs.getString("last_name") + "<br>");
				out.println("Username: " + rs.getString("user_name") + "<br>");
				out.println("Password: " + rs.getString("pass_word") + "<br>");
				out.println("<hr />");
			}
			out.println("<a href='./employeePage.html'>Back</a>");
			out.println("<a href='./updateInfo.html'>Update</a>");
			out.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}