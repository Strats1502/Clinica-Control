package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by esva on 25/04/17.
 */
public class CustomVista extends JPanel {

    public CustomVista() {
        propiedades();
    }

    private void propiedades() {
        this.setLayout(null);
        this.setBounds(0,0,800,500);
    }

    public static JLabel fondo() {
        JLabel fondo = new JLabel();
        fondo.setIcon(establecerImagen("fondoGeneral", 800, 500));
        fondo.setBounds(0 ,0 ,800, 500);
        return fondo;
    }


    public static Icon establecerIcono(String archivo,int ancho, int alto) {
        ImageIcon imagen = new ImageIcon("src/Iconos/icn_" + archivo + ".png");
        Icon i = new ImageIcon(imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
        return i;
    }

    public static Icon establecerImagen(String archivo,int ancho, int alto) {
        ImageIcon imagen = new ImageIcon("src/Imagenes/" + archivo + ".png");
        Icon i = new ImageIcon(imagen.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
        return i;
    }

    public static Icon establecerImagenPath(String archivo) {
        ImageIcon imagen = new ImageIcon(archivo);
        Icon i = new ImageIcon(imagen.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT));
        return i;
    }

    public static Font appleFont() {
        try {
            Font appleFont = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream(new File("src/Fuentes/MYRIADAT.TTF"))).
                    deriveFont(Font.PLAIN, 16);
            return appleFont;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
