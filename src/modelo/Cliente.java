package modelo;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String correo;
    private String tipoCliente;
    
    @Override 
    public String toString() {
        return nombre;
    }

    public Cliente(int idCliente, String nombre, String correo, String tipoCliente) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.tipoCliente = tipoCliente;
    }

    public int getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getTipoCliente() { return tipoCliente; }

    public void registrarCliente() {
        System.out.println("Cliente registrado: " + nombre);
    }

    public void mostrarInfo() {
        System.out.println("Cliente: " + nombre + " | Tipo: " + tipoCliente);
    }
}

class ClienteNormal extends Cliente {
    public ClienteNormal(int id, String nombre, String correo) {
        super(id, nombre, correo, "Normal");
    }
}

class ClienteFrecuente extends Cliente {
    public ClienteFrecuente(int id, String nombre, String correo) {
        super(id, nombre, correo, "Frecuente");
    }
    public double descuento() { return 0.10; }
}

class ClienteVIP extends Cliente {
    public ClienteVIP(int id, String nombre, String correo) {
        super(id, nombre, correo, "VIP");
    }
    public double descuento() { return 0.20; }
}
