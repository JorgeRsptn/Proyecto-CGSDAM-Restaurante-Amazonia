package controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.DAO.InformesCierresDAO;
import modelo.DTO.DetallePedidoDto;
import modelo.DTO.InfoPedidosDTO;

public class VistaResultadosController implements Initializable {
	
	private int totalCons;
	private double totalFact;
	
    @FXML
    private Label lblFacturacion;

    @FXML
    private Label lblConsumiciones;
    
    @FXML
    private TableView<DetallePedidoDto> tblInfoEmpleados;

    @FXML
    private TableColumn<DetallePedidoDto, String> colEmpleadoInforme;

    @FXML
    private TableColumn<DetallePedidoDto, Double> colFacturadoTotal;

    @FXML
    private TableColumn<DetallePedidoDto, Integer> colConsumicionesTotal;
    
    private ObservableList<DetallePedidoDto> informe;
    
    @FXML
    private JFXButton btnPrint;

    @FXML
    void print(MouseEvent event) {
    	makeFileTxt(totalCons, totalFact, informe);
    }

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
		System.out.println("Inicializa tabla");
		this.colConsumicionesTotal.setCellValueFactory((new PropertyValueFactory<>("cantidad")));
		this.colFacturadoTotal.setCellValueFactory((new PropertyValueFactory<>("total")));
		this.colEmpleadoInforme.setCellValueFactory((new PropertyValueFactory<>("empleado")));
		System.out.println("Carga datos");
		
		informe = FXCollections.observableArrayList();
		InfoPedidosDTO respuesta = InformesCierresDAO.arqueo();
		informe.addAll(respuesta.getPedidos());
		this.tblInfoEmpleados.setItems(informe);
		
		totalCons = respuesta.getCantTotal();
		totalFact = respuesta.getCantTotalFact();
		
		lblConsumiciones.setText(String.valueOf(totalCons));
		lblFacturacion.setText(String.valueOf(totalFact));
		//makeFileTxt(totalCons, totalFact, informe);		
	}
    
	public String makeFileTxt(int cons, double fact, ObservableList list) {
        try {
            String currentPath = Paths.get("").toAbsolutePath().normalize().toString();
            String downloadFolder = "/arqueo_Diario";
            String downloadPath = currentPath + downloadFolder;
            File newFolder = new File(downloadPath);
            boolean dirCreated = newFolder.mkdir();

            // get current time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-dd_HH-mm-ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            String fileName = "Arqueo_" + dtf.format(now) + ".csv";

            // Whatever the file path is.
            File statText = new File(downloadPath + "/" + fileName);
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            

            
            w.write("Empleado, Total consumiciones, Total facturado, Total Consumiciones, "+ cons + ", Total Facturación "+fact+"\n");
            
            for (int i = 0;i < list.size() ; i++) {
            	DetallePedidoDto pedido = (DetallePedidoDto) list.get(i);
                w.append(pedido.getEmpleado()+","+pedido.getCantidad()+","+pedido.getTotal() + "\n");
            }
            w.close();
            return downloadPath + "/" + fileName;
        } catch (IOException e) {
            System.err.println("Problem writing to the file " + e);
        }

        return "error";
    }

}
