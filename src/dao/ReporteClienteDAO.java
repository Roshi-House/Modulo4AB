package dao;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ReporteClienteDAO {
    public DefaultTableModel obtenerVentasPorCliente() {
        String[] columnas = {"ID Cliente", "Cliente", "Tipo", "Compras", "Total Gastado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT c.idCliente, c.nombre, c.tipoCliente, " +
                         "COUNT(v.idVenta) AS NumeroCompras, SUM(d.subtotal) AS TotalGastado " +
                         "FROM Cliente c " +
                         "JOIN Venta v ON c.idCliente = v.idCliente " +
                         "JOIN DetalleVenta d ON v.idVenta = d.idVenta " +
                         "GROUP BY c.idCliente, c.nombre, c.tipoCliente " +
                         "ORDER BY TotalGastado DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("tipoCliente"),
                    rs.getInt("NumeroCompras"),
                    rs.getDouble("TotalGastado")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelo;
    }
}
