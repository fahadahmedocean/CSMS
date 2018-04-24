/**
 * CustomerViewController.java contains methods 
 * which helps customer to SignUp and
 * Customer can add items to the cart and place order.
 * Update his/her profile information.
 * Delete his/her account.
 */
package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.AdminEmployeeOperationsDao;
import model.CustomerViewDao;
import model.GetUserIdDao;
import model.InsertOrderDao;
import model.MenuItemDetailsDao;
import service.OrderParams;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

/**
 * @author dark_fahad
 *
 */
public class CustomerViewController implements Initializable, GiftCard {
	@FXML
	private Pane orderViewPane, customerViewPane, confrimationPane;
	@FXML
	private Label wellomeLabel, orderCartLabel, totalLabel, errorLabel, checkTotalError, cartItemsLabel,
			totalItemsFinalLabel, nameLabel, addressLabel, giftCard;
	@FXML
	private HBox menuListHBox;
	@FXML
	private Button editProfileButton, logoutButton, addToCartButton, checkTotalButton, placeOrderButton;
	@FXML
	private TextField itemIdTextFeild;
	private Stage dialogStage, primaryStage, updateStage;
	private CustomerViewDao customerViewDao;
	private ObservableList<ObservableList> data;
	@FXML
	private TableView tableView;
	private Properties prop = new Properties();
	private InputStream input = null;
	private String user;
	private String address, empId;
	private MenuItemDetailsDao menuItemDetailsDao;
	private InsertOrderDao insertOrderDao;
	private HashMap<String, String> hmap;
	private ArrayList menuitemsPrice;
	private ArrayList menuitemsNames;
	private double total = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		giftCard.setVisible(false);
		customerViewPane.setVisible(true);
		orderViewPane.setVisible(false);
		confrimationPane.setVisible(false);

		loadItemsData();
		hmap = new HashMap<String, String>();
		menuitemsPrice = new ArrayList<>();
		menuitemsNames = new ArrayList<>();
	}

	public CustomerViewController() {
	}

	/**
	 * editProfileButtonOnAction() method loads the customer update profile view
	 * using FXMLLoader into new Scene .
	 */
	@FXML
	void editProfileButtonOnAction() {
		updateStage = new Stage();
		// dialogStage.fireEvent(new
		// WindowEvent(dialogStage,WindowEvent.WINDOW_CLOSE_REQUEST));
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
	 * logoutButtonOnAction() method loads the logout view using FXMLLoader into
	 * new Scene .
	 */
	@FXML
	void logoutButtonOnAction() {
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
	 * deleteProfileButtonOnAction() method deletes customers record from the
	 * database.
	 */
	@FXML
	void deleteProfileButtonOnAction() {

		AdminEmployeeOperationsDao adminEmployeeOperationsDao = new AdminEmployeeOperationsDao();
		adminEmployeeOperationsDao.deleteParentOrders(Integer.parseInt(empId));
		adminEmployeeOperationsDao.deleteParent(Integer.parseInt(empId));
		adminEmployeeOperationsDao.deleteEmployee(Integer.parseInt(empId));

		try {
			// primaryStage.close();
			primaryStage = new Stage();
			dialogStage.fireEvent(new WindowEvent(dialogStage, WindowEvent.WINDOW_CLOSE_REQUEST));

			// Create a loader for the UI components
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteCustomer.fxml"));
			// Inflate the view using the loader
			AnchorPane root = (AnchorPane) loader.load();
			// Set window title
			primaryStage.setTitle("Online Coffee Shop Management System");
			// Create a scene with the inflated view
			Scene scene = new Scene(root);
			// Set the scene to the stage
			primaryStage.setScene(scene);
			// Get the controller instance from the loader
			DeleteCustomerProfile controller = loader.getController();
			// Set the parent stage in the controller
			controller.setDialogStage(primaryStage);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

	/**
	 * deleteProfileButtonOnAction() method add menu items into the cart.
	 */
	@FXML
	void addToCartButtonOnAction() {
		try {
			errorLabel.setText("");
			menuItemDetailsDao = new MenuItemDetailsDao();
			ResultSet rs = menuItemDetailsDao.getMenuItemDetails(itemIdTextFeild.getText().toString());
			if (rs.next()) {

				hmap.put(rs.getString(3), rs.getString(4));
				System.out.println();
				menuitemsPrice.add(rs.getString(4));
				menuitemsNames.add(rs.getString(3));

				itemIdTextFeild.setText("");

			} else {
				errorLabel.setText("Enter valid Menu Item ID");
			}
			orderCartLabel.setText(menuitemsNames.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * clearCartButtonOnAction() clears cart menu items.
	 */
	@FXML
	void clearCartButtonOnAction() {
		clearOrder();

	}

	private void clearOrder() {
		hmap.clear();
		menuitemsPrice.clear();
		menuitemsNames.clear();
		orderCartLabel.setText("");

		totalLabel.setText("");
		checkTotalError.setText("");
		total = 0;
	}

	/**
	 * checkTotalOnAction() calculate the total amount of menu items which are
	 * in cart .
	 */
	@FXML
	void checkTotalOnAction() {
		totalLabel.setText("");
		checkTotalError.setText("");
		if (!menuitemsPrice.isEmpty()) {

			Iterator<String> itr = menuitemsPrice.iterator();
			while (itr.hasNext()) {
				double element = Double.parseDouble(itr.next());
				total = total + element;
			}

			totalLabel.setText("$" + total);
		} else {
			checkTotalError.setText("Enter Menu Item to Cart");
		}

	}

	/**
	 * placeOrderOnAction() method helps customer to place order of menu items
	 * which are added into the cart.
	 */
	@FXML
	void placeOrderOnAction() {
		if(total == 0){
		Iterator<String> itr = menuitemsPrice.iterator();
		while (itr.hasNext()) {
			double element = Double.parseDouble(itr.next());
			total = total + element;
		}}
		confrimationPane.setVisible(false);
		checkTotalError.setText("");
		// checkTotalOnAction();

		if (!menuitemsPrice.isEmpty()) {
			customerViewPane.setVisible(false);
			orderViewPane.setVisible(true);

			cartItemsLabel.setText(menuitemsNames.toString());
			totalItemsFinalLabel.setText("$" + total);

			nameLabel.setText(user);
			addressLabel.setText(address);

		} else {
			checkTotalError.setText("Enter Menu Item to Cart");

		}
	}

	/**
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

	/**
	 * loads menu items into the table view.
	 */
	public void loadItemsData() {
		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("user"));
			user = prop.getProperty("user");
			address = prop.getProperty("address");
			empId = prop.getProperty("userEMPId");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		wellomeLabel.setText("Welcome " + user + ".");
		// menuListPane = new Pane();
		customerViewDao = new CustomerViewDao();
		ResultSet rs = customerViewDao.getMenuItem();
		data = FXCollections.observableArrayList();

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

				tableView.getColumns().addAll(col);
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
			// loads observableArrayList object containing menu items into the
			// table view
			tableView.setItems(data);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * cancelOrderOnAction() helps customer to cancel the order.
	 */
	@FXML
	void cancelOrderOnAction() {
		customerViewPane.setVisible(true);
		orderViewPane.setVisible(false);
		confrimationPane.setVisible(false);
		clearOrder();
	}

	/**
	 * confirmOrderOnAction() method confirms the order.
	 */
	@FXML
	void confirmOrderOnAction() {

		if (checkOrderAmount()) {
			giftCard.setVisible(true);

		}
		confrimationPane.setVisible(true);
		orderViewPane.setVisible(false);
		customerViewPane.setVisible(false);
		GetUserIdDao getUserIdDao = new GetUserIdDao();
		insertOrderDao = new InsertOrderDao();
		OrderParams orderParams = new OrderParams();
		orderParams.setUserid(getUserIdDao.getMenuItem(empId));
		orderParams.setOrderPrice(total);
		orderParams.setOrderItems(menuitemsNames.toString());

		insertOrderDao.insertsOrder(orderParams);

	}

	/**
	 * placeNewOrderOnAction() method helps customer to place a new order.
	 */
	@FXML
	void placeNewOrderOnAction() {
		customerViewPane.setVisible(true);
		orderViewPane.setVisible(false);
		confrimationPane.setVisible(false);

		total = 0;
		clearOrder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.GiftCard#checkOrderAmount()
	 */
	@Override
	public Boolean checkOrderAmount() {
		boolean check = false;
		if (total > 50) {
			check = true;
		}
		return check;
	}

}
