package com.revature;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Hitting Update Servlet");
		HttpSession session = req.getSession();
		String url = "jdbc:postgresql://localhost:5432/project1?currentSchema=reimbursement";
		String username = "postgres";
		String password = "Xgen@5117";

		String newUserName = req.getParameter("newName");
		String newPass = req.getParameter("newPass");
		String checkPass = req.getParameter("checkPass");
		System.out.println(newUserName + ' ' + newPass + ' ' + checkPass);
		if (newUserName != null) {
			if (newPass != null) {
				if (checkPass != null) {
					if (newPass.equals(checkPass)) {
						try (Connection connection = DriverManager.getConnection(url, username, password)) {
							Statement statement = connection.createStatement();
							statement.executeUpdate("UPDATE login_data SET user_name = '" + newUserName + "',pass_word = '" + newPass + "' WHERE id ="
									+ session.getAttribute("id"));

						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						PrintWriter out = resp.getWriter();
						out.println("<h1>ERROR PAGE: </h1>");
						out.println("The new password you have entered does not match the 'Re-type password' field!");
						out.println("<a href='./updateInfo.html'>Back</a>");
						out.close();
					}
				}
			}
		}
		req.getRequestDispatcher("./employeePage.html").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}