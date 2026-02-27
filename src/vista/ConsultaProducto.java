package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import dao.ConexionBD;

public class ConsultaProducto extends JFrame {
    private JTable tablaProductos;
    private DefaultTableModel modelo;

    public ConsultaProducto() {
        setTitle("Consulta de Productos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnas = {"ID Producto", "Nombre", "Precio", "Categoría", "Stock"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modelo);

        cargarProductos();

        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
    }

    private void cargarProductos() {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT idProducto, nombre, precio, categoria, stock FROM Producto";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("idProducto"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getString("categoria"),
                    rs.getInt("stock")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ConsultaProducto().setVisible(true);
    }
}
