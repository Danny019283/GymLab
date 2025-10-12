/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.DTOs.SucursalDTO;

/**
 *
 * @author Danny
 */
public class Sucursal {
    private String cod;
    private String provi;
    private String canton;
    private String correo;
    private int telef;

    // Constructor vacío
    public Sucursal() {
        this.cod = "";
        this.provi = "";
        this.canton = "";
        this.correo = "";
        this.telef = 0;
    }

    // Constructor con todos los atributos
    public Sucursal(String cod, String provi, String canton, String correo, int telef) {
        this.cod = cod;
        this.provi = provi;
        this.canton = canton;
        this.correo = correo;
        this.telef = telef;
    }

    // Constructor con Builder
    public Sucursal(Builder builder) {
        this.cod = builder.cod;
        this.provi = builder.provi;
        this.canton = builder.canton;
        this.correo = builder.correo;
        this.telef = builder.telef;
    }

    public static class Builder {
        private String cod = "";
        private String provi = "";
        private String canton = "";
        private String correo = "";
        private int telef = 0;

        public Builder cod(String cod) {
            this.cod = cod;
            return this;
        }

        public Builder provi(String provi) {
            this.provi = provi;
            return this;
        }

        public Builder canton(String canton) {
            this.canton = canton;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder telef(int telef) {
            this.telef = telef;
            return this;
        }

        public Sucursal build() {
            return new Sucursal(this);
        }
    }

    // Método para convertir de DTO a Entidad
    public static Sucursal fromDTO(SucursalDTO dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setCod(dto.getCod());
        sucursal.setProvi(dto.getProvi());
        sucursal.setCanton(dto.getCanton());
        sucursal.setCorreo(dto.getCorreo());
        sucursal.setTelef(dto.getTelef());
        return sucursal;
    }

    // Método para convertir de Entidad a DTO
    public SucursalDTO toDTO() {
        return new SucursalDTO.Builder()
                .cod(this.cod)
                .provi(this.provi)
                .canton(this.canton)
                .correo(this.correo)
                .telef(this.telef)
                .build();
    }

    // Getters y Setters
    public String getCod() { return cod; }
    public void setCod(String cod) { this.cod = cod; }

    public String getProvi() { return provi; }
    public void setProvi(String provi) { this.provi = provi; }

    public String getCanton() { return canton; }
    public void setCanton(String canton) { this.canton = canton; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getTelef() { return telef; }
    public void setTelef(int telef) { this.telef = telef; }

    @Override
    public String toString() {
        return "Sucursal{" +
                "cod='" + cod + '\'' +
                ", provi='" + provi + '\'' +
                ", canton='" + canton + '\'' +
                ", correo='" + correo + '\'' +
                ", telef=" + telef +
                '}';
    }
}