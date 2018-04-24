package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author dark_fahad
 *
 */
public class GetOrderLogDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;
	/**
	 * @return
	 */
	public ResultSet getOrderLog(){
		ResultSet rs = null;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from orderss_cd";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return rs;
	}

}
