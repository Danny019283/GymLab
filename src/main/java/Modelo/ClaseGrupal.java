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
    private String id;
    private int cupoMax;
    private int numSalon;
    private String especialidad;
    private String horario;
    private Instructor instructor;

    // Constructor vacío
    public ClaseGrupal() {
        this.id = "";
        this.cupoMax = 0;
        this.numSalon = 0;
        this.especialidad = "";
        this.horario = "";
        this.instructor = new Instructor();
    }

    // Constructor con todos los atributos
    public ClaseGrupal(String id, int cupoMax, int numSalon,
                       String especialidad, String horario, Instructor instructor) {
        this.id = id;
        this.cupoMax = cupoMax;
        this.numSalon = numSalon;
        this.especialidad = especialidad;
        this.horario = horario;
        this.instructor = instructor;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    @Override
    public String toString() {
        return "ClaseGrupal{" +
                "id='" + id + '\'' +
                ", cupoMax=" + cupoMax +
                ", numSalon=" + numSalon +
                ", especialidad='" + especialidad + '\'' +
                ", horario='" + horario + '\'' +
                ", instructor=" + (instructor != null ? instructor.getNombreCom() : "N/A") +
                '}';
        
    }
}
