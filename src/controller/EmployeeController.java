/**
 * EmployeeController.java contains methods 
 * which helps delivery person and chef to view
 * the upcoming orders and update profile.
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.GetOrderLogDao;

/**
 * @author dark_fahad
 *
 */
public class EmployeeController implements Initializable {

	@FXML
	private Pane employeeViewPane, EmployeeOperationsPane;
	@FXML
	private TableView orderLogsTableView;
	private Stage primaryStage, dialogStage, updateStage;
	private ObservableList<ObservableList> data;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		employeeViewPane.setVisible(true);
		EmployeeOperationsPane.setVisible(false);
	}

	/**
	 * viewOrderLogsOnAction() loads the order logs records into the table view.
	 */
	@FXML
	void viewOrderLogsOnAction() {
		employeeViewPane.setVisible(false);
		EmployeeOperationsPane.setVisible(true);

		if (!(orderLogsTableView.getItems() == null) && !(data == null)) {
			orderLogsTableView.getItems().clear();
			orderLogsTableView.getColumns().clear();
			data.clear();
		}

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
	 * updateInformation() method loads the employee update profile view using
	 * FXMLLoader into new Scene .
	 */
	@FXML
	void updateInformation() {
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
	 * backOnAction() method gives employee to switch to the previous screen.
	 */
	@FXML
	void backOnAction() {
		employeeViewPane.setVisible(true);
		EmployeeOperationsPane.setVisible(false);
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
	 * @param primaryStage
	 */
	public void setDialogStage(Stage primaryStage) {
		this.dialogStage = primaryStage;
	}

}
