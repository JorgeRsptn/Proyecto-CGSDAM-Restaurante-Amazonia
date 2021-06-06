package modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySql {

	private String nombreBD ="Restaurante";
	private String usuario="root";
	private String password="";
	private String url="jdbc:mysql://localhost:3306/"+nombreBD+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private String nombreBD2 ="rest_amazonia";
	private String url2="jdbc:mysql://localhost:3306/"+nombreBD2+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	Connection conect2 =null;
	
	Connection conect =null;

	public ConexionMySql() {

		try {

			//Obtiene el driver

			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
			conect=DriverManager.getConnection(url,usuario,password);
			
			conect2=DriverManager.getConnection(url2,usuario,password);
			

			if(conect!= null) {
				System.out.println("Conexión correcta con la bd : "+nombreBD);
			} else if (conect2 != null) {
				System.out.println("Conexión 2 correcta con rest_amazonia");
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al localizar el Driver");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al conectar con la base de datos");
		}

	}
	
	

	public Connection getConnection() {
		System.out.println("Conectando...");
		return conect;
	}

	public Connection getConnection2() {
		System.out.println("Conectando...");
		return conect2;
	}


	public void desconectar() {
		if (conect!=null) {
			try {
				conect.close();
				conect2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
