package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author dark_fahad
 *
 */
public class CustomerViewDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;
	/**
	 * @return
	 */
	public ResultSet getMenuItem(){
		ResultSet rs = null;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from menuItem_cd";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return rs;
	}

}
