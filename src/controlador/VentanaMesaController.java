package controlador;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.DAO.PedidosDAO;

public class VentanaMesaController {


	@FXML
	private AnchorPane baseMesas;

	@FXML
	private AnchorPane bordePantalla;

	@FXML
	private AnchorPane pantalla;

	@FXML
	private AnchorPane anchorCerrarMesa;

	@FXML
	private Label lblPedir;

	@FXML
	private AnchorPane anchorSalir;

	@FXML
	private Label lblPedir2;

	@FXML
	private AnchorPane anchorPedir;

	@FXML
	private Label lblPedir1;

	@FXML
	private AnchorPane anchorCuenta;

	@FXML
	private Label lblPedir3;

	@FXML
	private AnchorPane anchorVisualizar;

	@FXML
	private Label lblVisualizar;

	@FXML
	private AnchorPane anchMesa;

	@FXML
	private Label lblMesa;

	static int mesa;

	/**
	 * @return the mesa
	 */
	public static int getMesa() {
		return mesa;
	}

	/**
	 * @param mesa the mesa to set
	 */
	public void setMesa(int mesaEnv) {
		mesa = mesaEnv;
	}

	/**
	 * Abre una ventana en un pane de la ventana padre que nos muestra el estado actual de los pedidos pendientes de pagar y permite hacer modificaciones sobre estos.
	 * @param event
	 */
	@FXML
	void estadoMesa(MouseEvent event) {
		Pane newLoadedPane = null;
		System.out.println("En ventana de mesa visualizar: " + mesa);
		pantalla.getChildren().clear();
		try {			
			newLoadedPane = FXMLLoader.load(getClass().getResource("../vista/VistaVisualizarMesa.fxml"));
			pantalla.getChildren().add(newLoadedPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Abre una ventana en un pane de la ventana padre que nos muestra los productos para consumir y realizar los pedidos
	 * @param event
	 */
	@FXML
	void verProductos(MouseEvent event) {
		System.out.println("En ventana de mesa : " + mesa);
		
		Pane newLoadedPane = null;
		pantalla.getChildren().clear();
		try {
			newLoadedPane = FXMLLoader.load(getClass().getResource("../vista/VistaProdPedir.fxml"));
			pantalla.getChildren().add(newLoadedPane);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
/**
 * Establece como pagado el total de los pedidos de la mesa sobre la que se esté trabajando
 * @param event
 */
	@FXML
	void cerrarMesa(MouseEvent event) {
		String respuesta = PedidosDAO.pagar(mesa);

		if(respuesta.equals("Modificado correctamente")) {

			Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Pagado");
			alert.setContentText("Se cerro la mesa.");
			alert.showAndWait();
			
		}else if (respuesta.equals("No se ha podido modificar")) {
			
			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No se pudo cerrar la mesa");
			alert.showAndWait();
		}
	}
	
	@FXML
	void cerrarPantalla(MouseEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource("../vista/VentanaPrincipal.fxml"));

			Parent root = loader.load();			
			Toolkit t = Toolkit.getDefaultToolkit();
			Dimension screenSize = t.getScreenSize();
			
			
			Scene scene = new Scene(root,(screenSize.getWidth()),(screenSize.getHeight()*0.9));	
			scene.getStylesheets().add(getClass().getResource("../vista/application.css").toExternalForm());
			Stage stage = new Stage();						
			stage.setScene(scene);
			stage.showAndWait(); 
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
