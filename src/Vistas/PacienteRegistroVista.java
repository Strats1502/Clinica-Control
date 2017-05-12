package Vistas;

import ComponentesBeauty.BeautyBlackButton;
import ComponentesBeauty.BeautyCheckbox;
import ComponentesBeauty.BeautyImageButton;
import ComponentesBeauty.BeautyTextField;

import javax.swing.*;

/**
 * Created by esva on 11/05/17.
 */
public class PacienteRegistroVista extends CustomVista {
    //Componentes
    BeautyImageButton btnImagenPaciente;
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

    public PacienteRegistroVista() {
        componentes();
    }

    private void componentes() {
        btnImagenPaciente = new BeautyImageButton(establecerIcono("FotoPerfil", 150, 150), 40, 70, 150, 150);
        btnNuevo = new BeautyImageButton(establecerIcono("Nuevo", 20, 20), 620, 130, 20, 20);
        btnEditar = new BeautyImageButton(establecerIcono("Editar", 20, 20), 660, 130, 20, 20);
        btnAntecedentes = new BeautyImageButton(establecerIcono("Antecedentes", 20, 20), 700, 130, 20, 20);
        btnRecomendaciones = new BeautyImageButton(establecerIcono("Recomendacion", 20, 20), 740, 130, 20, 20);
        txtBuscar = new BeautyTextField("Buscar...", 300, 120, 300, 30);

        txtNombre = new BeautyTextField("Nombre", 60, 270, 200, 20);
        txtApellidoPaterno = new BeautyTextField("Apellido Paterno", 310, 270, 200, 20);
        txtApellidoMaterno = new BeautyTextField("Apellido Materno", 570, 270, 200, 20);
        txtFechaNacimiento = new BeautyTextField("Fecha Nacimiento", 60, 330, 200,20);
        txtReligion = new BeautyTextField("Religion", 310, 330, 200,20);
        txtCorreo = new BeautyTextField("Correo", 60, 390, 200, 20);
        txtTelefono =  new BeautyTextField("Teléfono", 310, 390, 200, 20);
        txtTelefonoEmergencias = new BeautyTextField("Teléfono Emergencias", 570, 390, 200, 20);

        checkActivo = new BeautyCheckbox("Activo", 60, 450, 200);
        btnGuardar = new BeautyBlackButton("Guardar", 630, 440, 100, 30);

        this.add(btnImagenPaciente);
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


    public static void main(String[] args) {
        JFrame f = new JFrame("eee");
        f.add(new PacienteRegistroVista());
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(3);
        f.setSize(800, 500);
        f.setVisible(true);

    }

}
