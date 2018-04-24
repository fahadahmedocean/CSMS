/**
 * Main.java loads the coffee shop management application.
 */
package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author dark_fahad
 *
 */
public class Main extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
     Application.launch(args);
	}
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * loads the coffee shop management application.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/start.fxml"));
		//Inflate the view using the loader
        AnchorPane root = (AnchorPane) loader.load();
        //Set window title
        //Create a scene with the inflated view
        Scene scene = new Scene(root);
        loader.getController();
        //Set the scene to the stage
        primaryStage.setScene(scene);
        //Get the controller instance from the loader
        LoginController controller = loader.getController();
        //Set the parent stage in the controller
        controller.setDialogStage(primaryStage);
        //Show the view
        primaryStage.show();
	}

}
