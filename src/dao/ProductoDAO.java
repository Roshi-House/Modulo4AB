package dao;

import modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT idProducto, nombre, precio, categoria, stock FROM Producto";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getInt("stock")
                );
                productos.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }
}
