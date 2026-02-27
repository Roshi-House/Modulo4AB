package modelo;

public class Producto {
    private int idProducto;
    private String nombre;
    private double precio;
    private String categoria;
    private int stock;

    @Override
    public String toString() {
        return nombre + " ($" + precio + ")"; 
    }

    public Producto(int idProducto, String nombre, double precio, String categoria, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
    }

    public int getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
    public int getStock() { return stock; }

    public void setStock(int cantidad) { this.stock = cantidad; }
    public void actualizarStock(int cantidad) { this.stock += cantidad; }

    public void mostrarInfo() {
        System.out.println("Producto: " + nombre + " | Precio: " + precio + " | Stock: " + stock);
    }
}
