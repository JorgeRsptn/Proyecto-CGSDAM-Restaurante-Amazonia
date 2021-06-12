package modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.DTO.DetallePedidoDto;
import modelo.DTO.PedidoDto;
import modelo.conexion.SingleConnect;

public class PedidosDAO {

/**
 * Guarda los pedidos realizados en la base de datos.
 * @param pedido
 * @return
 */


	public static String registraPedido (PedidoDto pedido) {
		String respuesta=null;

		Connection conexion=null;
		PreparedStatement statement=null;


		conexion = SingleConnect.getConnection();

		String insert="INSERT INTO pedidos (mesa, empleado, idProd, cantidad) VALUES (?,?,?,?)";

		System.out.println(pedido.getIdProducto());
		try {
			if (conexion != null) {

				statement=conexion.prepareStatement(insert);
				statement.setInt(1, pedido.getMesa());
				statement.setString(2, pedido.getEmpleado());
				statement.setInt(3, pedido.getIdProducto());
				statement.setInt(4, pedido.getCantidad());

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
	
	/**
	 * Recupera los pedidos no pagados asociados a una mesa
	 * @param mesa
	 * @return un observable list con la relación de pedidos.
	 */
	
	public static ObservableList<DetallePedidoDto> CargaPedidos(int mesa) {

		ObservableList<DetallePedidoDto> observable =  FXCollections.observableArrayList();
		
		DetallePedidoDto auxPed;		

		Connection conexion=null;
		PreparedStatement statement=null;
		ResultSet result;
/*
		conexion = objConexion.getConnection();

		String query ="SELECT p.id , p.articulo, p.cantidad, a.precio, (p.cantidad*a.precio) AS total  , p.empleado  FROM pedidos as p JOIN articulos as a ON a.nombre=p.articulo WHERE mesa = ? and pagado = 0";
*/
		
		conexion = SingleConnect.getConnection();
		
		String query = "SELECT p.mesa, p.empleado,  p.idProd, p.cantidad, p.idPedido, s.nombre, s.precio, (p.cantidad*s.precio) AS total FROM pedidos as p JOIN productos as s ON s.idProd = p.idProd WHERE mesa = ? and pagado = 0";
		try {
			if (conexion != null) {

				statement=conexion.prepareStatement(query);
				statement.setInt(1, mesa);

				result = statement.executeQuery();


				if(result != null) {

					while (result.next()) {
						
						//mesa es la misma que la consulta
						String empleado = result.getString("empleado");
						int idProd = result.getInt("idProd");						
						int cantidad = result.getInt("cantidad");
						//invitado en pruebas
						int idPed = result.getInt("idPedido");
						String articulo = result.getString("nombre");
						double precio = result.getDouble("precio");						
						double total = result.getDouble("total");
						

						auxPed = new DetallePedidoDto(mesa, empleado, idProd, cantidad, false, idPed, articulo, precio, total);


						observable.add(auxPed);
					}
				}else {
					System.out.println("Problema al conectar con la Base de datos");
				}
			}

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	return observable;

	}

	/**
	 * Realiza las modificaciones de cantidad necesarias sobre un pedido anterior.
	 * @param id
	 * @param cantidad
	 * @return devuelve una confirmación de la rrealización o no de la modificación
	 */
	public static String modificaPedido (int id, int cantidad) {
		String respuesta=null;

		Connection conexion=null;

		PreparedStatement statement=null;


		conexion = SingleConnect.getConnection();

		String insert="UPDATE pedidos SET cantidad = ? WHERE idPedido = ?";

		try {
			
			if (conexion != null) {

				statement=conexion.prepareStatement(insert);
				statement.setInt(1, cantidad);
				statement.setInt(2, id);

				int row = statement.executeUpdate();
				System.out.println(row);
				
				if(row==1) {
					respuesta="Modificado correctamente";
				}else {
					respuesta="No se ha podido modificar";
				}
			}
		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respuesta;		
	}
	
	/**
	 * Se encarga de poner como pagados todos los pedidos de la mesa que recibe por parametro. 
	 * @param mesa
	 * @return Respuesta avisando de si se han producido los cambios.
	 */
	
	public static String pagar(int mesa) {
		String respuesta=null;

		Connection conexion=null;
		PreparedStatement statement=null;
		conexion = SingleConnect.getConnection();

		String insert="UPDATE pedidos SET pagado = true WHERE pagado = false AND mesa = ?";


		try {
			if (conexion != null) {

				statement=conexion.prepareStatement(insert);
				statement.setInt(1, mesa);

				int row = statement.executeUpdate();
				System.out.println(row);
				if(row>0) {
					respuesta="Modificado correctamente";
				}else {
					respuesta="No se ha podido modificar";
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
