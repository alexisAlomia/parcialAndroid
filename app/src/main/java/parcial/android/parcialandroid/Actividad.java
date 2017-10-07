package parcial.android.parcialandroid;


import java.util.Date;

public class Actividad {

    private String nombreActividad;
    private Date fechaActividad;

    public Actividad() {
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }
}
