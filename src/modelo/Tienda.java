package modelo;

import java.util.*;

public class Tienda {
    @SuppressWarnings("unused")
    private String nombre;
    private List<Cliente> clientes;
    @SuppressWarnings("unused")
    private Inventario inventario;
    private List<Venta> ventas;

    public Tienda(String nombre) {
        this.nombre = nombre;
        clientes = new ArrayList<>();
        inventario = new Inventario();
        ventas = new ArrayList<>();
    }

    public void registrarVenta(Venta v) {
        ventas.add(v);
    }

    public void registrarCliente(Cliente c) {
        clientes.add(c);
    }

    public void mostrarClientesFrecuentes() {
        clientes.stream()
                .filter(c -> c instanceof ClienteFrecuente || c instanceof ClienteVIP)
                .forEach(Cliente::mostrarInfo);
    }
}
