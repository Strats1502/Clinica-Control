package Vistas;

import ComponentesBeauty.*;
import Conexion.ConexionSQL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by esva on 11/05/17.
 */
public class PacienteRegistroVista extends CustomVista {
    //Componentes
    BeautyImageButton btnImagenPerfil;
    BeautyImageButton btnNuevo;
    BeautyImageButton btnEditar;
    BeautyImageButton btnAntecedentes;
    BeautyImageButton btnRecomendaciones;
    BeautyTextField txtBuscar;
    BeautyTextField txtNombre;
    BeautyTextField txtApellidoPaterno;
    BeautyTextField txtApellidoMaterno;
    BeautyTextField txtFechaNacimiento;
    BeautyTextField txtReligion;
    BeautyTextField txtCorreo;
    BeautyTextField txtContraseña;
    BeautyTextField txtTelefono;
    BeautyTextField txtTelefonoEmergencias;
    BeautyCheckbox checkActivo;
    BeautyBlackButton btnGuardar;

    //Lista para buscador
    public static DefaultListModel dlmBuscador = new DefaultListModel();
    public static BeautyList listaBuscador = new BeautyList(dlmBuscador, 300, 150, 300, 140);

    //Mensajes de error
    public static BeautyErrorMessage errorNombre = new BeautyErrorMessage("Ingresa un nombre...");
    public static BeautyErrorMessage errorApellidoPaterno = new BeautyErrorMessage("Ingresa el apellido paterno...");
    public static BeautyErrorMessage errorApellidoMaterno = new BeautyErrorMessage("Ingresa el apellido materno...");
    public static BeautyErrorMessage errorFechaNacimiento = new BeautyErrorMessage("Ingresa la fecha de nacimiento...");
    public static BeautyErrorMessage errorReligion = new BeautyErrorMessage("Ingresa una religión...");
    public static BeautyErrorMessage errorCorreo = new BeautyErrorMessage("El usuario debe tener un correo...");
    public static BeautyErrorMessage errorNoEsCorreo = new BeautyErrorMessage("Ingresa un correo valido...");
    public static BeautyErrorMessage errorFormato = new BeautyErrorMessage("Ingresa un carácter numérico...");

    //Variables
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String opcionBusqueda = null;
    String pathFoto = null;
    boolean editando = false;
    int id = 0;

    //Variables para buscar un archivo
    JFileChooser jFile = new JFileChooser();
    BufferedImage image = null;
    File file;
    FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());

    public PacienteRegistroVista() {
        componentes();
    }

    private void componentes() {
        btnImagenPerfil = new BeautyImageButton(establecerIcono("FotoPerfil", 150, 150), 40, 70, 150, 150);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        btnAntecedentes = new BeautyImageButton(establecerIcono("Antecedentes", 20, 20), 700, 130, 20, 20);
        btnRecomendaciones = new BeautyImageButton(establecerIcono("Recomendacion", 20, 20), 740, 130, 20, 20);
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);

        txtNombre = new BeautyTextField("Nombre", 60, 270, 200, 20);
        txtApellidoPaterno = new BeautyTextField("Apellido paterno", 310, 270, 200, 20);
        txtApellidoMaterno = new BeautyTextField("Apellido materno", 570, 270, 200, 20);
        txtFechaNacimiento = new BeautyTextField("Fecha nacimiento", 60, 330, 200,20);
        txtReligion = new BeautyTextField("Religion", 310, 330, 200,20);
        txtCorreo = new BeautyTextField("Correo", 60, 390, 200, 20);
        txtTelefono =  new BeautyTextField("Teléfono", 310, 390, 200, 20);
        txtTelefonoEmergencias = new BeautyTextField("Teléfono emergencias", 570, 390, 200, 20);

        checkActivo = new BeautyCheckbox("Activo", 60, 450, 200);
        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);

        this.add(btnImagenPerfil);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(btnAntecedentes);
        this.add(btnRecomendaciones);
        this.add(txtBuscar);
        this.add(txtNombre);
        this.add(txtApellidoPaterno);
        this.add(txtApellidoMaterno);
        this.add(txtFechaNacimiento);
        this.add(txtReligion);
        this.add(txtCorreo);
        this.add(txtTelefono);
        this.add(txtTelefonoEmergencias);
        this.add(checkActivo);
        this.add(btnGuardar);
        this.add(fondo());
    }

    /**
     * Clases privadas para acción de componentes
     */

    private class CambiarImagen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            jFile.setFileFilter(imageFilter);
            jFile.showOpenDialog(null);
            file = jFile.getSelectedFile();
            try {
                image = ImageIO.read(file);
                ImageIcon icono = new ImageIcon(image);
                Icon i = new ImageIcon(icono.getImage().getScaledInstance(btnImagenPerfil.getWidth(), btnImagenPerfil.getHeight(), Image.SCALE_DEFAULT));
                btnImagenPerfil.setIcon(i);
            } catch (IOException ioException) {

            } catch (IllegalArgumentException argumentException) {

            }
        }
    }

    /**
     *
     * Metodos
     */
    private  boolean esCorreo (String correo) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    private  boolean datosPersonalesCompletos (String nombre, String apellidoPaterno, String apellidoMaterno,String fechaNacimiento, String religion) {
        if (!nombre.equals("Nombre")) {
            if (!apellidoPaterno.equals("Apellido paterno")) {
                if (!apellidoMaterno.equals("Apellido materno")) {
                    if(!fechaNacimiento.equals("Fecha nacimiento")) {
                        if(!religion.equals("Religión")) {
                            return true;
                        } else {
                            errorReligion.setVisible(true);
                            return false;
                        }
                    } else {
                        errorFechaNacimiento.setVisible(true);
                        return false;
                    }
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

    private  boolean datosContactoCompletos (String correo, String telefono, String telefonoEmergencias) {
        if (!correo.equals("Correo")) {
            if (esCorreo(correo)) {
                if (!telefono.equals("Teléfono")) {
                    if (!telefonoEmergencias.equals("Télefono emergencias")) {
                        return true;
                    } else {
                        errorFormato.setVisible(true);
                        return false;
                    }
                } else {
                    errorFormato.setVisible(true);
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

    private  boolean datosCompletos (String nombre, String apellidoPaterno, String apellidoMaterno,String fechaNacimiento, String religion, String correo, String telefono, String telefonoEmergencias) {
            if (datosPersonalesCompletos(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, religion)) {
                if (datosContactoCompletos(correo, telefono, telefonoEmergencias)) {
                    return true;
                }
            }
        return false;
    }

    private  void buscarPaciente (String cadena) {
        dlmBuscador.removeAllElements();
        listaBuscador.setVisible(true);
        String sql = "SELECT correo, activo FROM Paciente WHERE correo LIKE ?";
        int resultados = 0;
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, cadena + "%");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                resultados++;
                String correo = rs.getObject("correo").toString();
                boolean activo = rs.getBoolean("activo");
                if(activo) {
                    dlmBuscador.addElement(correo);
                }
            }
        } catch (SQLException sqlException) {

        }
    }

    private  void establecerDatos (String opcionBusqueda) {
        String sql = "SELECT * FROM Paciente WHERE correo = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, opcionBusqueda);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                this.id = Integer.parseInt(rs.getObject("id").toString());
                String pathFoto = rs.getObject("path_foto").toString();
                String nombre = rs.getObject("nombre").toString();
                String apellidoPaterno = rs.getObject("apellido_paterno").toString();
                String apellidoMaterno = rs.getObject("apellido_materno").toString();
                String fechaNacimiento = rs.getObject("fecha_nacimiento").toString();
                String religion = rs.getObject("religion").toString();
                String correo = rs.getObject("correo").toString();
                String telefono = rs.getObject("telefono").toString();
                String telefonoEmergencias = rs.getObject("telefono_emergencias").toString();
                boolean activo = rs.getBoolean("activo");

                btnImagenPerfil.setIcon(establecerImagenPath(pathFoto));
                txtNombre.setText(nombre);
                txtApellidoPaterno.setText(apellidoPaterno);
                txtApellidoMaterno.setText(apellidoMaterno);
                txtFechaNacimiento.setText(fechaNacimiento);
                txtReligion.setText(religion);
                txtCorreo.setText(correo);
                txtTelefono.setText(telefono);
                txtTelefonoEmergencias.setText(telefonoEmergencias);
                checkActivo.setSelected(activo);

                btnImagenPerfil.setEnabled(false);
                txtNombre.setEnabled(false);
                txtApellidoPaterno.setEnabled(false);
                txtApellidoMaterno.setEnabled(false);
                txtFechaNacimiento.setEnabled(false);
                txtReligion.setEnabled(false);
                txtCorreo.setEnabled(false);
                txtTelefono.setEnabled(false);
                txtTelefonoEmergencias.setEnabled(false);
                checkActivo.setEnabled(false);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(new PacienteRegistroVista());
        f.setDefaultCloseOperation(3);
        f.setUndecorated(true);
        f.setSize(800, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

}
