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

public class VistaInfoGralController implements Initializable {
	
	private int totalCons;
	private double totalFact;
	
    @FXML
    private Label lblFacturacion;

    @FXML
    private Label lblConsumiciones;
    
    @FXML
    private TableView<DetallePedidoDto> tblInfoGralEmpleados;

    @FXML
    private TableColumn<DetallePedidoDto, String> colEmpleadoInforme;

    @FXML
    private TableColumn<DetallePedidoDto, Double> colFacturadoTotal;

    @FXML
    private TableColumn<DetallePedidoDto, Integer> colConsumicionesTotal;
    
    private ObservableList<DetallePedidoDto> informeEmp;
    
    private ObservableList<InfoPedidosDTO> informeFecha;

    @FXML
    private JFXButton btnPrintGral;

    @FXML
    void print(MouseEvent event) {
    	makeFileTxt(totalCons, totalFact, informeEmp, informeFecha);
    }
    
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		

		this.colConsumicionesTotal.setCellValueFactory((new PropertyValueFactory<>("cantidad")));
		this.colFacturadoTotal.setCellValueFactory((new PropertyValueFactory<>("total")));
		this.colEmpleadoInforme.setCellValueFactory((new PropertyValueFactory<>("empleado")));
		
		informeEmp = FXCollections.observableArrayList();
		InfoPedidosDTO respuesta = InformesCierresDAO.estadisticasGralEmpleado();
		
		informeEmp.addAll(respuesta.getPedidos());
		this.tblInfoGralEmpleados.setItems(informeEmp);
		
		informeFecha = FXCollections.observableArrayList();
	//	InfoPedidosDTO resFecha = InformesCierresDAO.estadisticasGralFecha();
	//	informeFecha.addAll(resFecha.getObservable());
		
		totalCons = respuesta.getCantTotal();
		totalFact = respuesta.getCantTotalFact();
		
		lblConsumiciones.setText(String.valueOf(totalCons));
		lblFacturacion.setText(String.valueOf(totalFact));
		
		//makeFileTxt(totalCons, totalFact, informeEmp, informeFecha);		
	}
    
	public String makeFileTxt(int cons, double fact, ObservableList listEmpleada , ObservableList listFecha) {
        try {
        	
        	// Primer archivo resumen por Empleado
        	
            String currentPath = Paths.get("").toAbsolutePath().normalize().toString();
            String downloadFolder = "/cierre_Diario/Empleadas";
            String downloadPath = currentPath + downloadFolder;
            File newFolder = new File(downloadPath);
            boolean dirCreated = newFolder.mkdir();

            // get current time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-dd_HH-mm-ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            String fileName = "ResumenEmpleados_" + dtf.format(now) + ".csv";

            // Whatever the file path is.
            File statText = new File(downloadPath + "/" + fileName);
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            

            
            w.write("Empleado, Total consumiciones, Total facturado, Total Consumiciones, "+ cons + ", Total Facturación "+fact+"\n");
            
            for (int i = 0;i < listEmpleada.size() ; i++) {
            	DetallePedidoDto pedido = (DetallePedidoDto) listEmpleada.get(i);
                w.append(pedido.getEmpleado()+","+pedido.getCantidad()+","+pedido.getTotal() + "\n");
            }
            
            w.close();
            
            // Segundo archivo Resumen por Fecha
            
            downloadFolder = "/cierre_Diario/Fechas";
            String downloadPath2 = currentPath + downloadFolder;
            newFolder = new File(downloadPath2);
            dirCreated = newFolder.mkdir();

            String fileName2 = "ResumenFechas_" + dtf.format(now) + ".csv";

            File statText2 = new File(downloadPath2 + "/" + fileName2);
            FileOutputStream is2 = new FileOutputStream(statText2);
            OutputStreamWriter osw2 = new OutputStreamWriter(is2);
            Writer w2 = new BufferedWriter(osw2);
            

            
            w2.write("Fecha, Total consumiciones, Total facturado, Total Consumiciones, "+ cons + ", Total Facturación "+fact+"\n");
            
            for (int i = 0;i < listFecha.size() ; i++) {
            	DetallePedidoDto pedido = (DetallePedidoDto) listFecha.get(i);
                w2.append(pedido.getEmpleado()+","+pedido.getCantidad()+","+pedido.getTotal() + "\n");
            }
            
            w2.close();
            
            
            return "Generados los archivos: " +downloadPath + "/" + fileName + " - " + downloadPath2 + "/" + fileName2;
        
        } catch (IOException e) {
            System.err.println("Problem writing to the file " + e);
        }

        return "error";
    }

}
