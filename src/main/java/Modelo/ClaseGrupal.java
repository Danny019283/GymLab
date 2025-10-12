/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

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
    private String instructor;

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
                       String especialidad, String horario, String instructor) {
        this.codigo = codigo;
        this.cupoMax = cupoMax;
        this.numSalon = numSalon;
        this.especialidad = especialidad;
        this.horario = horario;
        this.instructor = instructor;
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

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    @Override
    public String toString() {
        return "Codigo: " + codigo + "\n" +
                "Cupo Maximo: " + cupoMax + "\n" +
                "Num Salon: " + numSalon + "\n" +
                "Especialidad: " + especialidad + "\n" +
                "Horario: " + horario + "\n" +
                "Instructor: " + (instructor.getCedula()) + "\n";
        
    }
}
