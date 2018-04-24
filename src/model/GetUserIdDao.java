package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author dark_fahad
 *
 */
public class GetUserIdDao {
	
		Connector connector = new Connector();
		Connection connection = null;
		Statement stmt = null;
		/**
		 * @param empId
		 * @return
		 */
		public String getMenuItem(String empId){
			String userId = null;
			ResultSet rs = null;
			try{
				connection = connector.connectDataBase();
				stmt = connection.createStatement();
				String query = "SELECT * from users_cd where empId ='"+empId+"' ";
				System.out.println(query);
				rs = stmt.executeQuery(query);
				if(rs.next()){
				userId = rs.getString(1);
				}
			}
			catch(SQLException e){
				System.out.println("\nData retrival form database  failed  "+e);
			}
			
		return userId;
		}
		/*public String getUserId(String empId){
			String userId = null;
			ResultSet rs = null;
			try{
				connection = connector.connectDataBase();
				stmt = connection.createStatement();
				String query = "SELECT * from users_cd where empId ='"+empId+"' ";
				System.out.println(query);
				rs = stmt.executeQuery(query);
				if(rs.next()){
				userId = rs.getString(1);
				}
			}
			catch(SQLException e){
				System.out.println("\nData retrival form database  failed  "+e);
			}
			
		return userId;
		}*/
		
		

	}
