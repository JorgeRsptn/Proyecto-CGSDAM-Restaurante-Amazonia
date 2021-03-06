package controlador;


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.DAO.InformesCierresDAO;

public class VistaGerenteController {

	@FXML
	private AnchorPane tnCaja;
	
	@FXML
	private AnchorPane anchBaseGerente;

	@FXML
	private ImageView imgCierre;

	@FXML
	private AnchorPane btnArqueo;

	@FXML
	private ImageView imgCierre1;

	@FXML
	private AnchorPane btnEstadisticas;

	@FXML
	private ImageView imgCierre11;

	@FXML
	private AnchorPane btnPedidos;

	@FXML
	private ImageView imgCierre111;

	@FXML
	private AnchorPane btnRoturaStock;

	@FXML
	private ImageView imgCierre1111;

	@FXML
	private AnchorPane btnSalir;

	@FXML
	private ImageView imgCierre11111;

	@FXML
	void SalirGerente(MouseEvent event) {
		try {
			Stage stage = (Stage) btnSalir.getScene().getWindow();
			stage.close();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Vista/LoginUI.fxml"));
			Parent root1;
			root1 = (Parent) fxmlLoader.load();
			stage = new Stage();

			stage.setScene(new Scene(root1));
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void arqueo(MouseEvent event) {

		mostrarVentanaNueva("../vista/VistaResultados.fxml");
	}

	@FXML
	void cierre(MouseEvent event) {
		
		if(InformesCierresDAO.comprobarCierreCaja().equals("Todo pagado")){
			mostrarVentanaNueva("VistaResultados.fxml");
			String respuesta = InformesCierresDAO.CierreCaja();
			if (respuesta.equals("Caja Cerrada")) {
				Alert alert = new Alert (Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Caja Cerrada");
				alert.setContentText("Se cerr? la facturaci?n del d?a");
				alert.showAndWait();
			}else {
				Alert alert = new Alert (Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error al cerrar la caja");
				alert.setContentText("NO se cerr? la facturaci?n del d?a");
				alert.showAndWait();
			}
		}else {
			Alert alert = new Alert (Alert.AlertType.WARNING);
			alert.setHeaderText(null);
			alert.setTitle("No se pudo cerrar caja");
			alert.setContentText("Faltan mesas por cerrar");
			alert.showAndWait();
		}



	}

	@FXML
	void estadisticasGral(MouseEvent event) {
		mostrarVentanaNueva("../vista/VistaInfoGral.fxml");
	}

	@FXML
	void pedidosProv(MouseEvent event) {

	}

	@FXML
	void roturaStock(MouseEvent event) {

	}

	private void mostrarVentanaNueva(String ventana){

		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource(ventana));
			Parent root = loader.load();						
			Scene scene = new Scene(root);
			Stage stage = new Stage();			
			stage.initModality(Modality.APPLICATION_MODAL);			
			stage.setScene(scene);
			stage.showAndWait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
