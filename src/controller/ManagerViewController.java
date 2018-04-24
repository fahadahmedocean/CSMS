/**
 * ManagerViewController.java contains methods 
 * which helps Manager to view the placed order list.
 * Add/Update/Delete/View inventory information.
 * Update his/her profile information.
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
import model.GetInventoryDataDao;
import model.GetOrderLogDao;
import service.InventoryParams;

/**
 * @author dark_fahad
 *
 */
public class ManagerViewController implements Initializable {

	@FXML
	private Pane managerViewPane, managerOperationsPane, inventoryPane;
	@FXML
	private TableView orderLogsTableView, inventoryTableView;
	@FXML
	private TextField deleteInventoryTextFeild, addItemID, addItemName, addItemQuantity, updateItemID,
			updateItemQuantity;
	@FXML
	private Label errorLabel, errorAddInventory, errorUpdateInventory;

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
		managerViewPane.setVisible(true);
		managerOperationsPane.setVisible(false);
		inventoryPane.setVisible(false);
	}

	/**
	 * viewOrderLogsOnAction() methods loads the order logs into the table view.
	 */
	@FXML
	public void viewOrderLogsOnAction() {
		if (!(orderLogsTableView.getItems() == null) && !(data == null)) {
			orderLogsTableView.getItems().clear();
			orderLogsTableView.getColumns().clear();
			data.clear();
		}
		managerViewPane.setVisible(false);
		managerOperationsPane.setVisible(true);
		inventoryPane.setVisible(false);

		data = FXCollections.observableArrayList();
		GetOrderLogDao getOrderLogDao = new GetOrderLogDao();
		ResultSet rs = getOrderLogDao.getOrderLog();
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

				orderLogsTableView.getColumns().addAll(col);
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
			orderLogsTableView.setItems(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * inventoryOnAction() loads the inventory items in to the table view.
	 */
	@FXML
	public void inventoryOnAction() {
		if (!(inventoryTableView.getItems() == null) && !(data == null)) {
			inventoryTableView.getItems().clear();
			inventoryTableView.getColumns().clear();
			data.clear();
		}
		managerViewPane.setVisible(false);
		managerOperationsPane.setVisible(false);
		inventoryPane.setVisible(true);

		GetInventoryDataDao getInventoryDataDao = new GetInventoryDataDao();
		ResultSet rs = getInventoryDataDao.getInventoryData();

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

				inventoryTableView.getColumns().addAll(col);
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
			inventoryTableView.setItems(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * backOnAction() gives manager to load the previous screen.
	 */
	@FXML
	public void backOnAction() {
		managerViewPane.setVisible(true);
		managerOperationsPane.setVisible(false);
		inventoryPane.setVisible(false);

	}

	/**
	 * updateInformation() method loads the manager update profile view using
	 * FXMLLoader into new Scene .
	 */
	@FXML
	public void updateInformation() {
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
	 * deleteInvOnAction() method deletes inventory items.
	 */
	@FXML
	public void deleteInvOnAction() {
		errorLabel.setText("");
		GetInventoryDataDao getInventoryDataDao = new GetInventoryDataDao();
		if (!(deleteInventoryTextFeild.getText().isEmpty())) {
			getInventoryDataDao.deleteInventoryData(Integer.parseInt(deleteInventoryTextFeild.getText()));
		} else {
			errorLabel.setText("Enter Inventory item Id");
		}
		inventoryOnAction();
		deleteInventoryTextFeild.setText("");
	}

	/**
	 * addItemOnAction() method adds inventory item.
	 */
	@FXML
	void addItemOnAction() {

		boolean check = false;
		errorAddInventory.setText("");
		InventoryParams inventoryParams = new InventoryParams();
		GetInventoryDataDao getInventoryDataDao = new GetInventoryDataDao();
		if (!(addItemQuantity.getText().isEmpty()) && !(addItemName.getText().isEmpty())
				&& !(addItemID.getText().isEmpty())) {
			inventoryParams.setItemId(addItemID.getText());
			inventoryParams.setItemName(addItemName.getText());
			inventoryParams.setItemQuantity(Integer.parseInt(addItemQuantity.getText()));
			getInventoryDataDao.addInventoryData(inventoryParams);
		} else {
			check = true;
			errorAddInventory.setText("Enter all Inventory item details");
		}
		inventoryOnAction();
		// if(check){
		addItemQuantity.setText("");
		addItemName.setText("");
		addItemID.setText("");
		// }
	}

	/**
	 * updateItemOnAction() method updates inventory items.
	 */
	@FXML
	void updateItemOnAction() {
		errorUpdateInventory.setText("");
		InventoryParams inventoryParams = new InventoryParams();
		GetInventoryDataDao getInventoryDataDao = new GetInventoryDataDao();
		if (!(updateItemID.getText().isEmpty()) && !(updateItemQuantity.getText().isEmpty())) {
			inventoryParams.setItemId(updateItemID.getText());
			inventoryParams.setItemQuantity(Integer.parseInt(updateItemQuantity.getText()));
			getInventoryDataDao.updateInventory(inventoryParams);
		} else {
			errorUpdateInventory.setText("Enter Inventory item details to be updated");
		}
		inventoryOnAction();
		updateItemID.setText("");
		updateItemQuantity.setText("");

	}

	/**
	 * @param primaryStage
	 */
	public void setDialogStage(Stage primaryStage) {
		this.dialogStage = primaryStage;
	}

}
