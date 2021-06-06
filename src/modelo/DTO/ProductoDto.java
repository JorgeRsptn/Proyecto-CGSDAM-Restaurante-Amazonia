

package modelo.DTO;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductoDto {

	private SimpleIntegerProperty idProduct;
	private SimpleStringProperty nombreProd;
	private SimpleIntegerProperty stock;
	private SimpleDoubleProperty precio;
	private SimpleStringProperty tipo;

	public ProductoDto(int idProduct, String nombreProd, int stock, double precio, String tipo) {
		super();
		this.idProduct = new SimpleIntegerProperty(idProduct);
		this.nombreProd = new SimpleStringProperty(nombreProd);
		this.stock = new SimpleIntegerProperty(stock);
		this.precio = new SimpleDoubleProperty(precio);
		this.tipo = new SimpleStringProperty(tipo);
	}
	/**
	 * @return the idProduct
	 */
	public int getIdProduct() {
		return idProduct.getValue();
	}
	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(int idProduct) {
		this.idProduct = new SimpleIntegerProperty(idProduct);
	}
	
	/**
	 * @return the stock
	 */
	public int getStock() {
		return stock.getValue();
	}
	/**
	 * @return the nombreProd
	 */
	public String getNombreProd() {
		return nombreProd.getValue();
	}
	/**
	 * @param nombreProd the nombreProd to set
	 */
	public void setNombreProd(String nombreProd) {
		this.nombreProd = new SimpleStringProperty(nombreProd);
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(int stock) {
		this.stock = new SimpleIntegerProperty(stock);
	}
	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio.getValue();
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = new SimpleDoubleProperty(precio);
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo.getValue();
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = new SimpleStringProperty(tipo);
	}

	
}
