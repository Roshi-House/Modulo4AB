
import javax.swing.*;
import java.awt.*;
import vista.*;

public class MainMenu extends JFrame {
    private JButton btnClientes, btnProductos, btnVentas, btnReportes, btnDashboard, btnSalir;

    public MainMenu() {
        setTitle("Sistema de Tienda - Menú Principal");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de...");
        itemAcerca.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Sistema de Tienda v1.0\nDesarrollado en Java + MySQL\nRoberto Cazares Centeno",
            "Acerca de", JOptionPane.INFORMATION_MESSAGE));
        menuAyuda.add(itemAcerca);

        JMenu menuClientes = new JMenu("Clientes");
        JMenuItem itemAltaCliente = new JMenuItem("Alta Cliente");
        itemAltaCliente.addActionListener(e -> new AltaCliente().setVisible(true));
        menuClientes.add(itemAltaCliente);

        JMenuItem itemConsultaCliente = new JMenuItem("Consulta Cliente");
        itemConsultaCliente.addActionListener(e -> new ConsultaCliente().setVisible(true));
        menuClientes.add(itemConsultaCliente);

        JMenu menuProductos = new JMenu("Productos");
        JMenuItem itemAltaProducto = new JMenuItem("Alta Producto");
        itemAltaProducto.addActionListener(e -> new AgregaProducto().setVisible(true));
        menuProductos.add(itemAltaProducto);

        JMenuItem itemConsultaProducto = new JMenuItem("Consulta Producto");
        itemConsultaProducto.addActionListener(e -> new ConsultaProducto().setVisible(true));
        menuProductos.add(itemConsultaProducto);

        JMenu menuVentas = new JMenu("Ventas");
        JMenuItem itemAltaVenta = new JMenuItem("Alta Venta");
        itemAltaVenta.addActionListener(e -> new AltaVenta().setVisible(true));
        menuVentas.add(itemAltaVenta);

        JMenuItem itemConsultaVenta = new JMenuItem("Consulta Venta");
        itemConsultaVenta.addActionListener(e -> new ConsultaVenta().setVisible(true));
        menuVentas.add(itemConsultaVenta);

        JMenuItem itemConsultaDetalle = new JMenuItem("Consulta Detalle Venta");
        itemConsultaDetalle.addActionListener(e -> new ConsultaDetalleVenta().setVisible(true));
        menuVentas.add(itemConsultaDetalle);

        menuBar.add(menuArchivo);
        menuBar.add(menuClientes);
        menuBar.add(menuProductos);
        menuBar.add(menuVentas);
        menuBar.add(menuAyuda);
        setJMenuBar(menuBar);

        // Panel principal con GridLayout
        JPanel panelBotones = new JPanel(new GridLayout(2,3,20,20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panelBotones.setBackground(new Color(240,240,250));

        // Botones con iconos
        btnClientes = crearBoton("Clientes", "src/icons/clientes.png");
        btnProductos = crearBoton("Productos", "src/icons/productos.png");
        btnVentas = crearBoton("Realizar Venta", "src/icons/ventas.png");
        btnReportes = crearBoton("Reporte", "src/icons/reportes.png");
        btnDashboard = crearBoton("Dashboard", "src/icons/dashboard.png");
        btnSalir = crearBoton("Salir", "src/icons/salir.png");

        // Agregar botones al panel
        panelBotones.add(btnClientes);
        panelBotones.add(btnProductos);
        panelBotones.add(btnVentas);
        panelBotones.add(btnReportes);
        panelBotones.add(btnDashboard);
        panelBotones.add(btnSalir);

        add(panelBotones);

        // Eventos
        btnClientes.addActionListener(e -> new ReporteCliente().setVisible(true));
        btnProductos.addActionListener(e -> new ConsultaProducto().setVisible(true));
        btnVentas.addActionListener(e -> new VentanaVenta().setVisible(true));
        btnReportes.addActionListener(e -> new VentanaReporte().setVisible(true));
        btnDashboard.addActionListener(e -> new VentanaDashboard().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private JButton crearBoton(String texto, String iconPath) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(200,220,240));
        boton.setFocusPainted(false);
        boton.setIcon(new ImageIcon(iconPath)); // Ruta de icono
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
