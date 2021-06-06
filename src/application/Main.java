package application;
	
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../vista/LoginUI.fxml"));
			//Buscamos el tamaño de la pantalla para ocuparla completamente con nuestra scene.
			Toolkit t = Toolkit.getDefaultToolkit();
			Dimension screenSize = t.getScreenSize();
			Scene scene = new Scene(root,(screenSize.getWidth()),(screenSize.getHeight()*0.9));
			//Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("../vista/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
