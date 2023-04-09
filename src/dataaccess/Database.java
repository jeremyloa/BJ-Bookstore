package dataaccess;

import java.sql.*;

public class Database {

	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "book_store";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection con;
	private ResultSet res;
	private Statement stm;
	public Database() {
		while (true) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
				stm = con.createStatement();
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Database db;
	public static Database getInstance() {
		if (db==null) 
			return new Database();
		return db;
	}
	
	public void insertupdatedelete (String statement) {
		try {
			stm.executeUpdate(statement);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public ResultSet select (String statement) {
		try {
			res = stm.executeQuery(statement);
		} catch (Exception e) {e.printStackTrace();}
		return res;
	}
	
	public PreparedStatement prep (String statement) {
		PreparedStatement preps = null;
		try {
			preps = con.prepareStatement(statement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preps;
	}
	
}
