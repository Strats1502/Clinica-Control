package ComponentesBeauty;

import Vistas.CustomVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by esva on 9/05/17.
 */
public class BeautyList extends JList {

    public BeautyList(DefaultListModel dlm, int x, int y, int ancho, int alto) {
        this.setModel(dlm);
        this.setForeground(Color.WHITE);
        this.setFont(CustomVista.appleFont());
        this.setBackground(new Color(0, 0, 0, 198));
        this.setVisible(false);
        this.setBounds(x, y, ancho, alto);
    }

}
