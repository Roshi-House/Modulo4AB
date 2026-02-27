package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dao.ConexionBD;

public class AltaVenta extends JFrame {
    private JTextField txtIdCliente, txtIdProducto, txtCantidad;
    private JButton btnGuardar, btnCancelar;

    public AltaVenta() {
        setTitle("Registrar Venta");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4,2,10,10));

        add(new JLabel("ID Cliente:"));
        txtIdCliente = new JTextField();
        add(txtIdCliente);

        add(new JLabel("ID Producto:"));
        txtIdProducto = new JTextField();
        add(txtIdProducto);

        add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        add(txtCantidad);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        add(btnGuardar);
        add(btnCancelar);

        btnGuardar.addActionListener(e -> guardarVenta());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarVenta() {
        try (Connection conn = ConexionBD.getConnection()) {
            // Insertar venta
            String sqlVenta = "INSERT INTO Venta (fecha, idCliente) VALUES (CURDATE(), ?)";
            PreparedStatement psVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setInt(1, Integer.parseInt(txtIdCliente.getText()));
            psVenta.executeUpdate();

            ResultSet rs = psVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rs.next()) idVenta = rs.getInt(1);

            // Insertar detalle
            String sqlDetalle = "INSERT INTO DetalleVenta (idVenta, idProducto, cantidad, subtotal) VALUES (?,?,?,?)";
            PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle);
            psDetalle.setInt(1, idVenta);
            psDetalle.setInt(2, Integer.parseInt(txtIdProducto.getText()));
            int cantidad = Integer.parseInt(txtCantidad.getText());
            psDetalle.setInt(3, cantidad);

            // Obtener precio del producto
            String sqlPrecio = "SELECT precio FROM Producto WHERE idProducto=?";
            PreparedStatement psPrecio = conn.prepareStatement(sqlPrecio);
            psPrecio.setInt(1, Integer.parseInt(txtIdProducto.getText()));
            ResultSet rsPrecio = psPrecio.executeQuery();
            double precio = 0;
            if (rsPrecio.next()) precio = rsPrecio.getDouble("precio");

            psDetalle.setDouble(4, precio * cantidad);
            psDetalle.executeUpdate();

            JOptionPane.showMessageDialog(this, "Venta registrada correctamente");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar venta: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AltaVenta().setVisible(true);
    }
}
