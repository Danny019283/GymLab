/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Danny
 */
public class Cliente {
    private String cedula;
    private String nombre;
    private int telef;
    private String correo;
    private String fechaNac;
    private String sexo;
    private String fechaInscrip;
    private int edad;
    private Instructor instructor;

    // Constructor vacío
    public Cliente() {
        this.cedula = "";
        this.nombre = "";
        this.telef = 0;
        this.correo = "";
        this.fechaNac = "";
        this.sexo = "";
        this.fechaInscrip = "";
        this.edad = 0;
        this.instructor = new Instructor();
    }

    // Constructor con todos los atributos
    public Cliente(String cedula, String nombre, int telef, String correo,
                   String fechaNac, String sexo, String fechaInscrip,
                   int edad, Instructor instructor) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telef = telef;
        this.correo = correo;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.fechaInscrip = fechaInscrip;
        this.edad = edad;
        this.instructor = instructor;
    }

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getTelef() { return telef; }
    public void setTelef(int telef) { this.telef = telef; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getFechaNac() { return fechaNac; }
    public void setFechaNac(String fechaNac) { this.fechaNac = fechaNac; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getFechaInscrip() { return fechaInscrip; }
    public void setFechaInscrip(String fechaInscrip) { this.fechaInscrip = fechaInscrip; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    @Override
    public String toString() {
        return "Cliente{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telef=" + telef +
                ", correo='" + correo + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", sexo='" + sexo + '\'' +
                ", fechaInscrip='" + fechaInscrip + '\'' +
                ", edad=" + edad +
                ", instructor=" + (instructor != null ? instructor.getNombreCom() : "N/A") +
                '}';
    }
}

