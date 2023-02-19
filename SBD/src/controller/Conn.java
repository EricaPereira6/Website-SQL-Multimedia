package controller;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;


public class Conn {

	private Connection conn = null;
	private static String sqlhost;
	private static String sqluser;
	private static String sqlpass;
	private static String dbDriver;
	private static String dbProvider;
	
	
	
	public Conn() {
		sqlhost = "jdbc:mysql://localhost:3306/isel_share?useLegacyDatetimeCode=false&serverTimezone=UTC";
		sqluser = "javaClient";
		sqluser = "root";
		sqlpass = "javaPass";
		sqlpass = "root";
		dbDriver =  "com.mysql.cj.jdbc.Driver";
		dbProvider = "mysql";
		
	}
	
	public Connection closeSession() throws SQLException {
		conn.close();
		return conn;
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public Connection rollback() throws SQLException {
		conn.rollback();
		return conn;
	}
	
	public Connection openSession() throws SQLException {
		try {
			MysqlDataSource mysqlDS = null;
			mysqlDS = new MysqlDataSource();
			mysqlDS.setURL(sqlhost);
			mysqlDS.setUser(sqluser);
			mysqlDS.setPassword(sqlpass);
			
//			Class.forName(dbDriver);
			
			conn = mysqlDS.getConnection();	
			conn.setAutoCommit(true);
		}
		catch(SQLException ex) {
			throw new SQLException("Could not connect to database; Wrong Parameters or Stopped Server?" + ex.getMessage());
		} 
//		catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.err.println("Mysql connector jar not found");
//		}
		return conn;
	}
}
