package com.revature;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
		System.out.println("hitting Employee Servlet");

		PrintWriter out = resp.getWriter();
		out.println("<h1>Hello there," + session.getAttribute("userName") + "</h1><br>");
		out.println("Your reimbursement #" + session.getAttribute("reim_id") + "<br>");
		out.println("Employee name:" + session.getAttribute("emp_name") + "<br>");
		out.println("Manager name:" + session.getAttribute("man_name") + "<br>");
		out.println("Manager id:" + session.getAttribute("man_id") + "<br>");
		out.println("Status:" + session.getAttribute("status") + "<br>");
		out.println("Item name:" + session.getAttribute("item_name") + "<br>");
		out.println("Item description id:" + session.getAttribute("item_description") + "<br>");
		out.println("Item cost:" + session.getAttribute("item_cost") + "<br>");
		out.println("<a href ='clear'>Back</a>");
		out.println("<a href='logout'>Logout</a>");
		out.close();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}