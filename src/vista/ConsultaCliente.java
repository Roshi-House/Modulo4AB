package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import dao.ConexionBD;

public class ConsultaCliente extends JFrame {
    private JTable tablaClientes;
    private DefaultTableModel modelo;

    public ConsultaCliente() {
        setTitle("Consulta de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Definir columnas
        String[] columnas = {"ID Cliente", "Nombre", "Correo", "Tipo"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modelo);

        // Cargar datos desde BD
        cargarClientes();

        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
    }

    private void cargarClientes() {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT idCliente, nombre, correo, tipoCliente FROM Cliente";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("tipoCliente")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar clientes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ConsultaCliente().setVisible(true);
    }
}
