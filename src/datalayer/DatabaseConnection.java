package datalayer;

import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseConnection {
	
	private static Connection conn;

	public static Connection getConnection() throws SQLException {
		
		if (conn == null || conn.isClosed()) {
			conn = magicallyCreateNewConnection();
		}
		return conn;
	}

	private static Connection magicallyCreateNewConnection() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
