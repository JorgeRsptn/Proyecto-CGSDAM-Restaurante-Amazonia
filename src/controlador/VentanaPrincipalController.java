package controlador;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Logica;

public class VentanaPrincipalController {

	Logica miLogica = new Logica();


	private static int mesa;
	
	@FXML
	private JFXButton btnReg;

	@FXML
	private JFXTextField ppalUser;

	@FXML
	private JFXPasswordField ppalPass;

	@FXML
	private ImageView mesa1;

	@FXML
	private ImageView mesa2;

	@FXML
	private ImageView mesa3;

	@FXML
	private ImageView mesa4;
	
	@FXML
	private ImageView mesa5;
	
	@FXML
	private ImageView mesa6;
	
	@FXML
	private ImageView mesa7;
	
	@FXML
	private ImageView mesa8;
	
	@FXML
	private ImageView mesa9;	

    @FXML
    private JFXButton btnSalirPpal;

    //*************************/
	

/**
	 * @return the mesa
	 */
	public static int getMesa() {
		return mesa;
	}


	/**
	 * @param mesa the mesa to set
	 */
	public static void setMesa(int nMesa) {
		mesa = nMesa;
	}

	
	/**
	 * Recibe el botón que ha sido pulsado y abre una ventana modal que efectuara los pedidos 
	 * y modificaciones sobre la mesa escogida.
	 * 
	 * @param event
	 */
	@FXML
	void irMesa(MouseEvent event) {
		int mesa=0;
		ImageView evento = (ImageView) event.getSource();

		switch(evento.getId()) {
		case "mesa1":
			mesa=1;
			break;
		case "mesa2":
			mesa=2;
			break;
		case "mesa3":
			mesa=3;
			break;
		case "mesa4":
			mesa=4;
			break;
		case "mesa5":
			mesa=5;
			break;
		case "mesa6":
			mesa=6;
			break;
		case "mesa7":
			mesa=7;
			break;
		case "mesa8":
			mesa=8;
			break;
		case "mesa9":
			mesa=9;
			break;
		default:

		} 
		
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource("../vista/VentanaMesa.fxml"));

			Parent root = loader.load();
			
			VentanaMesaController controlador = loader.getController();			
			controlador.setMesa(mesa);
			setMesa(mesa);
			
			Toolkit t = Toolkit.getDefaultToolkit();
			Dimension screenSize = t.getScreenSize();
			
			Scene scene = new Scene(root,(screenSize.getWidth()),(screenSize.getHeight()*0.9));	
			scene.getStylesheets().add(getClass().getResource("../vista/application.css").toExternalForm());
			Stage stage = new Stage();			
			//stage.initModality(Modality.APPLICATION_MODAL);			
			stage.setScene(scene);
			stage.show(); 

		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	

    @FXML
    void salirApp(MouseEvent event) {
    	Stage stage = (Stage) btnSalirPpal.getScene().getWindow();
		stage.close();         
    }

   
}
