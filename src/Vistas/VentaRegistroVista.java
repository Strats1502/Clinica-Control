package Vistas;

import ComponentesBeauty.*;
import Conexion.ConexionSQL;

import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by esva on 14/05/17.
 */
public class VentaRegistroVista extends CustomVista {
    //Componentes
    BeautyTextField txtBuscar;
    BeautyImageButton btnNuevo;
    BeautyImageButton btnEditar;
    BeautyImageButton btnOpciones;
    BeautyImageButton btnFactura;
    BeautyBlackButton btnGuardar;

    BeautyTextField txtFolio;
    BeautyTextField txtFecha;
    BeautyComboBox  comboTipoCliente;
    BeautyComboBox comboFormaPago;
    BeautyTextField txtPaciente;

    BeautyTextField txtNombre;
    BeautyTextField txtApellidoPaterno;
    BeautyTextField txtApellidoMaterno;

    BeautyTextField txtMunicipio;
    BeautyTextField txtColonia;
    BeautyTextField txtCalleNumero;

    BeautyCheckbox checkActivo;

    //Lista de busqueda
    public static DefaultListModel dlmBuscador = new DefaultListModel();
    public static BeautyList listaBuscador = new BeautyList(dlmBuscador, 300, 150, 300, 140);

    //Lista tipo de cliente
    public static DefaultListModel dlmTipoCliente = new DefaultListModel();
    public static BeautyList listaTipoCliente = new BeautyList(dlmTipoCliente,360, 290, 100, 40);

    //Lista de paciente
    public static DefaultListModel dlmPaciente = new DefaultListModel();
    public static BeautyList listaPaciente = new BeautyList(dlmPaciente, 570, 290, 200, 200);

    //Lista forma de pago
    public static DefaultListModel dlmFormaPago = new DefaultListModel();
    public static BeautyList listaFormaPago = new BeautyList(dlmFormaPago, 80, 230,150,50);

    //Variables
    String opcionBusqueda = null;
    int id = 0;

    public VentaRegistroVista() {
        componentes();
    }

    private void componentes() {
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        btnOpciones = new BeautyImageButton(establecerIcono("LlegadaMercancia", 20, 20), 700, 130, 20, 20);
        btnFactura = new BeautyImageButton(establecerIcono("Factura", 20, 20), 740, 130, 20, 20);
        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);

        txtFolio = new BeautyTextField("Folio", 60, 120, 200, 30);
        comboFormaPago = new BeautyComboBox("Forma de pago", 60, 210, 200,20);
        txtFecha = new BeautyTextField("Fecha", 60, 270, 200, 20);
        comboTipoCliente = new BeautyComboBox("Tipo cliente", 310, 270, 200, 20);
        txtPaciente = new BeautyTextField("Paciente", 570, 270, 200, 20);

        txtNombre = new BeautyTextField("Nombre", 60, 330, 200, 20);
        txtApellidoPaterno = new BeautyTextField("Apellido paterno", 310, 330, 200, 20);
        txtApellidoMaterno = new BeautyTextField("Apellido materno", 570, 330, 200, 20);

        txtMunicipio = new BeautyTextField("Municipio", 60, 390, 200, 20);
        txtColonia = new BeautyTextField("Colonia", 310, 390, 200, 20);
        txtCalleNumero = new BeautyTextField("Calle/Número", 570, 390, 200, 20);

        checkActivo = new BeautyCheckbox("Activo", 60, 450, 200);

        txtPaciente.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellidoPaterno.setEnabled(false);
        txtApellidoMaterno.setEnabled(false);

        dlmTipoCliente.addElement("Paciente");
        dlmTipoCliente.addElement("Normal");

        dlmFormaPago.addElement("Crédito");
        dlmFormaPago.addElement("Contado");

        listaTipoCliente.addMouseListener(new ListaTipoClienteMouseAdapter());
        comboTipoCliente.addFocusListener(new ComboTipoClienteFocusAdapter());
        listaFormaPago.addMouseListener(new ListaFormaPagoMouseAdapter());
        comboFormaPago.addFocusListener(new ComboFormaPagoFocusAdapter());

        this.add(txtBuscar);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(btnOpciones);
        this.add(btnFactura);
        this.add(btnGuardar);
        this.add(txtFolio);
        this.add(comboFormaPago);
        this.add(txtFecha);
        this.add(comboTipoCliente);
        this.add(txtPaciente);
        this.add(txtNombre);
        this.add(txtApellidoPaterno);
        this.add(txtApellidoMaterno);
        this.add(txtMunicipio);
        this.add(txtColonia);
        this.add(txtCalleNumero);
        this.add(checkActivo);
        this.add(fondo());
    }

    private class ComboFormaPagoFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaFormaPago.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaFormaPago.setVisible(false);
        }
    }

    private class ListaFormaPagoMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String formaPago = listaFormaPago.getSelectedValue().toString();
            comboFormaPago.setText(formaPago);
            listaFormaPago.setVisible(false);
        }
    }

    private class ComboTipoClienteFocusAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            listaTipoCliente.setVisible(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            listaTipoCliente.setVisible(false);
        }
    }

    private class ListaTipoClienteMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String tipoCliente = listaTipoCliente.getSelectedValue().toString();
            comboTipoCliente.setText(tipoCliente);
            listaTipoCliente.setVisible(false);

            if (tipoCliente.equals("Paciente")) {
                txtPaciente.setEnabled(true);
                txtNombre.setEnabled(false);
                txtApellidoPaterno.setEnabled(false);
                txtApellidoMaterno.setEnabled(false);
            } else if (tipoCliente.equals("Normal")) {
                txtPaciente.setEnabled(false);
                txtNombre.setEnabled(true);
                txtApellidoPaterno.setEnabled(true);
                txtApellidoMaterno.setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(listaBuscador);
        f.add(listaTipoCliente);
        f.add(listaPaciente);
        f.add(listaFormaPago);
        f.add(new VentaRegistroVista());
        f.setDefaultCloseOperation(3);
        f.setUndecorated(true);
        f.setSize(800, 500);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

}
