package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.io.*;
import dao.ConexionBD;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

public class VentanaDashboard extends JFrame {
    private JTable tablaClientes;
    private DefaultTableModel modelo;
    private JFreeChart chart;

    public VentanaDashboard() {
        setTitle("Dashboard de Ventas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabla con reporte de clientes
        String[] columnas = {"ID Cliente", "Cliente", "Tipo", "Compras", "Total Gastado"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modelo);
        cargarDatosClientes();

        JScrollPane scrollTabla = new JScrollPane(tablaClientes);

        // Panel con gráfico
        JPanel panelGrafico = new JPanel(new BorderLayout());
        ChartPanel chartPanel = crearGraficoTopClientes();
        panelGrafico.add(chartPanel, BorderLayout.CENTER);

        // Dividir pantalla
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, panelGrafico);
        splitPane.setDividerLocation(300);

        add(splitPane, BorderLayout.CENTER);

        // Botones de exportación
        JPanel panelBotones = new JPanel();
        JButton btnPDF = new JButton("Exportar PDF");
        panelBotones.add(btnPDF);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnPDF.addActionListener(e -> exportarPDF("ReporteDashboard"));
    }

    private void cargarDatosClientes() {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT c.idCliente, c.nombre, c.tipoCliente, " +
                         "COUNT(v.idVenta) AS Compras, SUM(d.subtotal) AS TotalGastado " +
                         "FROM Cliente c " +
                         "JOIN Venta v ON c.idCliente = v.idCliente " +
                         "JOIN DetalleVenta d ON v.idVenta = d.idVenta " +
                         "GROUP BY c.idCliente, c.nombre, c.tipoCliente " +
                         "ORDER BY TotalGastado DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("tipoCliente"),
                    rs.getInt("Compras"),
                    rs.getDouble("TotalGastado")
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ChartPanel crearGraficoTopClientes() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT c.nombre, SUM(d.subtotal) AS TotalGastado " +
                         "FROM Cliente c " +
                         "JOIN Venta v ON c.idCliente = v.idCliente " +
                         "JOIN DetalleVenta d ON v.idVenta = d.idVenta " +
                         "GROUP BY c.nombre " +
                         "ORDER BY TotalGastado DESC LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dataset.addValue(rs.getDouble("TotalGastado"), "Gasto", rs.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar gráfico: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        chart = ChartFactory.createBarChart(
                "Top 5 Clientes por Gasto",
                "Cliente",
                "Total Gastado",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return new ChartPanel(chart);
    }

    // Exportar a PDF
    private void exportarPDF(String nombreArchivo) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo + ".pdf"));
            document.open();

            document.add(new Paragraph("Reporte Dashboard de Ventas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(" "));

            // Tabla
            PdfPTable table = new PdfPTable(modelo.getColumnCount());
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                table.addCell(new PdfPCell(new Phrase(modelo.getColumnName(i))));
            }
            for (int i = 0; i < modelo.getRowCount(); i++) {
                for (int j = 0; j < modelo.getColumnCount(); j++) {
                    table.addCell(modelo.getValueAt(i, j).toString());
                }
            }
            document.add(table);

            document.add(new Paragraph(" "));

            // Gráfico
            ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
            ChartUtils.writeChartAsPNG(chartOut, chart, 500, 300); // Usa ChartUtils
            Image chartImage = Image.getInstance(chartOut.toByteArray());
            document.add(chartImage);

            document.close();
            JOptionPane.showMessageDialog(this, "Exportado a PDF correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar PDF: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new VentanaDashboard().setVisible(true);
    }
}
