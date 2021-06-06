package modelo.DTO;

import javafx.beans.property.SimpleStringProperty;

public class UsuarioDto {

	private SimpleStringProperty nomUser;
	private SimpleStringProperty pass;
	
	
	public UsuarioDto(String nomUser, String pass) {
		super();
		this.nomUser = new SimpleStringProperty(nomUser);
		this.pass = new SimpleStringProperty(pass);
	}
	public final SimpleStringProperty nomUserProperty() {
		return this.nomUser;
	}
	
	public final String getNomUser() {
		return this.nomUserProperty().get();
	}
	
	public final void setNomUser(final String nomUser) {
		this.nomUserProperty().set(nomUser);
	}
	
	public final SimpleStringProperty passProperty() {
		return this.pass;
	}
	
	public final String getPass() {
		return this.passProperty().get();
	}
	
	public final void setPass(final String pass) {
		this.passProperty().set(pass);
	}
	
	
	
	
}
