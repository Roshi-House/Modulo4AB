package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import dao.ConexionBD;

public class ConsultaVenta extends JFrame {
    private JTable tablaVentas;
    private DefaultTableModel modelo;

    public ConsultaVenta() {
        setTitle("Consulta de Ventas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"ID Venta", "Fecha", "Cliente", "Producto", "Cantidad", "Subtotal"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaVentas = new JTable(modelo);

        cargarVentas();

        add(new JScrollPane(tablaVentas), BorderLayout.CENTER);
    }

    private void cargarVentas() {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT v.idVenta, v.fecha, c.nombre AS cliente, p.nombre AS producto, d.cantidad, d.subtotal " +
                         "FROM Venta v " +
                         "JOIN Cliente c ON v.idCliente = c.idCliente " +
                         "JOIN DetalleVenta d ON v.idVenta = d.idVenta " +
                         "JOIN Producto p ON d.idProducto = p.idProducto";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("idVenta"),
                    rs.getDate("fecha"),
                    rs.getString("cliente"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar ventas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ConsultaVenta().setVisible(true);
    }
}
