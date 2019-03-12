package com.revature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ClearSessionServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println("hitting Clear Servlet");
		HttpSession session = req.getSession();
		session.removeAttribute("reim_id");
		session.removeAttribute("emp_name");
		session.removeAttribute("emp_id");
		session.removeAttribute("man_name");
		session.removeAttribute("man_id");
		session.removeAttribute("status");
		session.removeAttribute("item_name");
		session.removeAttribute("item_description");
		session.removeAttribute("item_cost");
		req.getRequestDispatcher("insert").forward(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}