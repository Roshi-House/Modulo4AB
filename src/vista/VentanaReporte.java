package vista;

import javax.swing.*;
import dao.ReporteDAO;
import java.awt.*;

public class VentanaReporte extends JFrame {
    private JTable tablaVentas;
    private ReporteDAO dao;

    public VentanaReporte() {
        setTitle("Reporte de Ventas");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new ReporteDAO();
        tablaVentas = new JTable(dao.obtenerVentas());

        add(new JScrollPane(tablaVentas), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new VentanaReporte().setVisible(true);
    }
}
