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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Logica;
import modelo.Validaciones;

public class LoginUIController {

	Validaciones validacion;
	Logica miLogica;

	@FXML
	private AnchorPane baseLogin;

	@FXML
	private JFXTextField txtUserLoggin;

	@FXML
	private JFXPasswordField txtpassLoggin;

	@FXML
	private JFXButton btnEntrar;

	@FXML
	private PasswordField passpruebafx;
	/**
	 * 
	 * @param event
	 * 
	 * Método que pide la validación de los datos introducidos por el usuario y comprueba si son correctos para permitir el acceso. 
	 * 
	 * Llama al método que lanza las nuevas ventanas.
	 */
	
	@FXML
	void entrarLoggin(ActionEvent event) {

		validacion=new Validaciones();
		miLogica=new Logica();
		
		String user = txtUserLoggin.getText().trim();
		String pass = miLogica.encriptPassSHA1(txtpassLoggin.getText().trim());
		limpiarDatos();

		System.out.println("Usuario = "+user+" Pass = "+pass);
		String respuesta = validacion.entradasLogin(user, pass);
		System.out.println(respuesta);

		if(respuesta.equals("valido")) {

			respuesta=miLogica.validarIngreso(user, pass);

		}else {
			// Mensaje de error en la validación 

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error en la validación");
			alert.setHeaderText("Error en los datos introducidos");//Puede ir en null y queda más pequeño
			alert.setContentText(respuesta);
			alert.showAndWait();

		}

		if (respuesta.equals("admitido")) {
			
			if (user.equals("Gerente")) {
				accesoVentanaPpal(1);
			} else if(user.equals("Administrador") )  {
				accesoVentanaPpal(3);
			}else {
				accesoVentanaPpal(2);
			}
			
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error en la validación");
			alert.setHeaderText("Error en los datos introducidos");//Puede ir en null y queda más pequeño
			alert.setContentText(respuesta);
			alert.showAndWait();
		}
	}

/**
 * Método para cargar las nuevas ventanas dependiendo del valor recibido por parametros.
 * 
 * @param usuario
 * 
 * 
 */
	void accesoVentanaPpal(int usuario) {
			String ventana = "";
			
			switch(usuario) {
			case 1 :
				ventana = "../vista/VistaGerente.fxml";
				break;
			case 2 :
				ventana = "../vista/VentanaPrincipal.fxml";
				break;
			case 3 : 
				ventana = "../vista/VistaAdmin.fxml";
				break;			
			}
			
		
		try {

			Stage stage = (Stage) btnEntrar.getScene().getWindow();
			stage.close();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ventana));
			Parent root1;
			root1 = (Parent) fxmlLoader.load();
			stage = new Stage();
			Toolkit t = Toolkit.getDefaultToolkit();
			Dimension screenSize = t.getScreenSize();
			Scene scene = new Scene(root1,(screenSize.getWidth()),(screenSize.getHeight()*0.9));
			//Scene scene = new Scene(root1);
			scene.getStylesheets().add(getClass().getResource("../vista/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void limpiarDatos() {
		txtpassLoggin.setText("");
		txtUserLoggin.setText("");
	}

}