package modelo.DTO;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DetallePedidoDto extends PedidoDto{
	
	private SimpleIntegerProperty idPedido;
	private SimpleStringProperty nombreArt;
	private SimpleDoubleProperty precio;
	private SimpleDoubleProperty total;

	


	public DetallePedidoDto(int mesa, String empleado, int idProducto,
			int cantidad, boolean invitado, int idPedido,
			String nombreArt, Double precio, Double total) {
		super(mesa, empleado, idProducto, cantidad, invitado);
		this.idPedido = new SimpleIntegerProperty(idPedido);
		this.nombreArt = new SimpleStringProperty(nombreArt);
		this.precio = new SimpleDoubleProperty(precio);
		this.total = new SimpleDoubleProperty(total);
	}


	public final SimpleIntegerProperty idPedidoProperty() {
		return this.idPedido;
	}
	

	public final int getIdPedido() {
		return this.idPedidoProperty().get();
	}
	

	public final void setIdPedido(final int idPedido) {
		this.idPedidoProperty().set(idPedido);
	}
	

	public final SimpleStringProperty nombreArtProperty() {
		return this.nombreArt;
	}
	

	public final String getNombreArt() {
		return this.nombreArtProperty().get();
	}
	

	public final void setNombreArt(final String nombreArt) {
		this.nombreArtProperty().set(nombreArt);
	}
	

	public final SimpleDoubleProperty precioProperty() {
		return this.precio;
	}
	

	public final double getPrecio() {
		return this.precioProperty().get();
	}
	

	public final void setPrecio(final double precio) {
		this.precioProperty().set(precio);
	}
	

	public final SimpleDoubleProperty totalProperty() {
		return this.total;
	}
	

	public final double getTotal() {
		return this.totalProperty().get();
	}
	

	public final void setTotal(final double total) {
		this.totalProperty().set(total);
	}
	
	
	
	
	
}