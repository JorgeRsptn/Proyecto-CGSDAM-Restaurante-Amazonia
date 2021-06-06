package modelo.DTO;

public class EmpleadoDto {

	private String codEmp;
	private String nombreEmp;
	private String apellidosEmp;
	public EmpleadoDto(String codEmp, String nombreEmp, String apellidosEmp) {
		super();
		this.codEmp = codEmp;
		this.nombreEmp = nombreEmp;
		this.apellidosEmp = apellidosEmp;
	}
	/**
	 * @return the codEmp
	 */
	public String getCodEmp() {
		return codEmp;
	}
	/**
	 * @param codEmp the codEmp to set
	 */
	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}
	/**
	 * @return the nombreEmp
	 */
	public String getNombreEmp() {
		return nombreEmp;
	}
	/**
	 * @param nombreEmp the nombreEmp to set
	 */
	public void setNombreEmp(String nombreEmp) {
		this.nombreEmp = nombreEmp;
	}
	/**
	 * @return the apellidosEmp
	 */
	public String getApellidosEmp() {
		return apellidosEmp;
	}
	/**
	 * @param apellidosEmp the apellidosEmp to set
	 */
	public void setApellidosEmp(String apellidosEmp) {
		this.apellidosEmp = apellidosEmp;
	}
	
	
	
}
