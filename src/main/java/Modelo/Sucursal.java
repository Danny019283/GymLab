/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

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


