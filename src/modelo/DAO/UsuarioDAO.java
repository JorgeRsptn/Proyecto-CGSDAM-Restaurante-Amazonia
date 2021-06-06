package modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.DTO.UsuarioDto;
import modelo.conexion.ConexionMySql;
import modelo.conexion.SingleConnect;


public class UsuarioDAO {


	/********************************************************************Consulta de usuarios***************/
	public boolean consultaUsuario(String user, String pass) {
		Connection conexion=null;
		PreparedStatement statement=null;
		ResultSet result = null;
		boolean admitir = false;


		conexion = SingleConnect.getConnection();

		String consulta="SELECT * FROM usuarios where perfil=?";

		try {
			UsuarioDto usuarioPojo = new UsuarioDto(user, pass);
			
			if (conexion != null) {

				statement=conexion.prepareStatement(consulta);
				statement.setString(1, user);

				result=statement.executeQuery();

				while(result.next()==true) {
					usuarioPojo.setNomUser(result.getString(1));
					usuarioPojo.setPass(result.getString(2));					
				}
				if (pass.equals(usuarioPojo.getPass())){
					System.out.println("Pass es "+ pass + "y en base "+ usuarioPojo.getPass());
					
					admitir=true;
				}

			}else {
				usuarioPojo=null;
			}
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Error en la consulta de usuario"+e.getMessage());
		}
		return admitir;

	}
	/*********************************************************************************Registro de usuarios**********************************/


	public String registraUser (String user, String pass) {
		String respuesta=null;

		Connection conexion=null;
		PreparedStatement statement=null;
		

		conexion = SingleConnect.getConnection();

		String insert="INSERT INTO usuarios (tipo, pass) VALUES (?,?)";

		System.out.println(pass);
		try {
			if (conexion != null) {

				statement=conexion.prepareStatement(insert);
				statement.setString(1, user);
				statement.setString(2, pass);

				int row = statement.executeUpdate();
				System.out.println(row);
				if(row==1) {
					respuesta="Ingresado correctamente";
				}else {
					respuesta="No se ha podido ingresar";
				}
			}

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respuesta;
	}
}
