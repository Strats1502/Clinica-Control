package Vistas;

import ComponentesBeauty.*;
import Conexion.ActualizarSQL;
import Conexion.ConexionSQL;
import Conexion.InsertarSQL;
import Modelos.Paciente;

import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by esva on 13/05/17.
 */
public class PacienteAntecedenteVista extends CustomVista {
    //Componentes
    BeautyImageButton btnImagenPerfil;
    BeautyImageButton btnNuevo;
    BeautyImageButton btnEditar;
    BeautyImageButton btnAntecedentes;
    BeautyImageButton btnRecomendaciones;
    BeautyComboBox comboTipo;
    BeautyComboBox comboEnfermedad;
    BeautyTextField txtDescripcion;
    BeautyTextField txtBuscar;
    BeautyBlackButton btnGuardar;
    BeautyTextField txtNombre;
    BeautyTextField txtApellidoPaterno;
    BeautyTextField txtApellidoMaterno;
    BeautyTextField txtFechaInicio;
    BeautyTextField txtEnfermedad;
    BeautyTextField txtTratamiento;

    //Lista para buscador
    public static DefaultListModel dlmBuscador = new DefaultListModel();
    public static BeautyList listaBuscador = new BeautyList(dlmBuscador, 300, 150, 300, 140);

    //Lista para tipo
    public static DefaultListModel dlmTipo = new DefaultListModel();
    public static BeautyList listaTipo = new BeautyList(dlmTipo, 60, 290, 150, 80);

    //Lista para enfermedad
    public static DefaultListModel dlmEnfermedad = new DefaultListModel();
    public static BeautyList listaEnfermedad = new BeautyList(dlmEnfermedad, 330,290,150, 200);

    //errores
    public static BeautyErrorMessage errorTipo = new BeautyErrorMessage("Escoge un tipo de enfermedad");
    public static BeautyErrorMessage errorEnfermedad1 = new BeautyErrorMessage("Escoge una enfermedad");
    public static BeautyErrorMessage errorDescripcion = new BeautyErrorMessage("Escribe una descripción");
    public static BeautyErrorMessage errorAño = new BeautyErrorMessage("Escribe un año de inicio");
    public static BeautyErrorMessage errorEnfermedad2 = new BeautyErrorMessage("Escribe una enfermedad");
    public static BeautyErrorMessage errorTratamiento = new BeautyErrorMessage("Escribe un tratamiento");

    //Variables
    String opcionBusqueda = null;
    int id = 0;

    public PacienteAntecedenteVista() {
        componentes();
    }

    private void componentes () {
        btnImagenPerfil = new BeautyImageButton(establecerIcono("FotoPerfil", 150, 150), 40, 70, 150, 150);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        btnAntecedentes = new BeautyImageButton(establecerIcono("Antecedentes", 20, 20), 700, 130, 20, 20);
        btnRecomendaciones = new BeautyImageButton(establecerIcono("Recomendacion", 20, 20), 740, 130, 20, 20);
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);

        comboTipo = new BeautyComboBox("Tipo de enfermedad", 60, 270, 150,20);
        comboEnfermedad = new BeautyComboBox("Enfermedad", 330, 270, 100,20);
        txtDescripcion = new BeautyTextField("Descripción", 570, 265,200,20);

        txtFechaInicio = new BeautyTextField("Año inicio", 60, 330, 200, 20);
        txtEnfermedad = new BeautyTextField("Enfermedad", 310, 330, 200,20);
        txtTratamiento = new BeautyTextField("Tratamiento", 570, 330, 200, 20);

        txtNombre = new BeautyTextField("Nombre", 60, 390, 200, 20);
        txtApellidoPaterno = new BeautyTextField("Apellido paterno", 310, 390, 200, 20);
        txtApellidoMaterno = new BeautyTextField("Apellido materno", 570, 390, 200, 20);

        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);

        dlmTipo.addElement("Hereditario");
        dlmTipo.addElement("Patológico");
        dlmTipo.addElement("No patológico");
        dlmTipo.addElement("Crónico");

        btnImagenPerfil.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellidoPaterno.setEnabled(false);
        txtApellidoMaterno.setEnabled(false);
        comboTipo.setEnabled(false);
        comboEnfermedad.setEnabled(false);
        txtDescripcion.setEnabled(false);
        txtFechaInicio.setEnabled(false);
        txtEnfermedad.setEnabled(false);
        txtTratamiento.setEnabled(false);
        btnGuardar.setEnabled(false);


        txtBuscar.addKeyListener(new BuscarKeyAdapter());
        txtBuscar.addFocusListener(new BuscarFocusAdapter());
        listaBuscador.addMouseListener(new ListaBuscarMouseAdapter());
        comboTipo.addFocusListener(new ComboTipoFocusAdapter());
        comboEnfermedad.addFocusListener(new ComboEnfermedadFocusAdapter());
        listaTipo.addMouseListener(new ListaTipoMouseAdapter());
        listaEnfermedad.addMouseListener(new ListaEnfermedadAdapter());
        btnGuardar.addActionListener(new BotonGuardar());

        this.add(btnImagenPerfil);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(btnAntecedentes);
        this.add(btnRecomendaciones);
        this.add(txtBuscar);
        this.add(txtFechaInicio);
        this.add(txtEnfermedad);
        this.add(txtTratamiento);
        this.add(txtNombre);
        this.add(txtApellidoPaterno);
        this.add(txtApellidoMaterno);
        this.add(comboTipo);
        this.add(comboEnfermedad);
        this.add(txtDescripcion);
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
                comboTipo.setEnabled(true);
                comboEnfermedad.setEnabled(true);
                txtDescripcion.setEnabled(true);
                btnGuardar.setEnabled(true);
            } catch (NullPointerException nullException) {

            }
        }
    }

    private class ComboTipoFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained (FocusEvent e){
            listaTipo.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaTipo.setVisible(false);
        }
    }

    private class ComboEnfermedadFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaEnfermedad.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaEnfermedad.setVisible(false);
        }
    }

    private class ListaTipoMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String elementoSeleccionado = listaTipo.getSelectedValue().toString();
            comboTipo.setText(elementoSeleccionado);
            listaTipo.setVisible(false);
            if (elementoSeleccionado.equals("Hereditario")){
                comboEnfermedad.setText("Enfermedad");
                dlmEnfermedad.removeAllElements();
                comboEnfermedad.setEnabled(true);
                txtDescripcion.setEnabled(true);
                txtFechaInicio.setEnabled(false);
                txtEnfermedad.setEnabled(false);
                txtTratamiento.setEnabled(false);
                dlmEnfermedad.addElement("Diabetes");
                dlmEnfermedad.addElement("Cáncer");
                dlmEnfermedad.addElement("Renal");
                dlmEnfermedad.addElement("Autoinmune");
                dlmEnfermedad.addElement("Musculo-Esqueletica");
                dlmEnfermedad.addElement("HAS");
                dlmEnfermedad.addElement("Cardiopatía");
                dlmEnfermedad.addElement("Tiroides");
                dlmEnfermedad.addElement("Embolia");
                dlmEnfermedad.addElement("Otra");
            } else if (elementoSeleccionado.equals("Patológico")) {
                comboEnfermedad.setText("Enfermedad");
                comboEnfermedad.setEnabled(true);
                txtDescripcion.setEnabled(true);
                txtFechaInicio.setEnabled(false);
                txtEnfermedad.setEnabled(false);
                txtTratamiento.setEnabled(false);
                dlmEnfermedad.removeAllElements();
                dlmEnfermedad.addElement("Cirujía");
                dlmEnfermedad.addElement("Fractura");
                dlmEnfermedad.addElement("Transfución");
            } else if (elementoSeleccionado.equals("No patológico")) {
                comboEnfermedad.setText("Enfermedad");
                comboEnfermedad.setEnabled(true);
                txtDescripcion.setEnabled(true);
                txtFechaInicio.setEnabled(false);
                txtEnfermedad.setEnabled(false);
                txtTratamiento.setEnabled(false);
                dlmEnfermedad.removeAllElements();
                dlmEnfermedad.addElement("Tabaquismo");
                dlmEnfermedad.addElement("Toxicomanía");
                dlmEnfermedad.addElement("Alergía");
                dlmEnfermedad.addElement("Alcoholismo");
            } else if (elementoSeleccionado.equals("Crónico")) {
                comboEnfermedad.setText("Enfermedad");
                dlmEnfermedad.removeAllElements();
                txtFechaInicio.setEnabled(true);
                txtEnfermedad.setEnabled(true);
                txtTratamiento.setEnabled(true);
                comboEnfermedad.setEnabled(false);
                txtDescripcion.setEnabled(false);
            }
        }
    }

    private class ListaEnfermedadAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String elementoSeleccionado = listaEnfermedad.getSelectedValue().toString();
            comboEnfermedad.setText(elementoSeleccionado);
            listaEnfermedad.setVisible(false);
        }
    }

    private class BotonGuardar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buscarID(opcionBusqueda);

            String tipo = comboTipo.getText();
            String enfermedad = comboEnfermedad.getText();
            String descripcion = txtDescripcion.getText();
            String añoInicio = txtFechaInicio.getText();
            String enfermedad2 = txtEnfermedad.getText();
            String tratamiento = txtTratamiento.getText();

            if (datosCompletos(tipo, enfermedad, descripcion, añoInicio, enfermedad2, tratamiento)) {
                if (!tipo.equals("Crónico")) {
                    InsertarSQL.insertarAntecedente(id, tipo, enfermedad, descripcion);
                } else {
                    InsertarSQL.insertarAntecedenteCronico(id, enfermedad2, añoInicio, tratamiento);
                }
                btnImagenPerfil.setIcon(establecerIcono("FotoPerfil", 150, 150));
                comboTipo.setText("Tipo de enfermedad");
                comboEnfermedad.setText("Enfermedad");
                txtDescripcion.setText("Descripción");
                txtFechaInicio.setText("Año inicio");
                txtEnfermedad.setText("Enfermedad");
                txtTratamiento.setText("Tratamiento");
                txtNombre.setText("Nombre");
                txtApellidoPaterno.setText("Apellido paterno");
                txtApellidoMaterno.setText("Apellido materno");

                comboTipo.setEnabled(false);
                comboEnfermedad.setEnabled(false);
                txtDescripcion.setEnabled(false);
                txtFechaInicio.setEnabled(false);
                txtEnfermedad.setEnabled(false);
                txtTratamiento.setEnabled(false);
            }
            opcionBusqueda = null;
        }
    }

    /**
     *
     * metodos
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

    private boolean normal (String enfermedad, String descripcion) {
        if (!enfermedad.equals("Enfermedad")) {
            if (!descripcion.equals("Descripción")) {
                return true;
            } else {
                errorDescripcion.setVisible(true);
                return false;
            }
        } else {
            errorEnfermedad1.setVisible(true);
            return false;
        }
    }

    private boolean cronico (String añoInicio, String enfermedad, String tratamiento) {
        if (!añoInicio.equals("Año inicio")) {
            if (!enfermedad.equals("Enfermedad")) {
                if (!tratamiento.equals("Tratamiento")) {
                    return true;
                } else {
                    errorTratamiento.setVisible(true);
                    return false;
                }
            } else {
                errorEnfermedad2.setVisible(true);
                return false;
            }
        } else {
            errorAño.setVisible(true);
            return false;
        }
    }

    private boolean datosCompletos (String tipo, String enfermedad, String descripcion, String añoInicio, String enfermedad2, String tratamiento) {
        if (!tipo.equals("Tipo de enfermedad")) {
            if (!tipo.equals("Crónico")) {
                return normal(enfermedad, descripcion);
            } else {
                return cronico(añoInicio, enfermedad2, tratamiento);
            }
        } else {
            errorTipo.setVisible(true);
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

    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(listaBuscador);
        f.add(listaTipo);
        f.add(listaEnfermedad);
        f.add(errorTipo);
        f.add(errorEnfermedad1);
        f.add(errorDescripcion);
        f.add(errorAño);
        f.add(errorEnfermedad2);
        f.add(errorTratamiento);
        f.add(new PacienteAntecedenteVista());
        f.setDefaultCloseOperation(3);
        f.setUndecorated(true);
        f.setSize(800, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

}
