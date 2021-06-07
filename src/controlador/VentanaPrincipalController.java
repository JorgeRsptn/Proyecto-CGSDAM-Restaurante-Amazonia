package controlador;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Logica;
import modelo.DAO.UsuarioDAO;

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

		System.out.println(evento.getId());
		switch(evento.getId()) {
		case "mesa1":
			System.out.println("Mesa1");
			mesa=1;
			break;
		case "mesa2":
			System.out.println("Mesa2");
			mesa=2;
			break;
		case "mesa3":
			System.out.println("Mesa3");
			mesa=3;
			break;
		case "mesa4":
			System.out.println("Mesa4");
			mesa=4;
			break;
		case "mesa5":
			System.out.println("Mesa5");
			mesa=5;
			break;
		case "mesa6":
			System.out.println("Mesa6");
			mesa=6;
			break;
		case "mesa7":
			System.out.println("Mesa7");
			mesa=7;
			break;
		case "mesa8":
			System.out.println("Mesa8");
			mesa=8;
			break;
		case "mesa9":
			System.out.println("Mesa9");
			mesa=9;
			break;
		default:
			System.out.println("Otra Cosa");
		}
		System.out.println("Número recibido: " + mesa);
		
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
			
		/*	 Stage stage = new Stage();
			    Parent root = FXMLLoader.load( VentanaPrincipalController.class.getResource("VentanaMesa.fxml"));
			    
			    stage.setTitle("My modal window");
			    stage.initModality(Modality.WINDOW_MODAL);
			    stage.initOwner(
			        ((Node)event.getSource()).getScene().getWindow() );
			    stage.setScene(new Scene(root));
			    stage.showAndWait();
			*/

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
