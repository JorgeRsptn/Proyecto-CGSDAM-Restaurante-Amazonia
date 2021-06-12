package modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnect {

	private static Connection conexion = null;
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String usuario = "root";
	private String password = "";
	private String nombreBD = "rest_amazonia";
	private String url = "jdbc:mysql://localhost:3306/"+nombreBD+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private SingleConnect(){

		try{
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, password);
			System.out.println("conexion creada en single.");
		}
		catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
	}

	public static Connection getConnection(){

		if (conexion == null){
			new SingleConnect();
		}

		return conexion;
	}

}
