package Modelos;

/**
 * Created by Juan José Estrada Valtierra on 22/04/17.
 */
public class Producto {
    private int id;
    private int usuarioAlta;
    private String fechaAlta;
    private String nombre;
    private String proveedor;
    private double costo;
    private double precio;
    private String presentacion;
    private boolean activo;

    public Producto(int id, int usuarioAlta, String fechaAlta, String nombre, String proveedor, double costo, double precio, String presentacion, boolean activo) {
        this.id = id;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = fechaAlta;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.costo = costo;
        this.precio = precio;
        this.presentacion = presentacion;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(int usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
