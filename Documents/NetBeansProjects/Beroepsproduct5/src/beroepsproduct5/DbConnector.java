package beroepsproduct5;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnector {

	ResultSet result = null;

	public DbConnector() {

	}

	private Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String strConnString = ("jdbc:mysql://145.49.123.25/bp5db");  //IP sebastiaan 169.254.121.206/16
			conn = DriverManager.getConnection(strConnString, "newuser", "password");
		} catch (Exception e) {
			// error
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public ResultSet getData(String strSQL) {
		try {
			Statement stmt = createConnection().createStatement();
			result = stmt.executeQuery(strSQL);
		} catch (Exception e) {
			// error
			System.out.println(e.getMessage());
		}
		return result;
	}

	public int executeDML(String strSQL) {
		int result = 0;
		try {
			Statement stmt = createConnection().createStatement();
			result = stmt.executeUpdate(strSQL);
		} catch (Exception e) {
			// error
		}
		return result;
	}
}
