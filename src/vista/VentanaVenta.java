package vista;

import modelo.Cliente;
import modelo.DetalleVenta;
import modelo.Producto;
import modelo.Venta;
import dao.VentaDAO;
import dao.ClienteDAO;
import dao.ProductoDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class VentanaVenta extends JFrame {
    private JComboBox<Cliente> comboClientes;
    private JComboBox<Producto> comboProductos;
    private JTextField txtCantidad;
    private JButton btnAgregar, btnRegistrar;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaDetalles;
    private List<DetalleVenta> detalles = new ArrayList<>();

    public VentanaVenta() {
        setTitle("Registrar Venta");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelTop = new JPanel(new GridLayout(3,2));

        // Cargar clientes y productos desde BD
        comboClientes = new JComboBox<>();
        cargarClientes();

        comboProductos = new JComboBox<>();
        cargarProductos();

        txtCantidad = new JTextField();

        panelTop.add(new JLabel("Cliente:")); panelTop.add(comboClientes);
        panelTop.add(new JLabel("Producto:")); panelTop.add(comboProductos);
        panelTop.add(new JLabel("Cantidad:")); panelTop.add(txtCantidad);

        // Botones
        btnAgregar = new JButton("Agregar Detalle");
        btnRegistrar = new JButton("Registrar Venta");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnRegistrar);

        // Lista detalles
        modeloLista = new DefaultListModel<>();
        listaDetalles = new JList<>(modeloLista);

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(listaDetalles), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnAgregar.addActionListener(e -> {
            Producto p = (Producto) comboProductos.getSelectedItem();
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (cantidad > p.getStock()) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente para " + p.getNombre());
                return;
            }

            DetalleVenta d = new DetalleVenta(p, cantidad);
            detalles.add(d);

            modeloLista.addElement(p.getNombre() + " x " + cantidad + " = $" + d.calcularSubtotal());
            txtCantidad.setText("");
        });

        btnRegistrar.addActionListener(e -> {
            Cliente c = (Cliente) comboClientes.getSelectedItem();
            Venta v = new Venta(0, c);
            for (DetalleVenta d : detalles) v.agregarDetalle(d);

            VentaDAO dao = new VentaDAO();
            dao.registrarVenta(v);

            JOptionPane.showMessageDialog(this, "Venta registrada con éxito.");
            detalles.clear();
            modeloLista.clear();
        });
    }

    private void cargarClientes() {
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clientes = clienteDAO.obtenerClientes();
        for (Cliente c : clientes) {
            comboClientes.addItem(c);
        }
    }

    private void cargarProductos() {
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.obtenerProductos();
        for (Producto p : productos) {
            comboProductos.addItem(p);
        }
    }

    public static void main(String[] args) {
        new VentanaVenta().setVisible(true);
    }
}
