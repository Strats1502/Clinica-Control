package Modelos;

/**
 * Created by esva on 22/04/17.
 */
public class Consulta {
    private int idVenta;
    private int idUsuario;
    private int idPaciente;

    public Consulta(int idVenta, int idUsuario, int idPaciente) {
        this.idVenta = idVenta;
        this.idUsuario = idUsuario;
        this.idPaciente = idPaciente;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
}
