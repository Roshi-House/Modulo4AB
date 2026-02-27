package dao;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ReporteDAO {
    public DefaultTableModel obtenerVentas() {
        String[] columnas = {"ID Venta", "Cliente", "Fecha", "Producto", "Cantidad", "Subtotal"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT v.idVenta, c.nombre, v.fecha, p.nombre, d.cantidad, d.subtotal " +
                         "FROM Venta v " +
                         "JOIN Cliente c ON v.idCliente = c.idCliente " +
                         "JOIN DetalleVenta d ON v.idVenta = d.idVenta " +
                         "JOIN Producto p ON d.idProducto = p.idProducto";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("idVenta"),
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getString("p.nombre"),
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelo;
    }
}
