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

public class ViewEmpServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException,ServletException{
		System.out.println("Hitting ViewEmp Servlet");
		String url = "jdbc:postgresql://localhost:5432/project1?currentSchema=reimbursement";
		String username = "postgres";
		String password = "Xgen@5117";
		HttpSession session = req.getSession();

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM employees");
			
			PrintWriter out = resp.getWriter();
			out.println("<h1>User:" + ' ' + session.getAttribute("userName") + "</h1>");
			out.println("Employee list:<br>");
			while (rs.next()) {
				out.println("Id:" + ' '  + ' ' + rs.getInt("id"));
				out.println("Name:" + ' '  + ' ' + rs.getString("first_name") + ' ' + rs.getString("last_name") + "<br>");
			}
			out.println("<a href ='./managerPage.html'>Back</a>");
			out.println("<a href='logout'>Logout</a>");
			out.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException,ServletException{
		doGet(req,resp);
	}
}