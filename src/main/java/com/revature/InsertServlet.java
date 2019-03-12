package com.revature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("hitting Insert Servlet");
		String url = "jdbc:postgresql://localhost:5432/project1?currentSchema=reimbursement";
		String username = "postgres";
		String password = "Xgen@5117";
		Integer formId = 1;
		HttpSession session = req.getSession();
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT id FROM forms");

			Random rand = new Random();
			while (rs.next()) {
				if (rs.getInt("id") == formId) {
					formId = rand.nextInt(50);
				}
			}
			statement.executeUpdate("INSERT INTO forms VALUES (" + formId + ",'" + session.getAttribute("userName")
					+ "', " + session.getAttribute("id") + ",'unresolved',0,'pending','" + session.getAttribute("itemName") + "', '"
					+ session.getAttribute("itemDescription") + "', " + session.getAttribute("itemCost") + ")");
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("./employeePage.html").forward(req, resp);
		;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
