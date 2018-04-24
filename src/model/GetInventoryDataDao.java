package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import service.InventoryParams;

/**
 * @author dark_fahad
 *
 */
public class GetInventoryDataDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;
	public ResultSet getInventoryData(){
		ResultSet rs = null;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from inventory_cd";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return rs;
	}
	
	/**
	 * @param invId
	 * @return
	 */
	public Boolean deleteInventoryData(int invId){
		Boolean bValue = false;
		int rs = 0;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "Delete from inventory_cd where itemId='"+invId+"' ";
			System.out.println(query);
			rs = stmt.executeUpdate(query);
			
			if (rs == 1) {
				bValue = true;
			}

		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return bValue;
	}
	
	/**
	 * @param inventoryParams
	 * @return
	 */
	public Boolean addInventoryData(InventoryParams inventoryParams){
		Boolean bValue = false;
		int rs = 0;
		try{
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "INSERT INTO inventory_cd(itemId,itemName,itemQuantity)" +
			          "VALUES ('"+ inventoryParams.getItemId()+"','"
				         + inventoryParams.getItemName()+"','"
	                     + inventoryParams.getItemQuantity()+"')";
			System.out.println(query);
			rs = stmt.executeUpdate(query);
			
			if (rs == 1) {
				bValue = true;
			}

		}
		catch(SQLException e){
			System.out.println("\nData retrival form database  failed  "+e);
		}
		
	return bValue;
	}
	
	/**
	 * @param inventoryParams
	 * @return
	 */
	public Boolean updateInventory(InventoryParams inventoryParams) {
		Boolean bValue = false;
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "update inventory_cd set itemQuantity = '" + inventoryParams.getItemQuantity() + "'  where itemId='" + inventoryParams.getItemId() + "' ";
			System.out.println(query);
			rs = stmt.executeUpdate(query);

			if (rs == 1) {
				bValue = true;
			}

		} catch (SQLException e) {
			System.out.println("\nData retrival form database  failed  " + e);
		}

		return bValue;
	}


}
