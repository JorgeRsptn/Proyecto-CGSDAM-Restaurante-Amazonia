package modelo.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.DTO.ProductoDto;
import modelo.conexion.SingleConnect;

public class ArticulosDAO {


	public static ObservableList<ProductoDto> cargaTablaArt (){

		ObservableList<ProductoDto> observable =  FXCollections.observableArrayList();
		ProductoDto art;		
		String respuesta=null;
		Connection conexion=null;
		Statement statement=null;
		
		conexion = SingleConnect.getConnection();

		String query ="SELECT p.*, s.nowCant FROM productos as p, stock as s WHERE p.idProd = s.idProd";
		
		try {
			if (conexion != null) {

				statement=conexion.createStatement();

				ResultSet result = statement.executeQuery(query);
						
				if(result != null) {
					
					while (result.next()) {
						int id = result.getInt("id");
						String nombre = result.getString("nombre");
						double precio = result.getDouble("precio");
						String seccion = result.getString("tipo");
						int stock = result.getInt("nowCant");

						
						art=new ProductoDto(id, nombre, stock, precio, seccion);
						
						observable.add(art);
					}
				}else {
					respuesta="Problema al conectar con la Base de datos";
				}
			}

		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return observable;
	}



}
