package modelo.DTO;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class InfoPedidosDTO {

	private ObservableList<DetallePedidoDto> pedidos;
	
	private SimpleDoubleProperty cantTotalFact;
	
	private SimpleIntegerProperty cantTotalCons;
	/*
	private SimpleDateFormat horaPedido;
	
	private SimpleDateFormat horaPagado;
	 */
	
	public InfoPedidosDTO(ObservableList<DetallePedidoDto> pedidos, double cantTotalFact,
			int cantTotalCons) {
		super();
		this.pedidos = pedidos;
		this.cantTotalFact = new SimpleDoubleProperty(cantTotalFact);
		this.cantTotalCons = new SimpleIntegerProperty(cantTotalCons);
	}
	
	

	/**
	 * @return the pedidos
	 */
	public ObservableList<DetallePedidoDto> getPedidos() {
		return pedidos;
	}



	/**
	 * @param pedidos the pedidos to set
	 */
	public void setPedidos(ObservableList<DetallePedidoDto> pedidos) {
		this.pedidos = pedidos;
	}


	public final SimpleIntegerProperty cantTotalConsProperty() {
		return this.cantTotalCons;
	}
	

	public final int getCantTotal() {
		return this.cantTotalConsProperty().get();
	}
	

	public final void setCantTotal(final int cantTotal) {
		this.cantTotalConsProperty().set(cantTotal);
	}



	public final SimpleDoubleProperty cantTotalFactProperty() {
		return this.cantTotalFact;
	}
	



	public final double getCantTotalFact() {
		return this.cantTotalFactProperty().get();
	}
	



	public final void setCantTotalFact(final double cantTotalFact) {
		this.cantTotalFactProperty().set(cantTotalFact);
	}
	



	
	

	
	
	
	
}