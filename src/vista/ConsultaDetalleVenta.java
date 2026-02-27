package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import dao.ConexionBD;

public class ConsultaDetalleVenta extends JFrame {
    private JTable tablaDetalles;
    private DefaultTableModel modelo;

    public ConsultaDetalleVenta() {
        setTitle("Consulta de Detalles de Ventas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"ID Detalle", "ID Venta", "Producto", "Cantidad", "Subtotal"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaDetalles = new JTable(modelo);

        cargarDetalles();

        add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);
    }

    private void cargarDetalles() {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT d.idDetalle, d.idVenta, p.nombre AS producto, d.cantidad, d.subtotal " +
                         "FROM DetalleVenta d " +
                         "JOIN Producto p ON d.idProducto = p.idProducto";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("idDetalle"),
                    rs.getInt("idVenta"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("subtotal")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar detalles: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ConsultaDetalleVenta().setVisible(true);
    }
}
