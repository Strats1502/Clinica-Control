package Modelos;

import java.util.ArrayList;

/**
 * Created by esva on 22/04/17.
 */
public class Venta {
    private int id;
    private int usuarioAlta;
    private String fechaAlta;
    private String lugarExpedicion;
    private String formaPago;
    private double subtotal;
    private double iva;
    private double total;
    private boolean activo;
    private ArrayList<Producto> productos;
    private ArrayList<Consulta> consultas;

    public Venta(int id, int usuarioAlta, String fechaAlta, String lugarExpedicion, String formaPago, double subtotal, double iva, double total, boolean activo, ArrayList<Producto> productos, ArrayList<Consulta> consultas) {
        this.id = id;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = fechaAlta;
        this.lugarExpedicion = lugarExpedicion;
        this.formaPago = formaPago;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.activo = activo;
        this.productos = productos;
        this.consultas = consultas;
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

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
    }
}
