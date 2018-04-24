package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author dark_fahad
 *
 */
public class MenuItemDetailsDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;
	/**
	 * @param id
	 * @return
	 */
	public ResultSet getMenuItemDetails(String id){
		ResultSet rs = null;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from menuitem_cd where menuItem_cdId='"+id+"' ";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return rs;
	}

}
