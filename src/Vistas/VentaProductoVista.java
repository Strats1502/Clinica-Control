package Vistas;

import ComponentesBeauty.*;
import Conexion.ConexionSQL;
import Conexion.InsertarSQL;

import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by esva on 15/05/17.
 */
public class VentaProductoVista extends CustomVista {
    //Componentes
    BeautyTextField txtBuscar;
    BeautyImageButton btnNuevo;
    BeautyImageButton btnEditar;
    BeautyImageButton btnOpciones;
    BeautyImageButton btnFactura;
    BeautyBlackButton btnGuardar;

    BeautyTextField txtFolio;
    BeautyTextField txtNombreProducto;
    BeautyComboBox comboPresentacion;
    BeautyTextField txtCantidad;

    //Lista de busqueda
    public static DefaultListModel dlmBuscador = new DefaultListModel();
    public static BeautyList listaBuscador = new BeautyList(dlmBuscador, 300, 150, 300, 140);

    //Lista de producto
    public static DefaultListModel dlmProducto = new DefaultListModel();
    public static BeautyList listaProducto = new BeautyList(dlmProducto, 60,350,200, 100);

    //Lista de producto
    public static DefaultListModel dlmPresentacion = new DefaultListModel();
    public static BeautyList listaPresentacion = new BeautyList(dlmPresentacion, 360,350,100, 70);

    //Variables
    String opcionBusqueda = null;
    int id = 0;
    int idProducto = 0;


    public VentaProductoVista() {
        componentes();
    }

    private void componentes() {
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        btnOpciones = new BeautyImageButton(establecerIcono("LlegadaMercancia", 20, 20), 700, 130, 20, 20);
        btnFactura = new BeautyImageButton(establecerIcono("Factura", 20, 20), 740, 130, 20, 20);
        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);

        txtFolio = new BeautyTextField("Folio", 60, 210, 200, 30);
        txtNombreProducto = new BeautyTextField("Producto",60, 330, 200,20);
        comboPresentacion = new BeautyComboBox("Presentación", 310, 330, 200, 20);
        txtCantidad = new BeautyTextField("Cantidad", 570, 330, 200, 20);

        txtFolio.setEnabled(false);
        txtNombreProducto.setEnabled(false);
        comboPresentacion.setEnabled(false);
        txtCantidad.setEnabled(false);
        btnGuardar.setEnabled(false);

        txtBuscar.addFocusListener(new TXTBuscarFocusAdapter());
        txtBuscar.addKeyListener(new TXTBuscarKeyAdapter());
        listaBuscador.addMouseListener(new ListaBuscarMouseAdapter());
        txtNombreProducto.addFocusListener(new TXTNombreProductoFocusAdapter());
        txtNombreProducto.addKeyListener(new TXTNombreProductoKeyAdapter());
        listaProducto.addMouseListener(new ListaProductoMouseAdapter());
        comboPresentacion.addFocusListener(new ComboPresentacionFocusAdapter());
        listaPresentacion.addMouseListener(new ListaPresentacionMouseAdapter());
        btnGuardar.addActionListener(new BotonGuardar());

        this.add(txtBuscar);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(btnOpciones);
        this.add(btnFactura);
        this.add(btnGuardar);
        this.add(txtFolio);
        this.add(txtNombreProducto);
        this.add(comboPresentacion);
        this.add(txtCantidad);
        this.add(fondo());
    }


    private class TXTBuscarFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaBuscador.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaBuscador.setVisible(false);
        }
    }

    private class TXTBuscarKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char tecla = e.getKeyChar();
            String cadena = txtBuscar.getText() + tecla;
            buscarVenta(cadena);
        }
    }

    private class ListaBuscarMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            opcionBusqueda = listaBuscador.getSelectedValue().toString();
            establecerDatos(opcionBusqueda);
            listaBuscador.setVisible(false);
            txtNombreProducto.setEnabled(true);
            comboPresentacion.setEnabled(true);
            txtCantidad.setEnabled(true);
            btnGuardar.setEnabled(true);
            txtBuscar.setText("");
        }
    }

    private class TXTNombreProductoFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaProducto.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaProducto.setVisible(false);
        }
    }

    private class TXTNombreProductoKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char tecla = e.getKeyChar();
            String cadena = txtNombreProducto.getText() + tecla;
            buscarProducto(cadena);
        }
    }

    private class ListaProductoMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            opcionBusqueda = listaProducto.getSelectedValue().toString();
            txtNombreProducto.setText(opcionBusqueda);
            listaProducto.setVisible(false);
            buscarPresentacion(opcionBusqueda);
        }
    }

    private class ComboPresentacionFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaPresentacion.setVisible(true);
        }

        @Override
        public  void focusLost(FocusEvent e) {
            listaPresentacion.setVisible(false);
        }
    }

    private class ListaPresentacionMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String opcionPresentacion = listaPresentacion.getSelectedValue().toString();
            comboPresentacion.setText(opcionPresentacion);
            listaPresentacion.setVisible(false);
        }
    }

    private class BotonGuardar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String folioVenta = txtFolio.getText();
            String nombreProducto = txtNombreProducto.getText();
            String presentacionProducto = comboPresentacion.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            obtenerIDVenta(folioVenta);
            obtenerIDProducto(nombreProducto, presentacionProducto);

            if (datosCompletos(folioVenta, nombreProducto, presentacionProducto)) {
                try {
                    InsertarSQL.insertarVentaProducto(id, folioVenta, idProducto, cantidad);
                    txtFolio.setText("Folio");
                    txtNombreProducto.setText("Producto");
                    comboPresentacion.setText("Presentación");
                    txtCantidad.setText("Cantidad");
                    System.out.println("No mames");
                } catch (Exception nfE) {
                    System.err.println(nfE.getMessage());
                }
            } else {
                System.err.println("lololo");
            }

        }
    }

    /**
     *
     * Métodos
     */


    private  void buscarVenta (String cadena) {
        dlmBuscador.removeAllElements();
        listaBuscador.setVisible(true);
        String sql = "SELECT folio, activo FROM Venta WHERE folio LIKE ?";
        int resultados = 0;
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, cadena + "%");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                resultados++;
                String correo = rs.getObject("folio").toString();
                boolean activo = rs.getBoolean("activo");
                if(activo) {
                    dlmBuscador.addElement(correo);
                }
            }
        } catch (SQLException sqlException) {

        }
    }

    private  void establecerDatos (String opcionBusqueda) {
        String sql = "SELECT id, folio FROM Venta WHERE folio = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, opcionBusqueda);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                this.id = Integer.parseInt(rs.getObject("id").toString());
                String folio = rs.getObject("folio").toString();
                txtFolio.setText(folio);
                txtFolio.setEnabled(false);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    private void buscarProducto (String cadena) {
        dlmProducto.removeAllElements();
        listaProducto.setVisible(true);
        String sql = "SELECT DISTINCT nombre,activo FROM Producto WHERE nombre LIKE ?";
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
                    dlmProducto.addElement(nombre);
                }
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    private void buscarPresentacion(String opcionBusqueda) {
        dlmPresentacion.removeAllElements();
        String sql = "SELECT presentacion FROM Producto WHERE nombre = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, opcionBusqueda);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                String presentacion = rs.getObject("presentacion").toString();
                dlmPresentacion.addElement(presentacion);
            }
        } catch (SQLException sqlException) {

        }
    }

    private void obtenerIDVenta(String folio) {
        String sql = "SELECT id FROM Venta WHERE folio = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, folio);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                this.id = rs.getInt("folio");
            }
        } catch (SQLException sqlException) {

        }
    }

    private void obtenerIDProducto(String nombreProducto, String presentacionProducto) {
        String sql = "SELECT id FROM Producto WHERE nombre = ? and presentacion = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, nombreProducto);
            ps.setString(2, presentacionProducto);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                idProducto = rs.getInt("id");
            }
        } catch (SQLException sqlException) {

        }
    }

    private boolean datosCompletos(String folio, String nombreProducto, String presentacion) {
        if (!folio.equals("Folio")) {
            if (!nombreProducto.equals("Producto")) {
                if (!presentacion.equals("Presentación")) {
                    return true;
                } else {
                    System.err.println("3");
                    return false;
                }
            } else {
                System.err.println("2");
                return false;
            }
        } else {
            System.err.println("2345234");
            return false;
        }
    }


    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(listaBuscador);
        f.add(listaProducto);
        f.add(listaPresentacion);
        f.add(new VentaProductoVista());
        f.setDefaultCloseOperation(3);
        f.setUndecorated(true);
        f.setSize(800, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }
}
