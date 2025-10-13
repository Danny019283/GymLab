/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.DTOs.ClaseGrupalDTO;

/**
 *
 * @author Danny
 */
public class ClaseGrupal {
    private String codigo;
    private int cupoMax;
    private int numSalon;
    private String especialidad;
    private String horario;
    private Instructor instructor;

    // Constructor vacío
    public ClaseGrupal() {
        this.codigo = "";
        this.cupoMax = 0;
        this.numSalon = 0;
        this.especialidad = "";
        this.horario = "";
        this.instructor = new Instructor();
    }

    // Constructor con todos los atributos
    public ClaseGrupal(String codigo, int cupoMax, int numSalon,
                       String especialidad, String horario, Instructor instructor) {
        this.codigo = codigo;
        this.cupoMax = cupoMax;
        this.numSalon = numSalon;
        this.especialidad = especialidad;
        this.horario = horario;
        this.instructor = instructor;
    }

    // Constructor con Builder
    public ClaseGrupal(Builder builder) {
        this.codigo = builder.codigo;
        this.cupoMax = builder.cupoMax;
        this.numSalon = builder.numSalon;
        this.especialidad = builder.especialidad;
        this.horario = builder.horario;
        this.instructor = builder.instructor;
    }

    public static class Builder {
        private String codigo;
        private int cupoMax;
        private int numSalon;
        private String especialidad;
        private String horario;
        private Instructor instructor;

        public Builder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder cupoMax(int cupoMax) {
            this.cupoMax = cupoMax;
            return this;
        }

        public Builder numSalon(int numSalon) {
            this.numSalon = numSalon;
            return this;
        }

        public Builder especialidad(String especialidad) {
            this.especialidad = especialidad;
            return this;
        }

        public Builder horario(String horario) {
            this.horario = horario;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public ClaseGrupal build() {
            return new ClaseGrupal(this);
        }
    }

    // Método para convertir de DTO a Entidad
    public static ClaseGrupal fromDTO(ClaseGrupalDTO dto, Instructor instructor) {
        ClaseGrupal claseGrupal = new ClaseGrupal();
        claseGrupal.setCodigo(dto.getCodigo());
        claseGrupal.setCupoMax(dto.getCupoMax());
        claseGrupal.setNumSalon(dto.getNumSalon());
        claseGrupal.setEspecialidad(dto.getEspecialidad());
        claseGrupal.setHorario(dto.getHorario());
        claseGrupal.setInstructor(instructor);
        return claseGrupal;
    }

    // Método para convertir de Entidad a DTO
    public ClaseGrupalDTO toDTO() {
        return new ClaseGrupalDTO.Builder()
                .codigo(this.codigo)
                .cupoMax(this.cupoMax)
                .numSalon(this.numSalon)
                .especialidad(this.especialidad)
                .horario(this.horario)
                .cedulaInstructor(this.instructor != null ? this.instructor.getCedula() : "")
                .build();
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getCupoMax() { return cupoMax; }
    public void setCupoMax(int cupoMax) { this.cupoMax = cupoMax; }

    public int getNumSalon() { return numSalon; }
    public void setNumSalon(int numSalon) { this.numSalon = numSalon; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    // Corrección en el setter - el parámetro debe ser Instructor, no String
    public void setInstructor(String instructorCedula) {
        // Este método podría necesitar buscar el instructor por cédula
        // Por ahora, creamos un instructor básico con la cédula
        Instructor inst = new Instructor();
        inst.setCedula(instructorCedula);
        this.instructor = inst;
    }

    public String getInfo() {
        return
                "Especialidad: " + especialidad + "\n" +
                "Num Salon: " + numSalon + "\n" +
                "Horario: " + horario + "\n" +
                "Instructor: " + (instructor != null ? instructor.getCedula() : "N/A") + "\n";
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + "\n" +
                "Cupo Maximo: " + cupoMax + "\n" +
                "Num Salon: " + numSalon + "\n" +
                "Especialidad: " + especialidad + "\n" +
                "Horario: " + horario + "\n" +
                "Instructor: " + (instructor != null ? instructor.getCedula() : "N/A") + "\n";
    }
}