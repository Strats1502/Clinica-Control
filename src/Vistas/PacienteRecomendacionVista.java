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
 * Created by esva on 13/05/17.
 */
public class PacienteRecomendacionVista extends CustomVista {
    //Componentes
    BeautyImageButton btnImagenPerfil;
    BeautyImageButton btnNuevo;
    BeautyImageButton btnEditar;
    BeautyImageButton btnAntecedentes;
    BeautyImageButton btnRecomendaciones;
    BeautyTextField txtBuscar;
    BeautyBlackButton btnGuardar;

    BeautyTextField txtDoctor;
    BeautyTextField txtRecomendacion;

    BeautyTextField txtNombre;
    BeautyTextField txtApellidoPaterno;
    BeautyTextField txtApellidoMaterno;

    //Lista para buscador
    public static DefaultListModel dlmBuscador = new DefaultListModel();
    public static BeautyList listaBuscador = new BeautyList(dlmBuscador, 300, 150, 300, 140);

    //Lista para doctor
    public static DefaultListModel dlmDoctor = new DefaultListModel();
    public static BeautyList listaDoctor = new BeautyList(dlmDoctor, 60, 290, 200, 100);

    //errores
    public static BeautyErrorMessage errorMedico = new BeautyErrorMessage("No has seleccionado un medico");
    public static BeautyErrorMessage errorRecomendacion = new BeautyErrorMessage("No has ingresado una recomendación");

    //Variables
    String opcionBusqueda = null;
    String opcionBusquedaDoctor = null;
    int id = 0;
    int idDoctor = 0;

    public PacienteRecomendacionVista() {
        componentes();
    }

    private void componentes() {
        btnImagenPerfil = new BeautyImageButton(establecerIcono("FotoPerfil", 150, 150), 40, 70, 150, 150);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        btnAntecedentes = new BeautyImageButton(establecerIcono("Antecedentes", 20, 20), 700, 130, 20, 20);
        btnRecomendaciones = new BeautyImageButton(establecerIcono("Recomendacion", 20, 20), 740, 130, 20, 20);
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);

        txtDoctor = new BeautyTextField("Medico", 60, 270, 200,20);
        txtRecomendacion = new BeautyTextField("Recomendación", 330, 270, 400,20);

        txtNombre = new BeautyTextField("Nombre", 60, 390, 200, 20);
        txtApellidoPaterno = new BeautyTextField("Apellido paterno", 310, 390, 200, 20);
        txtApellidoMaterno = new BeautyTextField("Apellido materno", 570, 390, 200, 20);

        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);

        btnImagenPerfil.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellidoPaterno.setEnabled(false);
        txtApellidoMaterno.setEnabled(false);
        txtDoctor.setEnabled(false);
        txtRecomendacion.setEnabled(false);
        btnGuardar.setEnabled(false);

        txtBuscar.addKeyListener(new BuscarKeyAdapter());
        txtBuscar.addFocusListener(new BuscarFocusAdapter());
        listaBuscador.addMouseListener(new ListaBuscarMouseAdapter());
        txtDoctor.addKeyListener(new DoctorKeyAdapter());
        txtDoctor.addFocusListener(new DoctorFocusAdapter());
        listaDoctor.addMouseListener(new ListaDoctorMouseAdapter());
        btnGuardar.addActionListener(new BotonGuardar());

        this.add(btnImagenPerfil);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(btnAntecedentes);
        this.add(btnRecomendaciones);
        this.add(txtBuscar);
        this.add(txtDoctor);
        this.add(txtRecomendacion);
        this.add(txtNombre);
        this.add(txtApellidoPaterno);
        this.add(txtApellidoMaterno);
        this.add(btnGuardar);
        this.add(fondo());
    }

    /**
     * Clases privadas
     */

    private class BuscarKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char tecla = e.getKeyChar();
            String cadena = txtBuscar.getText() + tecla;
            buscarPaciente(cadena);
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
            try {
                opcionBusqueda = listaBuscador.getSelectedValue().toString();
                establecerDatos(opcionBusqueda);
                listaBuscador.setVisible(false);
                txtBuscar.setText("");
                txtDoctor.setEnabled(true);
                txtRecomendacion.setEnabled(true);
                btnGuardar.setEnabled(true);
            } catch (NullPointerException nullException) {

            }
        }
    }

    private class DoctorKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char tecla = e.getKeyChar();
            String cadena = txtDoctor.getText() + tecla;
            buscarDoctor(cadena);
        }
    }

    private class DoctorFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaDoctor.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaDoctor.setVisible(false);
        }
    }

    private class ListaDoctorMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                opcionBusquedaDoctor = listaDoctor.getSelectedValue().toString();
                txtDoctor.setText(opcionBusquedaDoctor);
                listaDoctor.setVisible(false);
            } catch (NullPointerException nullException) {

            }
        }
    }

    private class BotonGuardar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buscarID(opcionBusqueda);
            buscarIDDoctor(txtDoctor.getText());

            String doctor = txtDoctor.getText();
            String recomendacion = txtRecomendacion.getText();

            if (datosCompletos(doctor, recomendacion)) {
                InsertarSQL.insertarRecomendacion(id, idDoctor, recomendacion);
                btnImagenPerfil.setIcon(establecerIcono("FotoPerfil", 150, 150));
                txtDoctor.setText("Medico");
                txtRecomendacion.setText("Recomendación");
                txtNombre.setText("Nombre");
                txtApellidoPaterno.setText("Apellido paterno");
                txtApellidoMaterno.setText("Apellido materno");

                btnGuardar.setEnabled(false);
                txtDoctor.setEnabled(false);
                txtRecomendacion.setEnabled(false);
            }
            opcionBusqueda = null;
        }
    }

    /**
     *
     * métodos
     */

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

                btnImagenPerfil.setIcon(establecerImagenPath(pathFoto));
                txtNombre.setText(nombre);
                txtApellidoPaterno.setText(apellidoPaterno);
                txtApellidoMaterno.setText(apellidoMaterno);

                btnImagenPerfil.setEnabled(false);
                txtNombre.setEnabled(false);
                txtApellidoPaterno.setEnabled(false);
                txtApellidoMaterno.setEnabled(false);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    private boolean datosCompletos (String medico, String recomendacion) {
        if (!medico.equals("Medico")) {
            if (!recomendacion.equals("Recomendación")) {
                return true;
            } else {
                errorRecomendacion.setVisible(true);
                return false;
            }
        } else {
            errorMedico.setVisible(true);
            return false;
        }
    }

    private void buscarID(String correo) {
        String sql = "SELECT id FROM Paciente WHERE correo = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, correo);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                id = Integer.parseInt(rs.getObject("id").toString());
            }
        } catch (SQLException sqlException) {

        }
    }

    private void buscarIDDoctor(String correo) {
        String sql = "SELECT id FROM Usuario WHERE correo = ?";
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, correo);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next()) {
                idDoctor = Integer.parseInt(rs.getObject("id").toString());
            }
        } catch (SQLException sqlException) {

        }
    }

    private void buscarDoctor(String cadena) {
        dlmDoctor.removeAllElements();
        listaDoctor.setVisible(true);
        String sql = "SELECT id, correo, rol FROM Usuario WHERE correo LIKE ?";
        int resultados = 0;
        try {
            PreparedStatement ps = ConexionSQL.getConexion().prepareStatement(sql);
            ps.setString(1, cadena + "%");
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                resultados++;
                String correo = rs.getObject("correo").toString();
                String rol = rs.getObject("rol").toString();
                if(rol.equals("Medico")) {
                    dlmDoctor.addElement(correo);
                }
            }
        } catch (SQLException sqlException) {

        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(listaBuscador);
        f.add(listaDoctor);
        f.add(errorRecomendacion);
        f.add(errorMedico);
        f.add(new PacienteRecomendacionVista());
        f.setDefaultCloseOperation(3);
        f.setUndecorated(true);
        f.setSize(800, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }


}
