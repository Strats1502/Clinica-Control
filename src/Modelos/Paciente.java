package Modelos;

import java.util.ArrayList;

/**
 * Created by Juan José Estrada Valtierra on 22/04/17.
 */
public class Paciente {
    private int id;
    private int usuarioAlta;
    private String fechaAlta;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private String religion;
    private String correo;
    private int telefono;
    private int telefonoEmergencias;
    private ArrayList<Antecedente> antecedentes;
    private ArrayList<Recomendacion> recomendaciones;

    public Paciente(int id, int usuarioAlta, String fechaAlta, String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento, String religion, String correo, int telefono, int telefonoEmergencias, ArrayList<Antecedente> antecedentes, ArrayList<Recomendacion> recomendaciones) {
        this.id = id;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = fechaAlta;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.religion = religion;
        this.correo = correo;
        this.telefono = telefono;
        this.telefonoEmergencias = telefonoEmergencias;
        this.antecedentes = antecedentes;
        this.recomendaciones = recomendaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(int usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getTelefonoEmergencias() {
        return telefonoEmergencias;
    }

    public void setTelefonoEmergencias(int telefonoEmergencias) {
        this.telefonoEmergencias = telefonoEmergencias;
    }

    public ArrayList<Antecedente> getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(ArrayList<Antecedente> antecedentes) {
        this.antecedentes = antecedentes;
    }

    public ArrayList<Recomendacion> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(ArrayList<Recomendacion> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }
}
