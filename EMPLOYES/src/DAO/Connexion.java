package DAO;

import java.sql.*;

public class Connexion {
	
	private static final String URL ="jdbc:mysql://localhost:3306/employe_management";
	private static final String USER = "root";
    private static final String PASSWORD = " ";
	static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn != null) {
		    System.out.println("Database connection established.");
		}
		try {
			
			conn = DriverManager.getConnection(URL , USER , PASSWORD);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
