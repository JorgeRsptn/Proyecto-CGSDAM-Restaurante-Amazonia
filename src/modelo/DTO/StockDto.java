package modelo.DTO;

import javafx.beans.property.SimpleIntegerProperty;

public class StockDto {

	private SimpleIntegerProperty idProd;
	private SimpleIntegerProperty maxCant;
	private SimpleIntegerProperty minCant;
	private SimpleIntegerProperty nowCant;
	public StockDto(int idProd, int maxCant, int minCant,
			int nowCant) {
		super();
		this.idProd = new SimpleIntegerProperty(idProd);
		this.maxCant = new SimpleIntegerProperty(maxCant);
		this.minCant = new SimpleIntegerProperty(minCant);
		this.nowCant = new SimpleIntegerProperty(nowCant);
	}
	public final SimpleIntegerProperty idProdProperty() {
		return this.idProd;
	}
	
	public final int getIdProd() {
		return this.idProdProperty().get();
	}
	
	public final void setIdProd(final int idProd) {
		this.idProdProperty().set(idProd);
	}
	
	public final SimpleIntegerProperty maxCantProperty() {
		return this.maxCant;
	}
	
	public final int getMaxCant() {
		return this.maxCantProperty().get();
	}
	
	public final void setMaxCant(final int maxCant) {
		this.maxCantProperty().set(maxCant);
	}
	
	public final SimpleIntegerProperty minCantProperty() {
		return this.minCant;
	}
	
	public final int getMinCant() {
		return this.minCantProperty().get();
	}
	
	public final void setMinCant(final int minCant) {
		this.minCantProperty().set(minCant);
	}
	
	public final SimpleIntegerProperty nowCantProperty() {
		return this.nowCant;
	}
	
	public final int getNowCant() {
		return this.nowCantProperty().get();
	}
	
	public final void setNowCant(final int nowCant) {
		this.nowCantProperty().set(nowCant);
	}
	
	
	
	
	
}