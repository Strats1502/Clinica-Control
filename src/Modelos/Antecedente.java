package Modelos;

/**
 * Created by esva on 22/04/17.
 */
public class Antecedente {
    private int idPaciente;
    private String tipo;
    private String enfermedad;
    private String descripcion;

    public Antecedente(int idPaciente, String tipo, String enfermedad, String descripcion) {
        this.idPaciente = idPaciente;
        this.tipo = tipo;
        this.enfermedad = enfermedad;
        this.descripcion = descripcion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
