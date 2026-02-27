package vista;

import javax.swing.*;
import java.awt.*;
import dao.ReporteClienteDAO;

public class ReporteCliente extends JFrame {
    private JTable tablaClientes;
    private ReporteClienteDAO dao;
    
    public ReporteCliente() {
        setTitle("Reporte de Clientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new ReporteClienteDAO();
        tablaClientes = new JTable(dao.obtenerVentasPorCliente());

        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new ReporteCliente().setVisible(true);
    }
}
