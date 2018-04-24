package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import service.OrderParams;


/**
 * @author dark_fahad
 *
 */
public class InsertOrderDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;
	
	/**
	 * @param orderParams
	 */
	public void insertsOrder(OrderParams orderParams){
		
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = null;
				query = "INSERT INTO orderss_cd(userid,orderItems,orderPrice)" +
			          "VALUES ('"+ orderParams.getUserid()+"','"
						         + orderParams.getOrderItems()+"','"
			                     + orderParams.getOrderPrice()+"')";
				System.out.println(" SQL Query --> "+query);		
				 stmt.executeUpdate(query);
			          		     		
		stmt.close();
		}catch(SQLException e){
			System.out.println("\nFailed to insert records in the table " +e);
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

}
