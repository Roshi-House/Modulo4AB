package dao;

import modelo.DetalleVenta;
import modelo.Venta;
import java.sql.*;

public class VentaDAO {
    public void registrarVenta(Venta venta) {
        try (Connection conn = ConexionBD.getConnection()) {
            conn.setAutoCommit(false); // Transacción

            // Insertar venta
            String sqlVenta = "INSERT INTO Venta(fecha, idCliente) VALUES (?, ?)";
            PreparedStatement psVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setDate(1, new java.sql.Date(venta.getFecha().getTime()));
            psVenta.setInt(2, venta.getCliente().getIdCliente());
            psVenta.executeUpdate();

            ResultSet rs = psVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rs.next()) idVenta = rs.getInt(1);

            // Insertar detalles
            String sqlDetalle = "INSERT INTO DetalleVenta(idVenta, idProducto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
            PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle);

            for (DetalleVenta d : venta.getDetalles()) {
                psDetalle.setInt(1, idVenta);
                psDetalle.setInt(2, d.getProducto().getIdProducto());
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.setDouble(4, d.calcularSubtotal());
                psDetalle.executeUpdate();

                // Actualizar stock
                String sqlStock = "UPDATE Producto SET stock = stock - ? WHERE idProducto = ?";
                PreparedStatement psStock = conn.prepareStatement(sqlStock);
                psStock.setInt(1, d.getCantidad());
                psStock.setInt(2, d.getProducto().getIdProducto());
                psStock.executeUpdate();
            }

            conn.commit();
            System.out.println("Venta registrada con éxito en BD.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
