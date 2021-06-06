

package controlador;


import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Validaciones;
import modelo.DAO.PedidosDAO;
import modelo.DTO.DetallePedidoDto;

public class VistaVisualizarMesaController implements Initializable {

	private int mesa;


	/**
	 * @return the mesa
	 */
	public int getMesa() {
		return mesa;
	}

	/**
	 * @param mesa the mesa to set
	 */
	public void setMesa(int mesa) {
		this.mesa = mesa;
	}

	@FXML
	private ImageView btnVisualizar;

	@FXML
	private ImageView btnModificar;

	@FXML
	private ImageView btnCuenta;

	@FXML
	private ImageView btnCerrar;

	@FXML
	private AnchorPane Visor;

	@FXML
	private AnchorPane resultado;

	@FXML
	private Label lblConsTotal;

	@FXML
	private Label lblTotal;

	@FXML
	private TextField txtProd;

	@FXML
	private TextField txtCantModificar;

	@FXML
	private Label lblProducto;

	@FXML
	private Label lblCantidad;

	@FXML
	private JFXButton btnSumar;

	@FXML
	private JFXButton btnRestar;
	
	@FXML
	private Label lblFlagMesa;

    @FXML
    private JFXButton btnSalir;
    
	@FXML
	private TableView<DetallePedidoDto> tblPedidos;    

	@FXML
	private TableColumn<DetallePedidoDto, Integer> colIdPed;

	@FXML
	private TableColumn<DetallePedidoDto, String> colNomArt;

	@FXML
	private TableColumn<DetallePedidoDto, Integer> colCantidad;

	@FXML
	private TableColumn<DetallePedidoDto, Double> colPrecio;

	@FXML
	private TableColumn<DetallePedidoDto, Integer> colTotal;

	@FXML
	private TableColumn<DetallePedidoDto, String> colEmpleado;

	private ObservableList<DetallePedidoDto> pedidos;
	
	/**
	 * Reduce la cantidad a modificar en 1 no pudiendo ser inferior a 0
	 * @param event
	 */
	@FXML
	void restar(MouseEvent event) {
		int cantNow = 0;

		cantNow = Integer.parseInt(txtCantModificar.getText());

		if (cantNow > 0 ) {
			txtCantModificar.setText(String.valueOf(cantNow - 1));
		}else {
			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error de cantidad");
			alert.setContentText("No se pueden introducir números menores que 0");
			alert.showAndWait();
		}
	}

	/**
	 * Aumenta la cantidad a modificar en 1
	 * @param event
	 */
	@FXML
	void sumar(MouseEvent event) {
		int cantNow = 0;

		cantNow = Integer.parseInt(txtCantModificar.getText());
		if (cantNow >= 0 ) {
			txtCantModificar.setText(String.valueOf(cantNow + 1));
		}else {
			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error de cantidad");
			alert.setContentText("No se pueden introducir números menores que 0");
			alert.showAndWait();
		}
	}

	/**
	 * Selecciona el pedido realizado anteriormente y permite su modificación
	 * @param event
	 */
	@FXML
	private void seleccionPedido(MouseEvent event) {
		DetallePedidoDto articulo = this.tblPedidos.getSelectionModel().getSelectedItem();
		//System.out.println(articulo.getArticulo());
		if(articulo != null) {
			this.lblProducto.setText(articulo.getNombreArt());
			this.lblCantidad.setText(String.valueOf(articulo.getCantidad()));
			System.out.println(articulo.getNombreArt());
		}
	}
	

	/**
	 * Modifica en la base de datos un pedido anteriormente seleccionado y muestra los cambios en la tabla
	 * @param event
	 */
	@FXML
	void modificarMesa(MouseEvent event) {

		DetallePedidoDto pedido = this.tblPedidos.getSelectionModel().getSelectedItem();
		System.out.println("En modificar: idPedido - "+ pedido.getIdPedido() + "mesa - "+ pedido.getMesa() + "nombre articulo - "+ pedido.getNombreArt());

		if (Validaciones.isNumPos(txtCantModificar.getText().trim()) == -1) {

			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Modificación");
			alert.setContentText("Debe haber una cantidad");
			alert.showAndWait();

		}else {
			
			int modificacion = Integer.parseInt(txtCantModificar.getText());

			if (pedido != null) {
				String respuesta = PedidosDAO.modificaPedido(pedido.getIdPedido(), modificacion);

				if(respuesta.equals("Modificado correctamente")) {

					//Recargamos con los nuevos datos generados

					pedidos = FXCollections.observableArrayList();
					setMesa(VentanaMesaController.getMesa());
					pedidos.addAll(PedidosDAO.CargaPedidos(mesa));
					this.tblPedidos.setItems(pedidos);
					tblPedidos.refresh();
					calcularTotales();
					this.txtCantModificar.setText("");
					this.lblCantidad.setText("");
					this.lblProducto.setText("");

					//Avisamos de que se ha producido el cambio

					Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
					alert.setHeaderText(null);
					alert.setTitle("Modificación");
					alert.setContentText("Se ha modificado la cantidad");
					alert.showAndWait();
					this.txtCantModificar.setText("");

				}else if (respuesta.equals("No se ha podido modificar")) {
					Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
					alert.setHeaderText(null);
					alert.setTitle("Modificación");
					alert.setContentText(respuesta);
					alert.showAndWait();
					this.txtCantModificar.setText("");

				}else {
					Alert alert = new Alert (Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Modificación");
					alert.setContentText("Debe tener algun producto seleccionado");
					alert.showAndWait();
				}
			}
		}		
	}
	
	/**
	 * Genera el ticket de venta y lo manda imprimir
	 */
	public static void printCuenta() {
		
		/*Pendiente de preparar la impresión implementando printable en otro fxml*/
	
	}

	/**
	 * Calcula los totales a mostrar en la interfaz
	 */
	void calcularTotales() {
		double totalFactura=0;
		int totalConsumiciones=0;

		for (int i= 0;i<tblPedidos.getItems().size();i++){
			totalFactura += Double.valueOf(String.valueOf(tblPedidos.getColumns().get(4).getCellObservableValue(i).getValue()));
			totalConsumiciones += Integer.valueOf(String.valueOf(tblPedidos.getColumns().get(2).getCellObservableValue(i).getValue()));
		}
		System.out.println(totalConsumiciones);
		lblTotal.setText(String.valueOf(totalFactura));
	}

	
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {

		this.colIdPed.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
		this.colNomArt.setCellValueFactory(new PropertyValueFactory<>("nombreArt"));
		this.colCantidad.setCellValueFactory((new PropertyValueFactory<>("cantidad")));
		this.colPrecio.setCellValueFactory((new PropertyValueFactory<>("precio")));
		this.colTotal.setCellValueFactory((new PropertyValueFactory<>("total")));
		this.colEmpleado.setCellValueFactory((new PropertyValueFactory<>("empleado")));

		pedidos = FXCollections.observableArrayList();
		setMesa(VentanaMesaController.getMesa());
		pedidos.addAll(PedidosDAO.CargaPedidos(mesa));
		this.tblPedidos.setItems(pedidos);
		calcularTotales();
		txtCantModificar.setText(String.valueOf(0));
		setMesa(VentanaMesaController.getMesa());
		lblFlagMesa.setText("Mesa: "+VentanaMesaController.getMesa());	
	}

}
