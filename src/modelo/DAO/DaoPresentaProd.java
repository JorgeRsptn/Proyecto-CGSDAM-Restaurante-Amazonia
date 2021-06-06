package modelo.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.DTO.ProductoDto;
import modelo.conexion.ConexionMySql;

public class DaoPresentaProd {

	private static ConexionMySql objConexion;
	private ProductoDto producto;
	
	
	public static ObservableList<ProductoDto> recuperaProd() {

		ObservableList<ProductoDto> observable =  FXCollections.observableArrayList();
		Connection conexion=null;
		objConexion=new ConexionMySql();
		Statement statement=null;
		
		ProductoDto auxProducto;

		conexion = objConexion.getConnection2();

		String query ="SELECT DISTINCT p.idProd , p.nombre, p.precio, p.tipo, s.nowCant  FROM productos as p , stock as s where p.idProd = s.idProd";
		
		try {
			if (conexion != null) {

				statement=conexion.createStatement();

				ResultSet result = statement.executeQuery(query);
				
				
				if(result != null) {
					
					while (result.next()) {
						int id = result.getInt("idProd");
						String nombre = result.getString("nombre");
						double precio = result.getDouble("precio");
						String tipo = result.getString("tipo");
						int stock = result.getInt("nowCant");
						
						auxProducto=new ProductoDto(id, nombre, stock, precio, tipo);
						
						observable.add(auxProducto);
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

	
}
