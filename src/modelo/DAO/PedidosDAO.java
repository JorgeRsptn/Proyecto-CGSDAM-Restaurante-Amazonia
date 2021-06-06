package modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.DTO.DetallePedidoDto;
import modelo.DTO.PedidoDto;
import modelo.conexion.ConexionMySql;
import modelo.conexion.SingleConnect;

public class PedidosDAO {
	private static ConexionMySql objConexion;

	/**
	 * @return the objConexion
	 */
	public ConexionMySql getObjConexion() {
		return objConexion;
	}
	/**
	 * @param objConexion the objConexion to set
	 */
	public void setObjConexion(ConexionMySql objConexion) {
		PedidosDAO.objConexion = objConexion;
	}


	public static String registraPedido (PedidoDto pedido) {
		String respuesta=null;

		Connection conexion=null;
		objConexion=new ConexionMySql();
		PreparedStatement statement=null;


		conexion = objConexion.getConnection2();

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

		objConexion.desconectar();


		return respuesta;
	}
	
	public static ObservableList<DetallePedidoDto> CargaPedidos(int mesa) {

		ObservableList<DetallePedidoDto> observable =  FXCollections.observableArrayList();
		
		DetallePedidoDto auxPed;		

		Connection conexion=null;
		objConexion=new ConexionMySql();
		PreparedStatement statement=null;
		ResultSet result;
/*
		conexion = objConexion.getConnection();

		String query ="SELECT p.id , p.articulo, p.cantidad, a.precio, (p.cantidad*a.precio) AS total  , p.empleado  FROM pedidos as p JOIN articulos as a ON a.nombre=p.articulo WHERE mesa = ? and pagado = 0";
*/
		
		conexion = objConexion.getConnection2();
		
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

		objConexion.desconectar();


		return observable;

	}

	public static String modificaPedido (int id, int cantidad) {
		String respuesta=null;

		Connection conexion=null;
		objConexion=new ConexionMySql();
		PreparedStatement statement=null;


		conexion = objConexion.getConnection2();

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
		objConexion.desconectar();
		return respuesta;		
	}
	
	
	public static String pagar(int mesa) {
		String respuesta=null;

		Connection conexion=null;
		objConexion=new ConexionMySql();
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

		objConexion.desconectar();



		return respuesta;		
	}


}
