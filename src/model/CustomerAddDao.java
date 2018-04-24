/**
 * CustomerAddDao.java contains methods 
 * which customer related database operations
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import service.EmployeeParams;

public class CustomerAddDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;

	/**
	 * @return
	 */
	public ResultSet getEmployeeId() {
		ResultSet rs = null;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "SELECT * from employee_cd order by empId desc limit 1";
			System.out.println(query);
			rs = stmt.executeQuery(query);

		} catch (SQLException e) {
			System.out.println("\nData retrival form database  failed  " + e);
		}

		return rs;
	}

	/**
	 * @param employeeParams
	 * @return addCust() adds new customer record into the database
	 */
	public boolean addCust(EmployeeParams employeeParams) {
		Boolean bValue = false;
		int rs = 0;
		int ns = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "INSERT INTO employee_cd(empId,firstname,lastname,phoneNo,SSN,email, address)" + "VALUES ('"
					+ employeeParams.getEmpId() + "','" + employeeParams.getFirstName() + "','"
					+ employeeParams.getLastName() + "','" + employeeParams.getContactNo() + "','"
					+ employeeParams.getSsn() + "','" + employeeParams.getEmail() + "','" + employeeParams.getAddress()
					+ "')";
			System.out.println(query);
			rs = stmt.executeUpdate(query);

			if (rs == 1) {
				stmt = connection.createStatement();
				String query2 = "INSERT INTO users_cd(userId,empId,username,password,role)" + "VALUES ('"
						+ employeeParams.getUserId() + "','" + employeeParams.getEmpId() + "','"
						+ employeeParams.getUserName() + "','" + employeeParams.getPassword() + "','"
						+ employeeParams.getRole() + "')";
				System.out.println(query2);
				ns = stmt.executeUpdate(query2);
				if (ns == 1) {
					bValue = true;
				}

			}

		} catch (SQLException e) {
			System.out.println("\nData retrival form database  failed  " + e);
		}

		return bValue;

	}

}
