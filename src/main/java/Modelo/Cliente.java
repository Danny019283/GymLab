/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import AccesoADatos.DAOMedicion;
import Modelo.DTOs.ClienteDTO;

/**
 *
 * @author Danny
 */
public class Cliente {
    private String cedula;
    private String nombre;
    private int telefono;
    private String correo;
    private String fechaNac;
    private String sexo;
    private String fechaInscrip;
    private int edad;
    private String instructor;
    private String sucursal;
    private DAOMedicion mediciones;

    // Constructor vacío
    public Cliente() {
        this.cedula = "";
        this.nombre = "";
        this.telefono = 0;
        this.correo = "";
        this.fechaNac = "";
        this.sexo = "";
        this.fechaInscrip = "";
        this.edad = 0;
        this.instructor = new Instructor();
        this.sucursal = new Sucursal();
        this.mediciones = new DAOMedicion();
    }

    // Constructor con todos los atributos
    public Cliente(String cedula, String nombre, int edad, int telefono, String correo,
                   String fechaNac, String sexo, String fechaInscrip,
                   String instructor, String sucursal) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.fechaInscrip = fechaInscrip;
        this.edad = edad;
        this.instructor = instructor;
        this.sucursal = sucursal;
    }

    //Constructor con Builder
    public Cliente(Builder builder) {
        this.cedula = builder.cedula;
        this.nombre = builder.nombre;
        this.telefono = builder.telefono;
        this.correo = builder.correo;
        this.fechaNac = builder.fechaNac;
        this.sexo = builder.sexo;
        this.fechaInscrip = builder.fechaInscrip;
        this.edad = builder.edad;
        this.instructor = builder.instructor;
        this.sucursal =builder.sucursal;
    }

    public static class Builder {
        private String cedula;
        private String nombre;
        private int telefono;
        private String correo;
        private String fechaNac;
        private String sexo;
        private String fechaInscrip;
        private int edad;
        private String instructor;
        private String sucursal;

        public Builder cedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder telefono(int telefono) {
            this.telefono = telefono;
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

        public Builder sexo(String sexo) {
            this.sexo = sexo;
            return this;
        }

        public Builder fechaInscrip(String fechaInscrip) {
            this.fechaInscrip = fechaInscrip;
            return this;
        }

        public Builder edad(int edad) {
            this.edad = edad;
            return this;
        }

        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder sucursal(String sucursal) {
            this.sucursal = sucursal;
            return this;
        }

        public Cliente build() {
            return new Cliente(this);
        }
    }

    public static Cliente fromDTO(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setCedula(dto.getCedula());
        cliente.setNombre(dto.getNombre());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreo(dto.getCorreo());
        cliente.setFechaNac(dto.getFechaNac());
        cliente.setSexo(dto.getSexo());
        cliente.setFechaInscrip(dto.getFechaInscrip());
        cliente.setEdad(dto.getEdad());
        cliente.setInstructor(dto.getInstructor());
        cliente.setSucursal(dto.getSucursal());
        return cliente;
    }

    public ClienteDTO toDTO() {
        return new ClienteDTO.Builder()
                .cedula(this.cedula)
                .nombre(this.nombre)
                .telefono(this.telefono)
                .correo(this.correo)
                .fechaNac(this.fechaNac)
                .sexo(this.sexo)
                .fechaInscrip(this.fechaInscrip)
                .edad(this.edad)
                .instructor(this.instructor)
                .sucursal(this.sucursal)
                .build();
    }

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getTelefono() { return telefono; }
    public void setTelefono(int telefono) { this.telefono = telefono; }

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

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public String getSucursal() {
        return sucursal;
    }
    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    @Override
    public String toString() {
        return "Cliente{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telef=" + telefono +
                ", correo='" + correo + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", sexo='" + sexo + '\'' +
                ", fechaInscrip='" + fechaInscrip + '\'' +
                ", edad=" + edad +
                ", instructor=" + (instructor != null ? instructor.getNombreCom() : "N/A") +
                ", sucursal=" + (sucursal != null ? sucursal.getCod() : "N/A") +
                '}';
    }
}