package modelo.DTO;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PedidoDto {

	private SimpleIntegerProperty mesa;
	private SimpleStringProperty empleado;
	private SimpleIntegerProperty idProducto;
	private SimpleIntegerProperty cantidad;
	private SimpleBooleanProperty invitado;
	
	
	
	
	public PedidoDto(int mesa, String empleado, int idProducto,
			int cantidad, boolean invitado) {
		super();
		this.mesa = new SimpleIntegerProperty(mesa);
		this.empleado = new SimpleStringProperty(empleado);
		this.idProducto = new SimpleIntegerProperty(idProducto);
		this.cantidad = new SimpleIntegerProperty(cantidad);
		this.invitado = new SimpleBooleanProperty(invitado);
	}

	public final SimpleIntegerProperty mesaProperty() {
		return this.mesa;
	}
	
	public final int getMesa() {
		return this.mesaProperty().get();
	}
	
	public final void setMesa(final int mesa) {
		this.mesaProperty().set(mesa);
	}
	
	public final SimpleStringProperty empleadoProperty() {
		return this.empleado;
	}
	
	public final String getEmpleado() {
		return this.empleadoProperty().get();
	}
	
	public final void setEmpleado(final String empleado) {
		this.empleadoProperty().set(empleado);
	}
	
	public final SimpleIntegerProperty idProductoProperty() {
		return this.idProducto;
	}
	
	public final int getIdProducto() {
		return this.idProductoProperty().get();
	}
	
	public final void setIdProducto(final int idProducto) {
		this.idProductoProperty().set(idProducto);
	}
	
	public final SimpleIntegerProperty cantidadProperty() {
		return this.cantidad;
	}
	
	public final int getCantidad() {
		return this.cantidadProperty().get();
	}
	
	public final void setCantidad(final int cantidad) {
		this.cantidadProperty().set(cantidad);
	}
	
	public final SimpleBooleanProperty invitadoProperty() {
		return this.invitado;
	}
	
	public final boolean isInvitado() {
		return this.invitadoProperty().get();
	}
	
	public final void setInvitado(final boolean invitado) {
		this.invitadoProperty().set(invitado);
	}
	

	
	
	
}
