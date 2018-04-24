package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author dark_fahad
 *
 */
public class GetUsernameDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;
	public ResultSet getUserDetails(String id){
		ResultSet rs = null;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from employee_cd where empId='"+id+"' ";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return rs;
	}
	/**
	 * @param id
	 * @return
	 */
	public ResultSet getUsersloginDetails(String id){
		ResultSet rs = null;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from users_cd where empId='"+id+"' ";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return rs;
	}
	
	
	
}
