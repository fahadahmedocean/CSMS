package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author dark_fahad
 *
 */
public class UpdateUserDao {
	Connector connector = new Connector();
	Connection connection = null;
	Statement stmt = null;

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Boolean updateAdress(String id, String value) {
		Boolean bValue = false;
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "update employee_cd set address = '" + value + "'  where empId='" + id + "' ";
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
	 * @param id
	 * @param value
	 * @return
	 */
	public Boolean updateSSN(String id, String value) {
		Boolean bValue = false;
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "update employee_cd set SSN = '" + value + "'  where empId='" + id + "' ";
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
	 * @param id
	 * @param value
	 * @return
	 */
	public Boolean updateContactNo(String id, String value) {
		Boolean bValue = false;
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "update employee_cd set phoneNo = '" + value + "'  where empId='" + id + "' ";
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
	 * @param id
	 * @param value
	 * @return
	 */
	public Boolean updateEmailID(String id, String value) {
		Boolean bValue = false;
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "update employee_cd set email = '" + value + "'  where empId='" + id + "' ";
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
	 * @param id
	 * @param value
	 * @return
	 */
	public Boolean updateLoginId(String id, String value) {
		Boolean bValue = false;
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "update users_cd set username = '" + value + "'  where empId='" + id + "' ";
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
	 * @param id
	 * @param value
	 * @return
	 */
	public Boolean updatePassword(String id, String value) {
		Boolean bValue = false;
		int rs = 0;
		try {
			connection = connector.connectDataBase();
			stmt = connection.createStatement();
			String query = "update users_cd set password = '" + value + "'  where empId='" + id + "' ";
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
