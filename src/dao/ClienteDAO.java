package dao;

import modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT idCliente, nombre, correo, tipoCliente FROM Cliente";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("tipoCliente")
                );
                clientes.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
