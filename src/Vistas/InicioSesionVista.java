package Vistas;

import ComponentesBeauty.BeautyBlackButton;
import ComponentesBeauty.BeautyErrorMessage;
import ComponentesBeauty.BeautyPasswordField;
import ComponentesBeauty.BeautyTextField;
import Conexion.ComprobarSQL;
import Contenedor.Contenedor;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by esva on 6/05/17.
 */
public class InicioSesionVista extends CustomVista {
    public  BeautyErrorMessage noEsCorreo = new BeautyErrorMessage("No es correo");
    public  BeautyErrorMessage noExiste= new BeautyErrorMessage("Usuario no existe");
    public  BeautyErrorMessage noEsContraseña = new BeautyErrorMessage("Usuario existe pero contraseña invalida");

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public InicioSesionVista() {
        this.setLayout(null);
        BeautyTextField txtCorreo = new BeautyTextField("Correo", 260,240 , 250, 30);
        BeautyPasswordField txtPassword = new BeautyPasswordField("Contraseña", 260, 310, 250, 30);
        BeautyBlackButton btnIniciarSesion = new BeautyBlackButton("Iniciar Sesión", 330, 375, 110,30);

        JLabel icono = new JLabel();
        icono.setIcon(establecerIcono("logo", 30,90));
        icono.setBounds(370,80, 30, 90);

        btnIniciarSesion.addActionListener((ActionEvent) -> {
            String correo = txtCorreo.getText();
            String contraseña = txtPassword.getText();
            acceso(correo, contraseña);
        });

        this.add(icono);
        this.add(txtCorreo);
        this.add(txtPassword);
        this.add(btnIniciarSesion);
        this.add(fondo());
    }


    private void acceso(String correo, String contraseña) {
        if(esCorreo(correo)) {
            if(ComprobarSQL.existeCorreo(correo)) {
                if(ComprobarSQL.existeContraseña(correo, contraseña)) {
                    //System.out.println("Acceso completado");
                    this.setVisible(false);
                } else {
                    noEsContraseña.setVisible(true);
                }
            } else {
                noExiste.setVisible(true);
            }
        } else {
            noEsCorreo.setVisible(true);
        }
    }

    private boolean esCorreo(String correo) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }


    /*
    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(noEsCorreo);
        f.add(noExiste);
        f.add(noEsContraseña);
        f.add(new InicioSesionVista());
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(3);
        f.setSize(800,500);
        f.setVisible(true);

    }*/

}
