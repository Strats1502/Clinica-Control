package Contenedor;

import Conexion.ConexionSQL;
import Vistas.*;

import javax.swing.*;

/**
 * Created by esva on 9/05/17.
 */
public class Contenedor extends JFrame {
    public static InicioSesionVista inicioSesionVista = new InicioSesionVista();
    public static HomeVista homeVista = new HomeVista();
    public static UsuarioVista usuarioVista = new UsuarioVista();
    public static ProductoRegistroVista productoRegistroVista = new ProductoRegistroVista();
    public static ProductoLlegadaMercanciaVista productoLlegadaMercanciaVista = new ProductoLlegadaMercanciaVista();

    public Contenedor() {
        this.setIconImage(new ImageIcon("src/Iconos/icn_logo.png").getImage());
        this.setTitle("Java sucks...");
        this.setSize(800,500);
        this.setVisible(true);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(inicioSesionVista.noEsContraseña);
        this.add(inicioSesionVista.noExiste);
        this.add(inicioSesionVista.noEsCorreo);
        this.add(usuarioVista.errorNoIguales);
        this.add(usuarioVista.errorContraseña2);
        this.add(usuarioVista.errorContraseña1);
        this.add(usuarioVista.errorNoEsCorreo);
        this.add(usuarioVista.errorCorreo);
        this.add(usuarioVista.errorApellidoMaterno);
        this.add(usuarioVista.errorApellidoPaterno);
        this.add(usuarioVista.errorNombre);
        this.add(usuarioVista.errorRol);
        this.add(usuarioVista.listaBuscador);
        this.add(productoRegistroVista.listaPresentacion);
        this.add(productoRegistroVista.listaBuscador);
        this.add(productoLlegadaMercanciaVista.listaBuscador);
        this.add(inicioSesionVista);
        //this.add(homeVista);
        //this.add(usuarioVista);
        this.add(productoRegistroVista);
        this.add(productoLlegadaMercanciaVista);
        //this.add(productoRegistroVista);
    }

    public static void main(String[] args) {
        Contenedor c = new Contenedor();
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
