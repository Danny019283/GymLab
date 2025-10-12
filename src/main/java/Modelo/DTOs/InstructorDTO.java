package Modelo.DTOs;

import java.util.ArrayList;

/**
 *
 * @author Danny
 */
public class InstructorDTO {
    private String cedula;
    private String nombreCom;
    private int telef;
    private String correo;
    private String fechaNac;
    private String codigoSucursal;
    private ArrayList<String> especialidad;

    // Constructor privado para el Builder
    private InstructorDTO(Builder builder) {
        this.cedula = builder.cedula;
        this.nombreCom = builder.nombreCom;
        this.telef = builder.telef;
        this.correo = builder.correo;
        this.fechaNac = builder.fechaNac;
        this.codigoSucursal = builder.codigoSucursal;
        this.especialidad = builder.especialidad != null ? new ArrayList<>(builder.especialidad) : new ArrayList<>();
    }

    // Builder class
    public static class Builder {
        private String cedula;
        private String nombreCom;
        private int telef;
        private String correo;
        private String fechaNac;
        private String codigoSucursal;
        private ArrayList<String> especialidad;

        public Builder cedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public Builder nombreCom(String nombreCom) {
            this.nombreCom = nombreCom;
            return this;
        }

        public Builder telef(int telef) {
            this.telef = telef;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder fechaNac(String fechaNac) {
            this.fechaNac = fechaNac;
            return this;
        }

        public Builder codigoSucursal(String codigoSucursal) {
            this.codigoSucursal = codigoSucursal;
            return this;
        }

        public Builder especialidad(ArrayList<String> especialidad) {
            this.especialidad = especialidad;
            return this;
        }

        public InstructorDTO build() {
            return new InstructorDTO(this);
        }
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreCom() {
        return nombreCom;
    }

    public void setNombreCom(String nombreCom) {
        this.nombreCom = nombreCom;
    }

    public int getTelef() {
        return telef;
    }

    public void setTelef(int telef) {
        this.telef = telef;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public ArrayList<String> getEspecialidad() {
        return new ArrayList<>(especialidad);
    }

    public void setEspecialidad(ArrayList<String> especialidad) {
        this.especialidad = new ArrayList<>(especialidad);
    }

    @Override
    public String toString() {
        return "InstructorDTO{" +
                "cedula='" + cedula + '\'' +
                ", nombreCom='" + nombreCom + '\'' +
                ", telef=" + telef +
                ", correo='" + correo + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", codigoSucursal='" + codigoSucursal + '\'' +
                ", especialidad=" + especialidad +
                '}';
    }
}