package Pruebas;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EjemploTabla extends JFrame {

    public EjemploTabla() {
        String[] columnas = {"Nombre", "Primer Apellido", "Segundo Apellido"};
        String[][] datos = {{"Juan", "Barroso", "Hernández"},
                {"Carlos", "Camacho", "Carreras"},
                {"Joel", "Mojica", "Serrano"},
                {"Juan", "Barroso", "Hernández"},
                {"Juan", "Barroso", "Hernández"},
                {"Juan", "Barroso", "Hernández"},};
        JTable tabla = new JTable(datos, columnas);
        JScrollPane scroll = new JScrollPane(tabla);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    public static void main (String ars[]){
        EjemploTabla prueba = new EjemploTabla();
        prueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prueba.setSize(400, 300);
        prueba.setVisible(true);
    }
}