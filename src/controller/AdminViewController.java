/**
 * AdminViewController.java contains methods 
 * which helps Admin to create and delete employee information 
 * and Update his/her personal information
 */
package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.AdminEmployeeOperationsDao;
import service.EmployeeParams;

/**
 * @author clitus dmonte
 *
 */
public class AdminViewController implements Initializable {

	@FXML
	private Pane adminOperationsPane, adminViewPane;
	@FXML
	private TableView empoyeeLogsTableView;
	@FXML
	private TextField deleteEmpTextFeild, addEmpID, addFirstName, addLastName, addContactNo, addSSN, addEmailID,
			addAddress, addUserID, addRole;
	@FXML
	private Label errorLabel, errorAddEmployee, errorAddEmployee1;
	private Stage dialogStage, primaryStage, updateStage;
	private ObservableList<ObservableList> data;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adminViewPane.setVisible(true);
		adminOperationsPane.setVisible(false);

	}

	/**
	 * addDeleteEmpOnAction() method loads the employee data in to tableview up
	 * on adding or deleting employee record.
	 */

	@FXML
	public void addDeleteEmpOnAction() {
		adminViewPane.setVisible(false);
		adminOperationsPane.setVisible(true);

		if (!(empoyeeLogsTableView.getItems() == null) && !(data == null)) {
			empoyeeLogsTableView.getItems().clear();
			empoyeeLogsTableView.getColumns().clear();
			data.clear();
		}

		// creating observableArrayList object to store employee records and
		// load it in to tableview.
		data = FXCollections.observableArrayList();
		AdminEmployeeOperationsDao adminEmployeeOperationsDao = new AdminEmployeeOperationsDao();
		ResultSet rs = adminEmployeeOperationsDao.getEmployeeLog();
		try {
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				empoyeeLogsTableView.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");
			}

			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}
			// adding observableArrayList object into tableview.
			empoyeeLogsTableView.setItems(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * updateInformation() method loads the admins update profile view using
	 * FXMLLoader into new Scene .
	 */
	@FXML
	public void updateInformation() {
		updateStage = new Stage();
		try {

			// Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/updatePofile.fxml"));
			// Inflate the view using the loader
			AnchorPane root = (AnchorPane) loader.load();
			// Set window title
			updateStage.setTitle("Online Coffee Shop Management System");
			// Create a scene with the inflated view
			Scene scene = new Scene(root);
			// Set the scene to the stage
			updateStage.setScene(scene);
			// Get the controller instance from the loader
			UpdateProfileController controller = loader.getController();

			// Set the parent stage in the controller
			controller.setDialogStage(updateStage);
			// Show the view
			updateStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());
		}
	}

	/**
	 * deleteEmpOnAction() method deletes employee employee record from the
	 * database.
	 */
	@FXML
	public void deleteEmpOnAction() {
		errorLabel.setText("");
		AdminEmployeeOperationsDao adminEmployeeOperationsDao = new AdminEmployeeOperationsDao();
		if (!(deleteEmpTextFeild.getText().isEmpty())) {
			adminEmployeeOperationsDao.deleteParentOrders(Integer.parseInt(deleteEmpTextFeild.getText()));
			adminEmployeeOperationsDao.deleteParent(Integer.parseInt(deleteEmpTextFeild.getText()));
			adminEmployeeOperationsDao.deleteEmployee(Integer.parseInt(deleteEmpTextFeild.getText()));

		} else {
			errorLabel.setText("Enter Employee Id");
		}
		addDeleteEmpOnAction();
		deleteEmpTextFeild.setText("");
		addDeleteEmpOnAction();

	}

	/**
	 * addEmployeeOnAction() method adds employee record into the database.
	 */
	@FXML
	public void addEmployeeOnAction() {
		Boolean add = false;
		errorAddEmployee.setText("");
		errorAddEmployee1.setText("");

		EmployeeParams employeeParams = new EmployeeParams();
		AdminEmployeeOperationsDao adminEmployeeOperationsDao = new AdminEmployeeOperationsDao();
		// if condition to check if all employee details has been entered.
		if (!(addEmpID.getText().toString()).isEmpty() && !(addFirstName.getText().toString()).isEmpty()
				&& !(addLastName.getText().toString()).isEmpty() && !(addContactNo.getText().toString()).isEmpty()
				&& !(addSSN.getText().toString()).isEmpty() && !(addEmailID.getText().toString()).isEmpty()
				&& !(addAddress.getText().toString()).isEmpty() && !(addUserID.getText().toString()).isEmpty()
				&& !(addRole.getText().toString()).isEmpty()) {
			add = true;
			employeeParams.setEmpId(addEmpID.getText());
			employeeParams.setFirstName(addFirstName.getText());
			employeeParams.setLastName(addLastName.getText());
			if (((addContactNo.getText()).length() == 10)) {
				employeeParams.setContactNo(Long.parseLong(addContactNo.getText()));
			} else {
				add = false;
				errorAddEmployee.setText("Enter valid Contact No.");

			}
			if ((addSSN.getText()).length() == 9) {
				employeeParams.setSsn(Integer.parseInt(addSSN.getText()));
			} else {
				add = false;
				errorAddEmployee.setText("Enter valid SSN");
			}
			if (addEmailID.getText().contains("@") && addEmailID.getText().contains(".")) {
				employeeParams.setEmail(addEmailID.getText());

			} else {
				errorAddEmployee.setText("Enter Valid Employee Email ID");
				add = false;
			}
			employeeParams.setAddress(addAddress.getText());
			employeeParams.setUserId(addUserID.getText());
			employeeParams.setRole(addRole.getText());

		} else {
			errorAddEmployee.setText("Enter employee info. in all fields");
			add = false;
		}
		if (addEmailID.getText().contains("@") && addEmailID.getText().contains(".") && add.equals(true)) {
			employeeParams.setEmail(addEmailID.getText());

		} else if (add.equals(true)) {
			errorAddEmployee.setText("Enter Valid Employee Email ID");
			add = false;
		}

		if (add == true) {
			add = adminEmployeeOperationsDao.addEmp(employeeParams);
		}

		if (add == true) {
			errorAddEmployee.setText("Emplyoee added");
			errorAddEmployee1
					.setText("Temporary Login ID: " + addFirstName.getText().toLowerCase() + " Password : 123");
			addEmpID.clear();
			addUserID.clear();
			addFirstName.clear();
			addLastName.clear();
			addContactNo.clear();
			addRole.clear();
			addSSN.clear();
			addEmailID.clear();
			addAddress.clear();
		}

		addDeleteEmpOnAction();

	}

	/**
	 * logoutButtonOnAction() method loads the logout view using FXMLLoader into
	 * new Scene .
	 */
	@FXML
	public void logoutButtonOnAction() {
		try {
			// primaryStage.close();
			primaryStage = new Stage();
			dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));

			// Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/logOut.fxml"));
			// Inflate the view using the loader
			AnchorPane root = (AnchorPane) loader.load();

			// Set window title
			primaryStage.setTitle("Online Coffee Shop Management System");
			// Create a scene with the inflated view
			Scene scene = new Scene(root);
			// Set the scene to the stage
			primaryStage.setScene(scene);
			// Get the controller instance from the loader
			LogoutController controller = loader.getController();
			// Set the parent stage in the controller
			controller.setDialogStage(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

	/**
	 * gives admin to switch to the previous screen.
	 */
	@FXML
	public void backOnAction() {
		adminViewPane.setVisible(true);
		adminOperationsPane.setVisible(false);

	}

	/**
	 * @param primaryStage
	 */
	public void setDialogStage(Stage primaryStage) {
		this.dialogStage = primaryStage;

	}

}
