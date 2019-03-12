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

public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Hitting Manager Servlet");
		String url = "jdbc:postgresql://localhost:5432/project1?currentSchema=reimbursement";
		String username = "postgres";
		String password = "Xgen@5117";
		HttpSession session = req.getSession();

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM forms");

			PrintWriter out = resp.getWriter();
			out.println("<h1>User:" + ' ' + session.getAttribute("userName") + "</h1>");
			while (rs.next()) {
				out.println("Reimbursement #" + ' '  + rs.getInt("id") + "<br>");
				out.println("Employee name:" + ' '  + rs.getString("employee_name") + "<br>");
				out.println("Employee id:" + ' '  + rs.getInt("employee_id") + "<br>");
				out.println("Resolved by Manager:" + ' '  + rs.getString("manager_name") + "<br>");
				out.println("Manager id:" + ' '  + rs.getInt("manager_id") + "<br>");
				out.println("Status:" + ' '  + rs.getString("status") + "<br>");
				out.println("Item name:" + ' '  + rs.getString("item_name") + "<br>");
				out.println("Item description:" + ' '  + rs.getString("item_description") + "<br>");
				out.println("Item cost:$" + ' '  + rs.getString("item_cost") + "<br>");
				out.println("<hr />");
			}
			out.println("<a href ='./managerPage.html'>Back</a>");
			out.println("<a href='logout'>Logout</a>");
			out.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}