/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import Modelo.DTOs.InstructorDTO;

/**
 *
 * @author Danny
 */

import java.util.ArrayList;

public class Instructor {
    private String cedula;
    private String nombreCom;
    private int telef;
    private String correo;
    private String fechaNac;
    private String codigoSucursal;
    private ArrayList<String> especialidad;

    // Constructor vacío
    public Instructor() {
        this.cedula = "";
        this.nombreCom = "";
        this.telef = 0;
        this.correo = "";
        this.fechaNac = "";
        this.codigoSucursal = "";
        this.especialidad = new ArrayList<>();
    }

    // Constructor con todos los atributos
    public Instructor(String cedula, String nombreCom, int telef, String correo,
                      String fechaNac, ArrayList<String> especialidad, String CodigoSucursal) {
        this.cedula = cedula;
        this.nombreCom = nombreCom;
        this.telef = telef;
        this.correo = correo;
        this.fechaNac = fechaNac;
        this.especialidad = especialidad != null ? new ArrayList<>(especialidad) : new ArrayList<>();
        this.codigoSucursal = CodigoSucursal;
    }

    //constructor con builder
    public Instructor(Builder builder) {
        this.cedula = builder.cedula;
        this.nombreCom = builder.nombreCom;
        this.telef = builder.telef;
        this.correo = builder.correo;
        this.fechaNac = builder.fechaNac;
        this.especialidad = builder.especialidad != null ? new ArrayList<>(builder.especialidad) : new ArrayList<>();
        this.codigoSucursal = builder.codigoSucursal;
    }

    public static class Builder{
        private String cedula;
        private String nombreCom;
        private int telef;
        private String correo;
        private String fechaNac;
        private ArrayList<String> especialidad;
        private String codigoSucursal;

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

        public Builder especialidad(ArrayList<String> especialidad) {
            this.especialidad = especialidad;
            return this;
        }

        public Builder codigoSucursal(String codigoSucursal) {
            this.codigoSucursal = codigoSucursal;
            return this;
        }

        public Instructor build() {
            return new Instructor(this);
        }
    }

    // Método para convertir de DTO a Entidad
    public static Instructor fromDTO(InstructorDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setCedula(dto.getCedula());
        instructor.setNombreCom(dto.getNombreCom());
        instructor.setTelef(dto.getTelef());
        instructor.setCorreo(dto.getCorreo());
        instructor.setFechaNac(dto.getFechaNac());
        instructor.setCodigoSucursal(dto.getCodigoSucursal());
        instructor.setEspecialidad(dto.getEspecialidad());
        return instructor;
    }

    // Método para convertir de Entidad a DTO
    public InstructorDTO toDTO() {
        return new InstructorDTO.Builder()
                .cedula(this.cedula)
                .nombreCom(this.nombreCom)
                .telef(this.telef)
                .correo(this.correo)
                .fechaNac(this.fechaNac)
                .codigoSucursal(this.codigoSucursal)
                .especialidad(this.especialidad)
                .build();
    }

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombreCom() { return nombreCom; }
    public void setNombreCom(String nombreCom) { this.nombreCom = nombreCom; }

    public int getTelef() { return telef; }
    public void setTelef(int telef) { this.telef = telef; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getFechaNac() { return fechaNac; }
    public void setFechaNac(String fechaNac) { this.fechaNac = fechaNac; }

    public ArrayList<String> getEspecialidad() { return new ArrayList<>(especialidad); }
    public void setEspecialidad(ArrayList<String> especialidad) { this.especialidad = new ArrayList<>(especialidad); }

    public String getCodigoSucursal() { return codigoSucursal; }
    public void setCodigoSucursal(String codigoSucursal) { this.codigoSucursal = codigoSucursal; }

    @Override
    public String toString() {
        return "Instructor{" +
                "cedula='" + cedula + '\'' +
                ", nombreCom='" + nombreCom + '\'' +
                ", telef=" + telef +
                ", correo='" + correo + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", especialidad=" + especialidad +
                "codigo de sucursal: " + codigoSucursal +
                '}';
    }
}