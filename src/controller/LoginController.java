/**
 * LoginController.java contains methods 
 * which authenticates the users login credentials and gives or 
 * restrict the access of users to the system.
 */
package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.CustomerAddDao;
import model.GetUsernameDao;
import model.LoginDaoModel;
import service.EmployeeParams;
import service.LoginParams;

public class LoginController implements Initializable {
	@FXML
	private Button customerLoginButton, employeeLoginButton, adminLoginButton, signUpButton;
	@FXML
	private GridPane loginGridPane1, buttonsGridPane2, footerGridPane, loginDetailsGridPane;
	@FXML
	private TextField loginIdTextField, firstNamett, lastnamett, userName, password, contactNo, ssn, email, address;
	@FXML
	private PasswordField passwordIdTextField;
	@FXML
	private Label errorMsg, custErr, loginViewLabel;
	@FXML
	private Pane custRegPane;
	private LoginParams loginParams;
	private LoginDaoModel loginDaoModel;
	private String roleName;
	private Stage primaryStage, dialogStage;
	private CustomerViewController customerViewController;
	private GetUsernameDao getUsernameDao;
	private Properties prop;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginDetailsGridPane.setVisible(false);
		loginGridPane1.setVisible(true);
		buttonsGridPane2.setVisible(true);
		custRegPane.setVisible(false);

	}

	/**
	 * loginPanel() loads user login panel
	 */
	void loginPanel() {
		loginGridPane1.setVisible(false);
		buttonsGridPane2.setVisible(false);
		loginDetailsGridPane.setVisible(true);
		signUpButton.setVisible(false);
	}

	/**
	 * loginButtonOnAction() validates the users login credentials.
	 */
	@FXML
	void loginButtonOnAction() {
		try {
			loginParams = new LoginParams();
			loginDaoModel = new LoginDaoModel();
			Boolean isValidUser = false;

			if (!(loginIdTextField.getText().toString()).isEmpty()
					&& !(passwordIdTextField.getText().toString()).isEmpty()) {
				loginParams.setUsername(loginIdTextField.getText().toString());
				loginParams.setPassword(passwordIdTextField.getText().toString());
				ResultSet resultset = loginDaoModel.getResultSet(loginParams);
				int i = 3;

				if (resultset.next() == true && loginIdTextField.getText().equals(resultset.getString(3))
						&& passwordIdTextField.getText().equals(resultset.getString(4))) {

					if (!roleName.equals(resultset.getString(5))) {
						errorMsg.setText("");
						errorMsg.setText("Please enter " + roleName + " login details correctly...");

					} else {
						errorMsg.setText("");
						errorMsg.setText("Login Successful");
						System.out.println("Login Successful");
						isValidUser = true;

						try {
							String empId = resultset.getString(2);
							String name = null;
							String address = null;

							getUsernameDao = new GetUsernameDao();
							ResultSet rs = getUsernameDao.getUserDetails(empId);

							if (rs.next()) {
								name = rs.getString(2);
								address = rs.getString(7);
							}
							OutputStream output = null;
							output = new FileOutputStream("config.properties");

							prop = new Properties();
							prop.setProperty("LoginID", loginIdTextField.getText());
							prop.setProperty("userEMPId", empId);
							prop.setProperty("user", name);
							prop.setProperty("address", address);

							prop.store(output, null);
							output.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					i = 0;
				} else {
					errorMsg.setText("");
					errorMsg.setText("Please enter login details correctly...");
				}

				if (isValidUser && roleName.equals("customer")) {
					loadCustomerView();
				} else if (isValidUser && roleName.equals("admin")) {
					loadAdminView();
				} else if (isValidUser && roleName.equals("manager")) {
					loadManagerView();
				} else if (isValidUser && roleName.equals("delivery")) {
					loadEmployeeView();
				} else if (isValidUser && roleName.equals("chef")) {
					loadEmployeeView();
				}

			} else {
				errorMsg.setText("");
				errorMsg.setText("Please enter login details correctly...");
			}
			loginIdTextField.clear();
			passwordIdTextField.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * loadEmployeeView() method loads the employee view using FXMLLoader into
	 * new Scene .
	 */
	private void loadEmployeeView() {
		primaryStage = new Stage();
		dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
		try {

			// Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employeeView.fxml"));
			// Inflate the view using the loader
			AnchorPane root = (AnchorPane) loader.load();
			// Set window title
			primaryStage.setTitle("Online Coffee Shop Management System");
			// Create a scene with the inflated view
			Scene scene = new Scene(root);
			// Set the scene to the stage
			primaryStage.setScene(scene);
			// Get the controller instance from the loader
			EmployeeController controller = loader.getController();

			// Set the parent stage in the controller
			controller.setDialogStage(primaryStage);
			// Show the view
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());

		}
	}

	/**
	 * loadManagerView() method loads the managers view using FXMLLoader into
	 * new Scene .
	 */
	private void loadManagerView() {
		primaryStage = new Stage();
		dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
		try {

			// Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/managerView.fxml"));
			// Inflate the view using the loader
			AnchorPane root = (AnchorPane) loader.load();
			// Set window title
			primaryStage.setTitle("Online Coffee Shop Management System");
			// Create a scene with the inflated view
			Scene scene = new Scene(root);
			// Set the scene to the stage
			primaryStage.setScene(scene);
			// Get the controller instance from the loader
			ManagerViewController controller = loader.getController();

			// Set the parent stage in the controller
			controller.setDialogStage(primaryStage);
			// Show the view
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());

		}

	}

	/**
	 * loadAdminView() method loads the admins view using FXMLLoader into new
	 * Scene .
	 */
	private void loadAdminView() {
		primaryStage = new Stage();
		dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
		try {

			// Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/adminView.fxml"));
			// Inflate the view using the loader
			AnchorPane root = (AnchorPane) loader.load();
			// Set window title
			primaryStage.setTitle("Online Coffee Shop Management System");
			// Create a scene with the inflated view
			Scene scene = new Scene(root);
			// Set the scene to the stage
			primaryStage.setScene(scene);
			// Get the controller instance from the loader
			AdminViewController controller = loader.getController();

			// Set the parent stage in the controller
			controller.setDialogStage(primaryStage);
			// Show the view
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());

		}
	}

	/**
	 * loadCustomerView() method loads the customers view using FXMLLoader into
	 * new Scene .
	 */
	private void loadCustomerView() {
		// customerViewController = new CustomerViewController();
		primaryStage = new Stage();
		dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));
		try {

			// Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customerView.fxml"));
			// Inflate the view using the loader
			AnchorPane root = (AnchorPane) loader.load();
			// Set window title
			primaryStage.setTitle("Online Coffee Shop Management System");
			// Create a scene with the inflated view
			Scene scene = new Scene(root);
			// Set the scene to the stage
			primaryStage.setScene(scene);
			// Get the controller instance from the loader
			CustomerViewController controller = loader.getController();

			// Set the parent stage in the controller
			controller.setDialogStage(primaryStage);
			// Show the view
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());

		}

	}

	/**
	 * backButtonOnAction() method gives user to go back to the previous screen
	 * or login view.
	 */
	@FXML
	void backButtonOnAction() {
		loginIdTextField.clear();
		passwordIdTextField.clear();
		loginDetailsGridPane.setVisible(false);
		loginGridPane1.setVisible(true);
		buttonsGridPane2.setVisible(true);
		errorMsg.setText("");

	}

	/**
	 * customerLoginOnAction() method calls loginPanel() method to verify login
	 * credentials
	 */
	@FXML
	void customerLoginOnAction() {
		loginViewLabel.setText("Customer Login");

		roleName = "customer";
		loginPanel();
		System.out.println("customer login");
		signUpButton.setVisible(true);

	}

	/**
	 * managerLoginOnAction() method calls loginPanel() method to verify login
	 * credentials
	 */
	@FXML
	void managerLoginOnAction() {
		loginViewLabel.setText("Manager Login");
		roleName = "manager";
		signUpButton.setVisible(false);

		loginPanel();
		System.out.println("manager login");
	}

	/**
	 * adminLoginOnAction() method calls loginPanel() method to verify login
	 * credentials
	 */
	@FXML
	void adminLoginOnAction() {
		loginViewLabel.setText("Admin Login");

		roleName = "admin";
		signUpButton.setVisible(false);

		loginPanel();
		System.out.println("admin login");
	}

	/**
	 * deliveryPersonOnAction() method calls loginPanel() method to verify login
	 * credentials
	 */
	@FXML
	void deliveryPersonOnAction() {
		loginViewLabel.setText("Delivery Person Login");

		roleName = "delivery";
		signUpButton.setVisible(false);

		loginPanel();
		System.out.println("delivery person login");
	}

	/**
	 * chefOnAction() method calls loginPanel() method to verify login
	 * credentials
	 */
	@FXML
	void chefOnAction() {
		loginViewLabel.setText("Chef Login");

		roleName = "chef";
		signUpButton.setVisible(false);

		loginPanel();
		System.out.println("chef login");
	}

	/**
	 * signUpButtonOnAction() loads sign up view for customer.
	 */
	@FXML
	void signUpButtonOnAction() {
		loginGridPane1.setVisible(false);
		buttonsGridPane2.setVisible(false);
		loginDetailsGridPane.setVisible(false);
		custRegPane.setVisible(true);

	}

	/**
	 * custDataSubmitOnAction() method collect and verifies data from customer
	 * sign up form.
	 */
	@FXML
	void custDataSubmitOnAction() {
		boolean add = false;
		custErr.setText("");
		EmployeeParams employeeParams = new EmployeeParams();
		// AdminEmployeeOperationsDao adminEmployeeOperationsDao = new
		// AdminEmployeeOperationsDao();
		CustomerAddDao customerAddDao = new CustomerAddDao();
		ResultSet rs = customerAddDao.getEmployeeId();
		int test = 0;
		try {
			if (rs.next()) {
				String temp = null;
				test = Integer.parseInt(rs.getString(1));
				test = test + 1;
				employeeParams.setEmpId(Integer.toString(test));
			}
			if (!(test == 0)) {
				test = test + 10;
				employeeParams.setUserId(Integer.toString(test));
			}

			if (!(firstNamett.getText().toString()).isEmpty() && !(lastnamett.getText().toString()).isEmpty()
					&& !(userName.getText().toString()).isEmpty() && !(password.getText().toString()).isEmpty()
					&& !(contactNo.getText().toString()).isEmpty() && !(ssn.getText().toString()).isEmpty()
					&& !(email.getText().toString()).isEmpty() && !(address.getText().toString()).isEmpty()) {
				add = true;
				employeeParams.setRole("customer");
				employeeParams.setFirstName(firstNamett.getText());
				employeeParams.setLastName(lastnamett.getText());
				employeeParams.setUserName(userName.getText());
				employeeParams.setPassword(password.getText());
				if (((contactNo.getText()).length() == 10)) {
					employeeParams.setContactNo((int) Long.parseLong(contactNo.getText()));
				} else {
					add = false;
					custErr.setText("Enter valid Contact No.");
				}
				if ((ssn.getText()).length() == 9) {
					employeeParams.setSsn(Integer.parseInt(ssn.getText()));
				} else {
					add = false;
					custErr.setText("Enter valid SSN");
				}
				if (email.getText().contains("@") && email.getText().contains(".")) {
					employeeParams.setEmail(email.getText());

				} else {
					custErr.setText("Enter Valid Employee Email ID");
					add = false;
				}
				employeeParams.setAddress(address.getText());

			} else {
				custErr.setText("Enter info. in all fields");
				add = false;
			}
			/*
			 * if (email.getText().contains("@") &&
			 * email.getText().contains(".")) {
			 * employeeParams.setEmail(email.getText());
			 * 
			 * } else { custErr.setText("Enter Valid Employee Email ID"); add =
			 * false; }
			 */

			if (add == true) {
				add = customerAddDao.addCust(employeeParams);
			}
			if (add == true) {
				custErr.setText("Customer Registered");
				firstNamett.setText("");
				lastnamett.setText("");
				userName.setText("");
				password.setText("");
				contactNo.setText("");
				ssn.setText("");
				email.setText("");
				address.setText("");

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * custBackOnAction() gives customer to go back to the sign in view.
	 */
	@FXML
	void custBackOnAction() {
		custRegPane.setVisible(false);
		loginGridPane1.setVisible(false);
		buttonsGridPane2.setVisible(false);
		loginDetailsGridPane.setVisible(true);
		signUpButton.setVisible(true);
	}

	/**
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
