package Modelos;

/**
 * Created by esva on 22/04/17.
 */
public class Recomendacion {
    private int idPaciente;
    private int idUsuario;
    private String recomendacion;

    public Recomendacion(int idPaciente, int idUsuario, String recomendacion) {
        this.idPaciente = idPaciente;
        this.idUsuario = idUsuario;
        this.recomendacion = recomendacion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }
}
