package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tempuser {
	Integer id;
	String user, pass, name;

	public static String Rankcheck(String userName, String passWord, Integer userId) {
		String rank = null;
		String url = "jdbc:postgresql://localhost:5432/project1?currentSchema=reimbursement";
		String username = "postgres";
		String password = "Xgen@5117";

		Tempuser userCheck = new Tempuser();
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from login_data");

			while (rs.next()) {
				userCheck.id = rs.getInt("id");
				userCheck.user = rs.getString("user_name");
				userCheck.pass = rs.getString("pass_word");

				if (userName.equals(userCheck.user) && passWord.equals(userCheck.pass)) {
					if (userCheck.id == userId) {
						if (userId.equals(1) || userId.equals(2) || userId.equals(3)) {
							rank = "manager";
							break;
						} else {
							rank = "employee";
							break;
						}
					}
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rank;
	}
}