package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dao.ConexionBD;

public class AgregaProducto extends JFrame {
    private JTextField txtNombre, txtPrecio, txtCategoria, txtStock;
    private JButton btnGuardar, btnCancelar;

    public AgregaProducto() {
        setTitle("Alta de Producto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5,2,10,10));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        add(txtPrecio);

        add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        add(txtCategoria);

        add(new JLabel("Stock:"));
        txtStock = new JTextField();
        add(txtStock);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        add(btnGuardar);
        add(btnCancelar);

        btnGuardar.addActionListener(e -> guardarProducto());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarProducto() {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO Producto (nombre, precio, categoria, stock) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtNombre.getText());
            ps.setDouble(2, Double.parseDouble(txtPrecio.getText()));
            ps.setString(3, txtCategoria.getText());
            ps.setInt(4, Integer.parseInt(txtStock.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Producto registrado correctamente");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar producto: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AgregaProducto().setVisible(true);
    }
}
