package modelo;

import java.util.*;

public class Venta {
    private int idVenta;
    private Cliente cliente;
    private Date fecha;
    private List<DetalleVenta> detalles;

    public Venta(int idVenta, Cliente cliente) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.fecha = new Date();
        this.detalles = new ArrayList<>();
    }

    public int getIdVenta() { return idVenta; }
    public Cliente getCliente() { return cliente; }
    public Date getFecha() { return fecha; }
    public List<DetalleVenta> getDetalles() { return detalles; }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
    }

    public double calcularTotal() {
        return detalles.stream().mapToDouble(DetalleVenta::calcularSubtotal).sum();
    }

    public void mostrarVenta() {
        System.out.println("Venta #" + idVenta + " Cliente: " + cliente.getNombre());
        for (DetalleVenta d : detalles) {
            System.out.println(" - " + d.getProducto().getNombre() + " x " + d.getCantidad());
        }
        System.out.println("Total: " + calcularTotal());
    }
}
