package Pruebas;

import Conexion.ConexionSQL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaPrueba2 extends JFrame{
    String[] columnas = {"Nombre", "Primer Apellido", "Segundo Apellido"};
    public TablaPrueba2(){
        super("Ejemplo 2 DTM");
        //String[] columnas = {"Nombre", "Primer Apellido", "Segundo Apellido"};
        String[][] datos = {{"Juan", "Barroso", "Hernández"},
                {"Carlos", "Camacho", "Carreras"},
                {"Joel", "Mojica", "Serrano"},
                {"Juan", "Barroso", "Hernández"},
                {"Juan", "Barroso", "Hernández"},
                {"Juan", "Barroso", "Hernández"},
                {"Juan", "Barroso", "Hernández"},
                {"Juan", "Barroso", "Hernández"},
        };


        DefaultTableModel dtm = new DefaultTableModel(datos,columnas);

        JTable tabla = new JTable(dtm);
        String[] nuevoColumna = {"IGE", "ISC", "IGE","IND","ISC","IE","LOG","TICS"};
        dtm.addColumn("Carrera", nuevoColumna);

        Object [] nuevoRegistro = {"Vania","","Papadaolis","IGE"};
        dtm.addRow(nuevoRegistro);

        dtm.setValueAt("Donald", 6, 0);

        tabla.setPreferredScrollableViewportSize(new Dimension(500,70));
        JScrollPane scroll = new JScrollPane(tabla);
        getContentPane().add(scroll, BorderLayout.CENTER);
        //ConexionSQL s = new ConexionSQL();
    }


    public static void main(String[] ar ){
        TablaPrueba2 objTabla = new TablaPrueba2();
        objTabla.pack();
        objTabla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        objTabla.setVisible(true);
    }

}