package vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dao.ConexionBD;

public class AltaCliente extends JFrame {
    private JTextField txtNombre, txtCorreo;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar, btnCancelar;

    public AltaCliente() {
        setTitle("Alta de Cliente");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4,2,10,10));

        // Campos
        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        add(txtCorreo);

        add(new JLabel("Tipo de Cliente:"));
        cmbTipo = new JComboBox<>(new String[]{"Normal","Frecuente","VIP"});
        add(cmbTipo);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        add(btnGuardar);
        add(btnCancelar);

        // Eventos
        btnGuardar.addActionListener(e -> guardarCliente());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String tipo = (String) cmbTipo.getSelectedItem();

        if(nombre.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO Cliente (nombre, correo, tipoCliente) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, tipo);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cliente registrado correctamente");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AltaCliente().setVisible(true);
    }
}
