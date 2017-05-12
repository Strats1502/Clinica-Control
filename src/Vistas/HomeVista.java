package Vistas;

import javax.swing.*;

/**
 * Created by esva on 6/05/17.
 */
public class HomeVista extends CustomVista {
    private static JLabel lblFondo;
    private static int contador = 1;

    public HomeVista() {
        this.setLayout(null);
        this.setBounds(0,0,800,500);
        this.setOpaque(true);

        lblFondo = new JLabel();
        lblFondo.setBounds(0, 0, 800, 500);
        lblFondo.setIcon(establecerImagen("1", 800, 500));

        cambiarImagen();
        this.add(lblFondo);
    }

    private static void cambiarImagen() {
        Timer timer = new Timer(2000, (ActionEvent -> {
            String cad = String.valueOf(contador);
            lblFondo.setIcon(establecerImagen(cad, 800, 500));
            contador ++;
            if (contador == 4) contador = 1;
        }));
        timer.start();
    }

}
