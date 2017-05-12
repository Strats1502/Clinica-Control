package Vistas;

import ComponentesBeauty.*;
import Conexion.ConexionSQL;
import Conexion.InsertarSQL;
import Contenedor.Contenedor;

import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by esva on 9/05/17.
 */
public class ProductoLlegadaMercanciaVista extends CustomVista {
    //Componentes
    BeautyTextField txtBuscar;
    BeautyImageButton btnNuevo;
    BeautyImageButton btnEditar;
    BeautyImageButton btnLlegadaMercancia;
    BeautyBlackButton btnGuardar;
    BeautyImageButton btnImagenProducto;
    BeautyComboBox comboBoxPresentacion;
    BeautyTextField txtNombre;
    BeautyTextField txtCantidad;


    //Lista de busqueda
    public static DefaultListModel dlmBuscador = new DefaultListModel();
    public static BeautyList listaBuscador = new BeautyList(dlmBuscador, 300, 150, 300, 140);

    //Variables
    String opcionBusqueda = "";
    int id = 0;

    //Constructor
    public ProductoLlegadaMercanciaVista() {
        componentes();
    }

    //Inicia componentes
    private void componentes() {
        //Componentes
        btnImagenProducto = new BeautyImageButton(establecerIcono("Producto", 150, 150), 40, 70, 150, 150);
        comboBoxPresentacion = new BeautyComboBox("Presentación", 40, 250, 120, 20);
        txtNombre = new BeautyTextField("Nombre", 60, 290, 200, 20);
        txtCantidad = new BeautyTextField("Cantidad", 60, 390, 200, 20);
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);
        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        btnLlegadaMercancia = new BeautyImageButton(establecerIcono("LlegadaMercancia", 20, 20), 700, 130, 20, 20);

        comboBoxPresentacion.setEnabled(false);
        txtNombre.setEnabled(false);

        txtBuscar.addKeyListener(new Buscador());
        listaBuscador.addMouseListener(new ListaBuscadorElemento());
        btnNuevo.addActionListener(new BotonNuevo());
        btnGuardar.addActionListener(new BotonGuardar());

        this.add(btnImagenProducto);
        this.add(comboBoxPresentacion);
        this.add(txtNombre);
        this.add(txtCantidad);
        this.add(txtBuscar);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(btnLlegadaMercancia);
        this.add(btnGuardar);
        this.add(fondo());
    }

    /**
     * Clases de accion a componentes
     */
    private class Buscador extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char tecla = e.getKeyChar();
            String cadena = txtBuscar.getText() + tecla;
            buscarProducto(cadena);
        }
    }

    private class ListaBuscadorElemento extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            opcionBusqueda = listaBuscador.getSelectedValue().toString();
            establecerDatos(opcionBusqueda);
            listaBuscador.setVisible(false);
            txtBuscar.setText("");
        }
    }

    private class BotonGuardar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = txtNombre.getText();
            String presentacion = comboBoxPresentacion.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            buscarID(nombre, presentacion);
            System.out.println(id);
            InsertarSQL.insertarInventario(id, cantidad);
            btnImagenProducto.setIcon(establecerIcono("Producto",150, 150));
            txtNombre.setText("Nombre");
            txtCantidad.setText("Cantidad");
        }
    }

    private class BotonNuevo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            Contenedor.productoRegistroVista.setVisible(true);
            //contenedor.productoRegistroVista.setVisible(true);
        }
    }

    /**
     * Métodos
     */
    private void buscarID(String nombre, String presentacion) {
        String sql = "SELECT id FROM Producto WHERE nombre = ? and presentacion = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, presentacion);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                id = Integer.parseInt(rs.getObject("id").toString());
            }
        } catch (SQLException sqlException) {

        }
    }

    private void buscarProducto (String cadena) {
        dlmBuscador.removeAllElements();
        listaBuscador.setVisible(true);
        String sql = "SELECT nombre, activo FROM Producto WHERE nombre LIKE ?";
        int resultados = 0;
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, cadena + "%");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                resultados++;
                String nombre = rs.getObject("nombre").toString();
                boolean activo = rs.getBoolean("activo");
                if(activo) {
                    dlmBuscador.addElement(nombre);
                } else {

                }
            }
        } catch (SQLException sqlException) {

        }
    }

    private void establecerDatos (String opcionBusqueda) {
        String sql = "SELECT * FROM Producto WHERE nombre = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, opcionBusqueda);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                String pathFoto = rs.getObject("path_foto").toString();
                String presentacion = rs.getObject("presentacion").toString();
                String nombre = rs.getObject("nombre").toString();

                //Le pone los datos a los componentes de la opcion seleccionada
                btnImagenProducto.setIcon(establecerImagenPath(pathFoto));
                comboBoxPresentacion.setText(presentacion);
                txtNombre.setText(nombre);

                //Evita que se editen los datos
                btnImagenProducto.setEnabled(false);
                comboBoxPresentacion.setEnabled(false);
                txtNombre.setEnabled(false);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }


    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(listaBuscador);
        f.add(new ProductoLlegadaMercanciaVista());
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(3);
        f.setSize(800, 500);
        f.setVisible(true);

    }

}
