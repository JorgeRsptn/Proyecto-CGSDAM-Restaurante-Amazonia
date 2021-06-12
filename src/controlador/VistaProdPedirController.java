package controlador;



import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Validaciones;
import modelo.DAO.DaoPresentaProd;
import modelo.DAO.PedidosDAO;
import modelo.DTO.PedidoDto;
import modelo.DTO.ProductoDto;


public class VistaProdPedirController implements Initializable {

	@FXML
	private Button btnModificarPr;

	@FXML
	private Tab tabComida;

	@FXML
	private TableView<ProductoDto> tblComida;

	@FXML
	private TableColumn<ProductoDto, String> colComNombre;

	@FXML
	private TableColumn<ProductoDto, Double> colComPrecio;

	@FXML
	private TableColumn<ProductoDto, Integer> colComId;

	@FXML
	private TableColumn<ProductoDto, Integer> colComStock;

	@FXML
	private Tab tabBebida;

	@FXML
	private Label lblmostrar;

	@FXML
	private Label lblFlagMesa;

	@FXML
	private JFXTextField txtCantidad;

	@FXML
	private JFXButton btnPedirSumar;

	@FXML
	private JFXButton btnPedirRestar;

	@FXML
	private TableView<ProductoDto> tblBebida; 

	@FXML
	private TableColumn<ProductoDto, String> colBebNom;

	@FXML
	private TableColumn<ProductoDto, Double> colBebPrec;

	@FXML
	private TableColumn<ProductoDto, Integer> colBebStock;

	@FXML
	private TableColumn<ProductoDto, Integer> colBebId;


	private ObservableList<ProductoDto> bebida;

	private ObservableList<ProductoDto> comida;

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

	private ProductoDto articulo;


	@FXML
	void restar(MouseEvent event) {
		int cantNow = 0;

		cantNow = Integer.parseInt(txtCantidad.getText());

		if (cantNow > 0 ) {
			txtCantidad.setText(String.valueOf(cantNow - 1));
		}else {
			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error de cantidad");
			alert.setContentText("No se pueden introducir números menores que 0");
			alert.showAndWait();
		}
	}

	@FXML
	void sumar(MouseEvent event) {
		int cantNow = 0;

		cantNow = Integer.parseInt(txtCantidad.getText());

		if (cantNow >= 0 ) {
			txtCantidad.setText(String.valueOf(cantNow + 1));
		}else {
			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error de cantidad");
			alert.setContentText("No se pueden introducir números menores que 0");
			alert.showAndWait();
		}
	}

	@FXML
	void pedir(MouseEvent event) {
		String cantidad = txtCantidad.getText().trim();

		if(cantidad.equals("")) {
			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Debes introducir una cantidad superior a 0.");
			alert.showAndWait();

		}else if (cantidad.equals(null)){
			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Debes introducir una cantidad superior a 0.");
			alert.showAndWait();

		}else if (Validaciones.isNumPos(cantidad) == -1) {
			Alert alert = new Alert (Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Debes introducir una cantidad superior a 0.");
			alert.showAndWait();			

		} else {

			int cantidadPedida =Integer.parseInt(txtCantidad.getText());

			if (cantidadPedida >= articulo.getStock()) {
				PedidoDto envPedido = new PedidoDto(mesa, "central", articulo.getIdProduct(), cantidadPedida , false);
				String respuesta = PedidosDAO.registraPedido(envPedido);

				if (respuesta.equals("Ingresado correctamente")) {
					Alert alert = new Alert (Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Crear Pedido");
					alert.setContentText("Se ha añadido "+lblmostrar.getText()+ "Cantidad: "+envPedido.getCantidad() );
					alert.showAndWait();
				}
			}
		}
		txtCantidad.setText("0");
		lblmostrar.setText("Sin selección");
	}

	@FXML
	private void seleccionar_Bebida(MouseEvent event) {
		articulo = this.tblBebida.getSelectionModel().getSelectedItem();

		if(articulo != null) {
			this.lblmostrar.setText(articulo.getNombreProd());
		}

	}

	@FXML
	private void seleccionar_comida(MouseEvent event) {
		articulo = this.tblComida.getSelectionModel().getSelectedItem();

		if(articulo != null) {
			this.lblmostrar.setText(articulo.getNombreProd());
		}

	}


	void cargarBaseProductos() {
		comida.addAll(DaoPresentaProd.recuperaProd());

		ProductoDto apoyo;
		for (int i = 0; i < comida.size(); i++) {

			if (comida.get(i).getTipo().equals("Bebidas")) {
				apoyo=comida.get(i);
				bebida.add(apoyo);
				comida.remove(i);
				i-=1;
			}
		}
	}


	@Override
	public void initialize(URL url, ResourceBundle bundle) {

		this.colComNombre.setCellValueFactory(new  PropertyValueFactory<>("nombreProd"));
		this.colComPrecio.setCellValueFactory((new PropertyValueFactory<>("precio")));
		this.colComId.setCellValueFactory((new PropertyValueFactory<>("idProduct")));
		this.colComStock.setCellValueFactory((new PropertyValueFactory<>("stock")));

		this.colBebNom.setCellValueFactory((new PropertyValueFactory<>("nombreProd")));
		this.colBebPrec.setCellValueFactory((new PropertyValueFactory<>("precio")));
		this.colBebId.setCellValueFactory((new PropertyValueFactory<>("idProduct")));
		this.colBebId.setCellValueFactory((new PropertyValueFactory<>("stock")));

		bebida = FXCollections.observableArrayList();
		comida = FXCollections.observableArrayList();
		cargarBaseProductos();
		this.tblComida.setItems(comida);
		this.tblBebida.setItems(bebida);
		setMesa(VentanaMesaController.getMesa());
		lblFlagMesa.setText("Mesa: "+mesa);	
		txtCantidad.setText(String.valueOf(0));

	}

}

