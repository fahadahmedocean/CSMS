/**
 * daoModel will process all necessary CRUD operations.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import service.LoginParams;


/**
 * @author dark_fahad
 *
 */
public class LoginDaoModel {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;
	
	/**
	 * createTable() method creates c_dmon_tab table.
	 */
	public void createTable(){
	    
		connection = connector.connectDataBase();
		try {
			stmt = connection.createStatement();
			
		String query = "CREATE TABLE c_dmon_tab" + 
		               "(personid INTEGER NOT NULL AUTO_INCREMENT," + 
				       "id VARCHAR(7),"+ 
		               "income NUMERIC(8,2)," + 
				       "pep VARCHAR(10)," + 
		               "PRIMARY KEY(personid))";
		stmt.executeUpdate(query);
		System.out.println("\nTable  c_dmon_tab Created in the Database");
		stmt.close();
		} catch (SQLException e) {
			System.out.println("\nTable  c_dmon_tab Creation Failed "+e);
		} 
		finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * @param loginParams 
	 * @return the rs
	 * getResultSet() method obtain your ResultSet object 
	 */
	public ResultSet getResultSet(LoginParams loginParams){
		ResultSet rs = null;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from users_cd where username='"+loginParams.getUsername().toString()+"' "
					+ "and password='"+loginParams.getPassword().toString()+"' ";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return rs;
	}
	
}
