package Vistas;

import ComponentesBeauty.*;
import Conexion.ConexionSQL;
import Conexion.InsertarSQL;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by esva on 6/05/17.
 */
public class UsuarioVista extends CustomVista {
    //Componentes
    private static BeautyImageButton btnImagenPerfil;
    private static BeautyTextField txtBuscar;
    private static BeautyImageButton btnNuevo;
    private static BeautyImageButton btnEditar;
    private static BeautyComboBox comboBoxRol;
    private static BeautyTextField txtNombre;
    private static BeautyTextField txtApellidoPaterno;
    private static BeautyTextField txtApellidoMaterno;
    private static BeautyTextField txtCorreo;
    private static BeautyPasswordField txtPass;
    private static BeautyPasswordField txtPass2;
    private static BeautyCheckbox checkActivo;
    private static BeautyCheckbox checkAdministrador;
    private static BeautyBlackButton btnGuardar;

    //Lista para buscador
    public static DefaultListModel dlmBuscador = new DefaultListModel();
    public static BeautyList listaBuscador = new BeautyList(dlmBuscador, 300, 150, 300, 140);

    //Lista para rol
    public static DefaultListModel dlmRol = new DefaultListModel();
    public static BeautyList listaRol = new BeautyList(dlmRol, 40, 280, 100, 100);

    //Mensajes de error
    public static BeautyErrorMessage errorRol = new BeautyErrorMessage("El usuario debe tener un rol...");
    public static BeautyErrorMessage errorNombre = new BeautyErrorMessage("Ingresa un nombre...");
    public static BeautyErrorMessage errorApellidoPaterno = new BeautyErrorMessage("Ingresa el apellido paterno...");
    public static BeautyErrorMessage errorApellidoMaterno = new BeautyErrorMessage("Ingresa el apellido materno...");
    public static BeautyErrorMessage errorCorreo = new BeautyErrorMessage("El usuario debe tener un correo...");
    public static BeautyErrorMessage errorNoEsCorreo = new BeautyErrorMessage("Ingresa un correo valido...");
    public static BeautyErrorMessage errorContraseña1 = new BeautyErrorMessage("Ingresa una contraseña...");
    public static BeautyErrorMessage errorContraseña2 = new BeautyErrorMessage("Repite la contraseña...");
    public static BeautyErrorMessage errorNoIguales = new BeautyErrorMessage("Las contraseñas no son iguales...");

    //Variables
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String opcionBusqueda = "";
    String opcionRol = "";
    boolean editando = false;

    //Variables para buscar un archivo
    JFileChooser jFile = new JFileChooser();
    BufferedImage image = null;
    File file = new File("src/Iconos/icn_FotoPerfil.png");
    FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());

    public UsuarioVista() {
        componentes();
        this.setLayout(null);
        this.setBounds(0, 0, 800, 500);
        this.setVisible(true);
    }

    private void componentes() {
        btnImagenPerfil = new BeautyImageButton(establecerIcono("FotoPerfil", 150, 150), 40, 70, 150, 150);
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        comboBoxRol = new BeautyComboBox("Rol", 40, 250, 120, 20);
        txtNombre = new BeautyTextField("Nombre", 60, 290, 200, 20);
        txtApellidoPaterno = new BeautyTextField("Apellido paterno", 310, 290, 200, 20);
        txtApellidoMaterno = new BeautyTextField("Apellido paterno", 570, 290, 200, 20);
        txtCorreo = new BeautyTextField("Correo", 60, 390, 200, 20);
        txtPass = new BeautyPasswordField("Contraseña", 310, 390, 200, 20);
        txtPass2 = new BeautyPasswordField("Repetir contraseña", 570, 390, 200, 20);
        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);
        checkActivo = new BeautyCheckbox("Activo", 60, 450, 200);
        checkAdministrador = new BeautyCheckbox("Administrador", 300, 450, 150);

        dlmRol.addElement("Medico");
        dlmRol.addElement("Administrativo");
        dlmRol.addElement("Normal");

        btnImagenPerfil.addActionListener(new CambiarImagen());
        btnGuardar.addActionListener(new BotonGuardar());
        txtBuscar.addKeyListener(new BuscarKeyAdapter());
        txtBuscar.addFocusListener(new BuscarFocusAdapter());
        listaBuscador.addMouseListener(new ListaBuscarMouseAdapter());
        btnEditar.addActionListener(new BotonEditar());
        btnNuevo.addActionListener(new BotonNuevo());
        comboBoxRol.addFocusListener(new BotonRol());
        listaRol.addMouseListener(new ListaRolMouseAdapter());

        this.add(btnImagenPerfil);
        this.add(txtBuscar);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(comboBoxRol);
        this.add(txtNombre);
        this.add(txtApellidoPaterno);
        this.add(txtApellidoMaterno);
        this.add(txtCorreo);
        this.add(txtPass);
        this.add(txtPass2);
        this.add(checkActivo);
        this.add(checkAdministrador);
        this.add(btnGuardar);
        this.add(fondo());
    }

    private class CambiarImagen implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            file.getPath();
            jFile.setFileFilter(imageFilter);
            jFile.showOpenDialog(null);
            file = jFile.getSelectedFile();
            try {
                image = ImageIO.read(file);
                ImageIcon icono = new ImageIcon(image);
                Icon i = new ImageIcon(icono.getImage().getScaledInstance(btnImagenPerfil.getWidth(), btnImagenPerfil.getHeight(), Image.SCALE_DEFAULT));
                btnImagenPerfil.setIcon(i);
            } catch (IOException ioException) {
                System.err.println(ioException.getMessage());
            }
        }
    }

    private class BotonGuardar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String rol = comboBoxRol.getText();
            String nombre = txtNombre.getText();
            String apellidoPaterno = txtApellidoPaterno.getText();
            String apellidoMaterno = txtApellidoMaterno.getText();
            String correo = txtCorreo.getText();
            String pass1 = String.copyValueOf(txtPass.getPassword());
            String pass2 = String.copyValueOf(txtPass2.getPassword());
            boolean activo = checkActivo.isSelected();
            boolean administrador = checkAdministrador.isSelected();
            String pathFoto = file.getPath();

            if (datosCompletos(rol, nombre, apellidoPaterno, apellidoMaterno, correo, pass1, pass2)) {
                InsertarSQL.insertarUsuario(1, rol, nombre, apellidoPaterno, apellidoMaterno, correo, pass1, activo, administrador, pathFoto);
                comboBoxRol.setText("Rol");
                txtNombre.setHint("Nombre");
                txtApellidoPaterno.setHint("Apellido paterno");
                txtApellidoMaterno.setHint("Apellido materno");
                txtCorreo.setHint("Correo");
                txtPass.setHint("Contraseña");
                txtPass2.setHint("Repetir contraseña");
                checkActivo.setSelected(false);
                checkAdministrador.setSelected(false);
                btnImagenPerfil.setIcon(establecerIcono("FotoPerfil", 150, 150));
            }
        }
    }

    private class BotonEditar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editando = true;
            btnImagenPerfil.setEnabled(true);
            comboBoxRol.setEnabled(true);
            txtNombre.setEnabled(true);
            txtApellidoPaterno.setEnabled(true);
            txtApellidoMaterno.setEnabled(true);
            txtCorreo.setEnabled(false);
            txtPass.setEnabled(true);
            txtPass2.setEnabled(true);
            checkActivo.setEnabled(true);
            checkAdministrador.setEnabled(true);
        }
    }

    private class BotonNuevo implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e ) {
            btnImagenPerfil.setIcon(establecerIcono("FotoPerfil", 150, 150));
            txtNombre.setText("Nombre");
            txtApellidoPaterno.setText("Apellido paterno");
            txtApellidoMaterno.setText("Apellido materno");
            txtCorreo.setText("Correo");
            txtPass.setText("Contraseña");
            txtPass2.setText("Repetir contraseña");
            checkActivo.setSelected(false);
            checkAdministrador.setSelected(false);

            btnImagenPerfil.setEnabled(true);
            txtNombre.setEnabled(true);
            txtApellidoPaterno.setEnabled(true);
            txtApellidoMaterno.setEnabled(true);
            txtCorreo.setEnabled(true);
            txtPass.setEnabled(true);
            txtPass2.setEnabled(true);
            checkActivo.setEnabled(true);
            checkAdministrador.setEnabled(true);
        }
    }

    private class BotonRol extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaRol.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaRol.setVisible(false);
        }
    }

    private class BuscarKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char tecla = e.getKeyChar();
            String cadena = txtBuscar.getText() + tecla;
            buscarUsuario(cadena);
        }
    }

    private class BuscarFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaBuscador.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaBuscador.setVisible(false);
        }
    }

    private class ListaBuscarMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            opcionBusqueda = listaBuscador.getSelectedValue().toString();
            establecerDatos(opcionBusqueda);
            listaBuscador.setVisible(false);
            txtBuscar.setText("");
        }
    }

    private class ListaRolMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            opcionBusqueda = listaRol.getSelectedValue().toString();
            comboBoxRol.setText(opcionBusqueda);
        }
    }

    private  boolean escogioRol (String rol) {
        if (rol == "Rol") {
            errorRol.setVisible(true);
            return false;
        } else {
            return true;
        }
    }

    private  boolean esCorreo (String correo) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    private  boolean contraseñasIguales (String contraseña1, String constraseña2) {
        if (contraseña1.equals(constraseña2)) {
            return true;
        } else {
            errorNoIguales.setVisible(true);
            return false;
        }
    }

    private  boolean datosPersonalesCompletos (String nombre, String apellidoPaterno, String apellidoMaterno) {
        if (!nombre.equals("Nombre")) {
            if (!apellidoPaterno.equals("Apellido paterno")) {
                if (!apellidoMaterno.equals("Apellido materno")) {
                    return true;
                } else {
                    errorApellidoMaterno.setVisible(true);
                    return false;
                }
            } else {
                errorApellidoPaterno.setVisible(true);
                return false;
            }
        } else {
            errorNombre.setVisible(true);
            return false;
        }
    }

    private  boolean datosContactoCompletos (String correo, String contraseña1, String contraseña2) {
        if (!correo.equals("Correo")) {
            if (esCorreo(correo)) {
                if (!contraseña1.equals("Contraseña")) {
                    if (!contraseña2.equals("Repetir contraseña")) {
                        if (contraseñasIguales(contraseña1, contraseña2)) {
                            return true;
                        } else {
                            errorNoIguales.setVisible(true);
                            return false;
                        }
                    } else {
                        errorContraseña2.setVisible(true);
                        return false;
                    }
                } else {
                    errorContraseña1.setVisible(true);
                    return false;
                }
            } else {
                errorNoEsCorreo.setVisible(true);
                return false;
            }
        } else {
            errorCorreo.setVisible(true);
            return false;
        }
    }

    private  boolean datosCompletos (String rol, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String contraseña, String contraseña2) {
        if (escogioRol(rol)) {
            if (datosPersonalesCompletos(nombre, apellidoPaterno, apellidoMaterno)) {
                if (datosContactoCompletos(correo, contraseña, contraseña2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private  void buscarUsuario (String cadena) {
        dlmBuscador.removeAllElements();
        listaBuscador.setVisible(true);
        String sql = "SELECT correo FROM Usuario WHERE correo LIKE ?";
        int resultados = 0;
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, cadena + "%");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                resultados++;
                String correo = rs.getObject("correo").toString();
                dlmBuscador.addElement(correo);
            }
        } catch (SQLException sqlException) {

        }
    }

    private  void establecerDatos (String opcionBusqueda) {
        String sql = "SELECT * FROM Usuario WHERE correo = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, opcionBusqueda);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                String pathFoto = rs.getObject("path_foto").toString();
                String rol = rs.getObject("rol").toString();
                String nombre = rs.getObject("nombre").toString();
                String apellidoPaterno = rs.getObject("apellido_paterno").toString();
                String apellidoMaterno = rs.getObject("apellido_materno").toString();
                String correo = rs.getObject("correo").toString();
                String contraseña = rs.getObject("contraseña").toString();
                boolean activo = rs.getBoolean("activo");
                boolean administrador = rs.getBoolean("administrador");

                btnImagenPerfil.setIcon(establecerImagenPath(pathFoto));
                comboBoxRol.setText(rol);
                txtNombre.setText(nombre);
                txtApellidoPaterno.setText(apellidoPaterno);
                txtApellidoMaterno.setText(apellidoMaterno);
                txtCorreo.setText(correo);
                txtPass.setText(contraseña);
                txtPass2.setText(contraseña);
                checkActivo.setSelected(activo);
                checkAdministrador.setSelected(administrador);

                btnImagenPerfil.setEnabled(false);
                comboBoxRol.setEnabled(false);
                txtNombre.setEnabled(false);
                txtApellidoPaterno.setEnabled(false);
                txtApellidoMaterno.setEnabled(false);
                txtCorreo.setEnabled(false);
                txtPass.setEnabled(false);
                txtPass2.setEnabled(false);
                checkActivo.setEnabled(false);
                checkAdministrador.setEnabled(false);

            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }


    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(InsertarSQL.errorCorreo);
        f.add(errorRol);
        f.add(errorNombre);
        f.add(errorApellidoPaterno);
        f.add(errorApellidoMaterno);
        f.add(errorCorreo);
        f.add(errorNoEsCorreo);
        f.add(errorContraseña1);
        f.add(errorContraseña2);
        f.add(errorNoIguales);
        //f.add(getListaUsuarios());
        f.add(listaRol);
        f.add(listaBuscador);
        f.add(new UsuarioVista());
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(3);
        f.setSize(800, 500);
        f.setVisible(true);

    }
}
