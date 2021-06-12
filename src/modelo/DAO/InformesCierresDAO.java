package modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.DTO.DetallePedidoDto;
import modelo.DTO.InfoPedidosDTO;
import modelo.conexion.SingleConnect;

public class InformesCierresDAO {

	
	
	/**
	 * Recupera el total facturado y cobrado ahasta el momento en el día.
	 * @return
	 */

	public static InfoPedidosDTO arqueo () {


		ResultSet result=null;
		Connection conexion=null;
		PreparedStatement statement=null;


		ObservableList<DetallePedidoDto> observable =  FXCollections.observableArrayList();
		conexion = SingleConnect.getConnection();
		int totConsumiciones=0;
		double totFacturacion=0;
		InfoPedidosDTO informe = null;
		DetallePedidoDto auxPedido;
		System.out.println("Pidiendo datos");



		String arqueo = "SELECT p.mesa, p.empleado, p.idProd, p.cantidad, p.idPedido, s.nombre, s.precio, (p.cantidad*s.precio) AS total FROM pedidos as p JOIN productos as s ON s.idProd = p.idProd WHERE pagado = 1 GROUP BY p.empleado";

		try {
			if (conexion != null) {

				statement=conexion.prepareStatement(arqueo);
				result = statement.executeQuery();

				if(result != null) {					
					DetallePedidoDto auxPed = null;
					ObservableList<DetallePedidoDto> pedidos = null;

					while(result.next()) {

						int mesa = 0;
						String empleado = result.getString("p.empleado");
						int idProd = 0;
						int cantidad = result.getInt("p.cantidad");
						boolean invitado = false;
						int idPedido = 0;
						String nomArt = "a";
						double precio = 0.0;
						double total = result.getDouble("total");

						auxPedido = new DetallePedidoDto(mesa, empleado, idProd, cantidad, invitado, idPedido, nomArt, precio, total);
						totConsumiciones += cantidad;
						totFacturacion += total;
						observable.add(auxPedido);

					}

					informe = new InfoPedidosDTO(observable, totFacturacion, totConsumiciones);

				}else {
					System.out.println("No hay resultados");
				}
			}

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally { // Close in order: ResultSet, Statement, Connection.
			try {
				result.close();
			} catch (Exception e) {
			}
			try {
				statement.close();
			} catch (Exception e) {
			}
		}

		return informe;
	}

	
	/**
	 * Comprueba que no falten mesas por pagar
	 * @return
	 */
	public static String comprobarCierreCaja() {


		String respuesta=null;
		ResultSet result=null;

		Connection conexion=SingleConnect.getConnection();
		PreparedStatement statement=null;

		conexion = SingleConnect.getConnection();	
		String comprobacion="select p.idPedido from pedidos as p where p.pagado = 0";

		try {
			if (conexion != null) {

				statement=conexion.prepareStatement(comprobacion);

				result = statement.executeQuery();

				System.out.println("Consulta");
				if(!result.next()) {

					respuesta = "Todo pagado";

				}else {
					respuesta="Faltan mesas por cerrar";
					System.out.println(respuesta);
				}
			}
		}
		catch(Exception e) {

		}
		return respuesta;
	}


	/**
	 * Trás comprobar que todo está pagado devuelve la información de lo facturado en el día y envía todas los datos a la tabla encargada del historico.
	 * @return
	 */
	public static String CierreCaja() {

		String respuesta=null;
		ResultSet result=null;

		Connection conexion=null;
		Statement statement=null;
		conexion = SingleConnect.getConnection();

		String cerrarDia= "INSERT INTO historico (cantidad, empleado, horaPagado, horaPedido, idProd, mesa, precio, total) SELECT p.cantidad AS cantidad, p.empleado AS empleado, p.horaPagado as hPagado,p.horaPed as hPed,p.idProd as id, p.mesa AS mesa,  a.precio  AS precio, (p.cantidad*a.precio) AS total FROM pedidos as p JOIN productos as a ON a.idProd=p.idProd";
		String vaciar = "DELETE FROM pedidos";

		try {
			if (conexion != null) {
				statement = conexion.createStatement();
				statement.executeUpdate(cerrarDia);
				statement.executeUpdate(vaciar);
				respuesta = "Caja Cerrada";

			}else {
				respuesta="Problema con la conexión";
				System.out.println(respuesta);
			}
		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally { // Close in order: ResultSet, Statement, Connection.
			try {
				result.close();
			} catch (Exception e) {
			}
			try {
				statement.close();
			} catch (Exception e) {
			}

		}
		return respuesta;
	}
	
	/**
	 * Devuelve los detalles de facturación divididos por empleado
	 * @return
	 */

	public static InfoPedidosDTO estadisticasGralEmpleado() {
		String respuesta=null;
		ResultSet result=null;

		Connection conexion=null;
		PreparedStatement statement=null;

		ObservableList<DetallePedidoDto> observable =  FXCollections.observableArrayList();
		int totConsumiciones=0;
		double totFacturacion=0;
		InfoPedidosDTO informe = null;
		DetallePedidoDto auxPedido;


		conexion = SingleConnect.getConnection();

		String infoEmpleado = "SELECT h.mesa, h.empleado, h.idProd, h.cantidad, h.idLinea, s.nombre, h.precio, h.total FROM historico as h JOIN productos as s ON s.idProd = h.idProd GROUP BY h.empleado";

		//	String infofecha = "SELECT t.fecha as fechaT, SUM(t.consumiciones) as TOTAL_cONS ,SUM(t.total) AS TOTAL_FACT FROM (SELECT p.fecha, p.cantidad as CONSUMICIONES, p.total AS total FROM historico as p) AS t GROUP BY t.fecha";

		try {
			if (conexion != null) {

				statement=conexion.prepareStatement(infoEmpleado);

				result = statement.executeQuery();

				if(result != null) {

					while(result.next()) {

						int mesa = 0;
						String empleado = result.getString("p.empleado");
						int idProd = 0;
						int cantidad = result.getInt("p.cantidad");
						boolean invitado = false;
						int idPedido = 0;
						String nomArt = "a";
						double precio = 0.0;
						double total = result.getDouble("total");

						auxPedido = new DetallePedidoDto(mesa, empleado, idProd, cantidad, invitado, idPedido, nomArt, precio, total);
						totConsumiciones += cantidad;
						totFacturacion += total;
						observable.add(auxPedido);
					}

					informe = new InfoPedidosDTO(observable, totFacturacion, totConsumiciones);

				}else {
					respuesta="No hay resultados";
					System.out.println(respuesta);
				}
			}

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally { // Cerramos en orden: ResultSet, Statement
			try {
				result.close();
			} catch (Exception e) {
			}
			try {
				statement.close();
			} catch (Exception e) {
			}
		}
		return informe;
	}

	/*
	public static InformesCierresVO estadisticasGralFecha() {
		String respuesta=null;
		ResultSet result=null;

		Connection conexion=null;
		objConexion=new ConexionMySql();
		PreparedStatement statement=null;

		ObservableList<PedidosDevolucionVO> observable =  FXCollections.observableArrayList();
		int totConsumiciones=0;
		double totFacturacion=0;
		InformesCierresVO informe = null;

		System.out.println("Pidiendo datos");
		conexion = objConexion.getConnection();

		//String arqueo="SELECT SUM(t.consumiciones)as TOTAL_cONS ,SUM(t.total) TOTAL_FACT FROM (SELECT p.cantidad as CONSUMICIONES, p.cantidad*a.precio AS total FROM pedidos as p JOIN articulos as a ON a.nombre=p.articulo WHERE pagado = 1) AS t";

		//String infoEmpleado = "SELECT t.empleado as empleadoT, SUM(t.consumiciones)as TOTAL_cONS ,SUM(t.total) TOTAL_FACT FROM (SELECT p.empleado, p.cantidad as CONSUMICIONES, p.cantidad*a.precio AS total FROM historico as p JOIN articulos as a ON a.nombre=p.articulo) AS t GROUP BY t.empleado";

		String infofecha = "SELECT t.fecha as fechaT, SUM(t.consumiciones) as TOTAL_cONS ,SUM(t.total) AS TOTAL_FACT FROM (SELECT p.fecha, p.cantidad as CONSUMICIONES, p.total AS total FROM historico as p) AS t GROUP BY t.fecha";

		try {
			if (conexion != null) {

				statement=conexion.prepareStatement(infofecha);

				result = statement.executeQuery();

				if(result != null) {
					respuesta="Generando Informe";
					while(result.next()) {
						int id = 0;
						String articulo = null;
						double precio = 0.0;
						int cantidad = result.getInt("TOTAL_cONS");
						double total = result.getDouble("TOTAL_FACT");
						String empleado = result.getString("fechaT");

						pedidoVO = new PedidosDevolucionVO(id, articulo, precio, cantidad, total, empleado);
						totConsumiciones += cantidad;
						totFacturacion += total;
						observable.add(pedidoVO);
					}

					informe = new InformesCierresVO(totConsumiciones, totFacturacion, observable);

				}else {
					respuesta="No hay resultados";
					System.out.println(respuesta);
				}
			}

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}finally { // Close in order: ResultSet, Statement, Connection.
			try {
				result.close();
			} catch (Exception e) {
			}
			try {
				statement.close();
			} catch (Exception e) {
			}
		}
		return informe;
	}
	 */
}


