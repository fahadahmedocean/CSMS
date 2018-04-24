/**
 * AdminEmployeeOperationsDao.java contains methods 
 * which employee related database operations
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import service.EmployeeParams;
import service.InventoryParams;

/**
 * @author dark_fahad
 *
 */
public class AdminEmployeeOperationsDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;

	/**
	 * gets Employee Log from the employee_cd table.
	 * @return
	 */
	public ResultSet getEmployeeLog() {
		ResultSet rs = null;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from employee_cd";
			System.out.println(query);
			rs = stmt.executeQuery(query);

		} catch (SQLException e) {
			System.out.println("\nData retrival form database  failed  " + e);
		}

		return rs;
	}

	/**
	 * @param empId
	 * @return
	 * 
	 * deletes the employee record from the database.
	 */
	public Boolean deleteEmployee(int empId) {
		Boolean bValue = false;
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "Delete from employee_cd where empId='" + empId + "' ";
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
	
	/**
	 * @param empId
	 */
	public void deleteParentOrders(int empId) {
		GetUserIdDao getUserIdDao= new GetUserIdDao(); 
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "Delete from orderss_cd where userid='" + getUserIdDao.getMenuItem(String.valueOf(empId)) + "' ";
			System.out.println(query);
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("\nData retrival form database  failed  " + e);
		}		
	}

	/**
	 * @param empId
	 */
	public void deleteParent(int empId) {
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "Delete from users_cd where empId='" + empId + "' ";
			System.out.println(query);
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("\nData retrival form database  failed  " + e);
		}		
	}

	/**
	 * @param employeeParams
	 * @return
	 * addEmp() add new employee record into database
	 */
	public Boolean addEmp(EmployeeParams employeeParams) {
		Boolean bValue = false;
		int rs = 0;
		int ns = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "INSERT INTO employee_cd(empId,firstname,lastname,phoneNo,SSN,email, address)" + "VALUES ('"
					+ employeeParams.getEmpId() + "','" + employeeParams.getFirstName() + "','"
					+ employeeParams.getLastName() + "','" + employeeParams.getContactNo() + "','" + employeeParams.getSsn() + "','"
					+ employeeParams.getEmail() + "','" + employeeParams.getAddress() + "')";
			System.out.println(query);
			rs = stmt.executeUpdate(query);

			if (rs == 1) {
				stmt = connection.createStatement();
				String query2 = "INSERT INTO users_cd(userId,empId,username,password,role)" + "VALUES ('"
						+ employeeParams.getUserId() + "','" + employeeParams.getEmpId() + "','"
						+ employeeParams.getFirstName().toLowerCase() + "','" + "123" + "','" + employeeParams.getRole() + "')";
				System.out.println(query2);
				ns = stmt.executeUpdate(query2);
				if (ns == 1) {
					bValue =true;
				}

			}

		} catch (SQLException e) {
			System.out.println("\nData retrival form database  failed  " + e);
		}

		return bValue;
	}

}
