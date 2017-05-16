package Vistas;

import ComponentesBeauty.*;
import Conexion.ActualizarSQL;
import Conexion.ConexionSQL;
import Conexion.InsertarSQL;
import Contenedor.Contenedor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by esva on 9/05/17.
 */
public class ProductoRegistroVista extends CustomVista {
    //Componentes
    BeautyTextField txtBuscar;
    BeautyImageButton btnNuevo;
    BeautyImageButton btnEditar;
    BeautyImageButton btnLlegadaMercancia;
    BeautyImageButton btnImagenProducto;
    BeautyComboBox comboBoxPresentacion;
    BeautyTextField txtNombre;
    BeautyTextField txtProovedor;
    BeautyTextField txtCosto;
    BeautyTextField txtPrecio;
    BeautyCheckbox checkActivo;
    BeautyBlackButton btnGuardar;

    //Variables cambiar imagen
    JFileChooser jFile = new JFileChooser();
    BufferedImage image;
    File file;
    FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());

    //Lista de busqueda
    public static DefaultListModel dlmBuscador = new DefaultListModel();
    public static BeautyList listaBuscador = new BeautyList(dlmBuscador, 300, 150, 300, 140);

    //ListaComboPresentacion
    public static DefaultListModel dlmPresentacion = new DefaultListModel();
    public static BeautyList listaPresentacion = new BeautyList(dlmPresentacion, 40, 270, 120, 100);

    //Errores
    public static BeautyErrorMessage errorEditar = new BeautyErrorMessage("No has seleccionado un registro para editar...");
    public static BeautyErrorMessage errorFormato = new BeautyErrorMessage("Ingresa un carácter numérico...");
    public static BeautyErrorMessage errorTipo = new BeautyErrorMessage("Selecciona un tipo de presentación...");
    public static BeautyErrorMessage errorNombre = new BeautyErrorMessage("Ingresa un nombre de producto...");
    public static BeautyErrorMessage errorProveedor = new BeautyErrorMessage("Ingresa un proveedor...");

    //Variables necesarias
    String opcionBusqueda = null;
    String pathFoto = null;
    boolean editando = false;
    int id = 0;

    public ProductoRegistroVista() {
        componentes();
    }

    private void componentes() {
        //Instanciar componentes
        btnImagenProducto = new BeautyImageButton(establecerIcono("Producto", 150, 150), 40, 70, 150, 150);
        comboBoxPresentacion = new BeautyComboBox("Presentación", 40, 250, 120, 20);
        txtNombre = new BeautyTextField("Nombre", 60, 290, 200, 20);
        txtProovedor = new BeautyTextField("Proveedor", 310, 290, 200, 20);
        txtCosto = new BeautyTextField("Costo", 60, 390, 200, 20);
        txtPrecio = new BeautyTextField("Precio", 310, 390, 200, 20);
        checkActivo = new BeautyCheckbox("Activo", 60, 450, 200);
        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        btnLlegadaMercancia = new BeautyImageButton(establecerIcono("LlegadaMercancia", 20, 20), 700, 130, 20, 20);

        //Añade opciones a defaults list model
        dlmPresentacion.addElement("Caja");
        dlmPresentacion.addElement("Envase");
        dlmPresentacion.addElement("Botella");

        //Implementar funciones a componentes
        btnImagenProducto.addActionListener(new CambiarImagen());
        txtBuscar.addKeyListener(new Buscador());
        txtBuscar.addFocusListener(new BuscadorFocus());
        comboBoxPresentacion.addFocusListener(new PresentacionFocus());
        listaBuscador.addMouseListener(new ListaBuscadorElemento());
        listaPresentacion.addMouseListener(new ListaPresentacionElemento());
        btnEditar.addActionListener(new BotonEditar());
        btnNuevo.addActionListener(new BotonNuevo());
        btnLlegadaMercancia.addActionListener(new BotonLlegadaMercancia());
        btnGuardar.addActionListener(new BotonGuardar());

        //Añadir componentes al panel
        this.add(btnImagenProducto);
        this.add(comboBoxPresentacion);
        this.add(txtNombre);
        this.add(txtProovedor);
        this.add(txtCosto);
        this.add(txtPrecio);
        this.add(checkActivo);
        this.add(btnGuardar);
        this.add(txtBuscar);
        this.add(listaBuscador);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(btnLlegadaMercancia);
        this.add(fondo());
    }

    private class CambiarImagen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            jFile.setFileFilter(imageFilter);
            jFile.showOpenDialog(null);
            file = jFile.getSelectedFile();
            try {
                image = ImageIO.read(file);
                ImageIcon icono = new ImageIcon(image);
                Icon i = new ImageIcon(icono.getImage().getScaledInstance(btnImagenProducto.getWidth(), btnImagenProducto.getHeight(), Image.SCALE_DEFAULT));
                btnImagenProducto.setIcon(i);
            } catch (IOException ioException) {

            } catch (IllegalArgumentException illegalArgument) {

            }
        }
    }

    private class Buscador extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char tecla = e.getKeyChar();
            String cadena = txtBuscar.getText() + tecla;
            buscarProducto(cadena);
        }
    }

    private class BuscadorFocus extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaBuscador.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaBuscador.setVisible(false);
        }
    }

    private class PresentacionFocus extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaPresentacion.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaPresentacion.setVisible(false);
        }
    }

    private class ListaBuscadorElemento extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            editando = false;
            try {
                opcionBusqueda = listaBuscador.getSelectedValue().toString();
                establecerDatos(opcionBusqueda);
                listaBuscador.setVisible(false);
                txtBuscar.setText("");
                btnGuardar.setEnabled(false);
            } catch (NullPointerException nullException) {

            }
        }
    }

    private class ListaPresentacionElemento extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String opcion = listaPresentacion.getSelectedValue().toString();
            System.out.println(opcion);
            comboBoxPresentacion.setText(opcion);
        }
    }

    private class BotonGuardar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = txtNombre.getText();
                String proveedor = txtProovedor.getText();
                double costo = Double.parseDouble(txtCosto.getText());
                double precio = Double.parseDouble(txtPrecio.getText());
                String presentacion = comboBoxPresentacion.getText();
                boolean activo = checkActivo.isSelected();


                if (file != null) {
                    pathFoto = file.getPath();
                } else {
                    pathFoto = "src/Iconos/icn_Producto.png";
                }

                if (datosCompletos(presentacion, nombre, proveedor)) {
                    if (!editando) {
                        InsertarSQL.insertarProducto(1, nombre, proveedor, costo, precio, presentacion, activo, pathFoto);
                        comboBoxPresentacion.setText("Presentación");
                        txtNombre.setHint("Nombre");
                        txtProovedor.setHint("Proveedor");
                        txtCosto.setHint("Costo");
                        txtPrecio.setHint("Precio");
                        checkActivo.setSelected(false);
                        btnImagenProducto.setIcon(establecerIcono("Producto", 150, 150));
                    } else {
                        ActualizarSQL.actualizarProducto(nombre, presentacion, proveedor, costo, precio, pathFoto, activo);
                        InsertarSQL.insertarModificacion(id, 1, "Producto");
                        comboBoxPresentacion.setText("Presentación");
                        txtNombre.setHint("Nombre");
                        txtProovedor.setHint("Proveedor");
                        txtCosto.setHint("Costo");
                        txtPrecio.setHint("Precio");
                        checkActivo.setSelected(false);
                        btnImagenProducto.setIcon(establecerIcono("Producto", 150, 150));
                    }

                }
                opcionBusqueda = null;
                editando = false;
            } catch (NumberFormatException errorFormateo) {
                errorFormato.setVisible(true);
            }
        }
    }

    private class BotonNuevo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            opcionBusqueda = null;
            editando = false;
            btnImagenProducto.setEnabled(true);
            comboBoxPresentacion.setEnabled(true);
            txtNombre.setEnabled(true);
            txtProovedor.setEnabled(true);
            txtCosto.setEnabled(true);
            txtPrecio.setEnabled(true);
            checkActivo.setEnabled(true);
            btnGuardar.setEnabled(true);

            //Valores default
            btnImagenProducto.setIcon(establecerIcono("Producto", 150, 150));
            comboBoxPresentacion.setText("Presentación");
            txtNombre.setText("Nombre");
            txtProovedor.setText("Proveedor");
            txtCosto.setText("Costo");
            txtPrecio.setText("Precio");
            checkActivo.setSelected(false);
        }
    }

    private class BotonEditar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (opcionBusqueda != null) {
                editando = true;
                btnGuardar.setEnabled(true);
                btnImagenProducto.setEnabled(true);
                comboBoxPresentacion.setEnabled(false);
                txtNombre.setEnabled(false);
                txtProovedor.setEnabled(true);
                txtCosto.setEnabled(true);
                txtPrecio.setEnabled(true);
                checkActivo.setEnabled(true);
            } else {
                errorEditar.setVisible(true);
            }
        }
    }

    private class BotonLlegadaMercancia implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            Contenedor.productoLlegadaMercanciaVista.setVisible(true);
        }
    }

    private static boolean datosCompletos(String presentacion, String nombre, String proveedor) {
        if (!presentacion.equals("Presentación")) {
            if (!nombre.equals("Nombre")) {
                if (!proveedor.equals("Proveedor")) {
                    return true;
                } else {
                    errorProveedor.setVisible(true);
                }
            } else {
                errorNombre.setVisible(true);
            }
        } else {
            errorTipo.setVisible(true);
        }
        return false;
    }

    private void buscarProducto(String cadena) {
        dlmBuscador.removeAllElements();
        listaBuscador.setVisible(true);
        String sql = "SELECT nombre , activo FROM Producto WHERE nombre LIKE ?";
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
                if (activo) {
                    dlmBuscador.addElement(nombre);
                } else {

                }
            }
        } catch (SQLException sqlException) {

        }
    }

    private void establecerDatos(String opcionBusqueda) {
        String sql = "SELECT * FROM Producto WHERE nombre = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, opcionBusqueda);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                this.id = Integer.parseInt(rs.getObject("id").toString());
                String pathFoto = rs.getObject("path_foto").toString();
                String presentacion = rs.getObject("presentacion").toString();
                String nombre = rs.getObject("nombre").toString();
                String proveedor = rs.getObject("proveedor").toString();
                double costo = rs.getDouble("costo");
                double precio = rs.getDouble("precio");
                boolean activo = rs.getBoolean("activo");

                //Le pone los datos a los componentes de la opcion seleccionada
                btnImagenProducto.setIcon(establecerImagenPath(pathFoto));
                comboBoxPresentacion.setText(presentacion);
                txtNombre.setText(nombre);
                txtProovedor.setText(proveedor);
                txtCosto.setText(String.valueOf(costo));
                txtPrecio.setText(String.valueOf(precio));
                checkActivo.setSelected(activo);

                //Evita que se editen los datos
                btnImagenProducto.setEnabled(false);
                comboBoxPresentacion.setEnabled(false);
                txtNombre.setEnabled(false);
                txtProovedor.setEnabled(false);
                txtCosto.setEnabled(false);
                txtPrecio.setEnabled(false);
                checkActivo.setEnabled(false);

            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }


    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(listaBuscador);
        f.add(listaPresentacion);
        f.add(errorEditar);
        f.add(errorFormato);
        f.add(errorTipo);
        f.add(errorNombre);
        f.add(errorProveedor);
        f.add(new ProductoRegistroVista());
        f.setDefaultCloseOperation(3);
        f.setUndecorated(true);
        f.setSize(800, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

}
