package Vistas;

import ComponentesBeauty.BeautyComboBox;
import InterfazUsuario.Home;

import javax.swing.*;

/**
 * Created by esva on 25/04/17.
 */
public class BaseDatosVista extends JPanel{

    private static JPanel panelBaseDatos() {
        JPanel panelBaseDatos = new JPanel(null);
        panelBaseDatos.setBounds(0,0,800,500);
        JLabel lblFondo = new JLabel(Home.establecerImagen("fondoGeneral",800,500));

        BeautyComboBox comboTabla = new BeautyComboBox("Tabla",350,50,150,30);

        panelBaseDatos.add(comboTabla);
        panelBaseDatos.add(lblFondo);
        return panelBaseDatos;
    }

    public static void main(String[] args) {

    }

}
