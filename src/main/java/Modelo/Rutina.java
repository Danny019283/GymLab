/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Danny
 */
public class Rutina {
    private String pecho;
    private String triceps;
    private String biceps;
    private String piernas;
    private String espalda;

    // Constructor vacío
    public Rutina() {
        this.pecho = "";
        this.triceps = "";
        this.biceps = "";
        this.piernas = "";
        this.espalda = "";
    }

    // Constructor con todos los atributos
    public Rutina(String pecho, String triceps, String biceps, String piernas, String espalda) {
        this.pecho = pecho;
        this.triceps = triceps;
        this.biceps = biceps;
        this.piernas = piernas;
        this.espalda = espalda;
    }

    // Getters y Setters
    public String getPecho() { return pecho; }
    public void setPecho(String pecho) { this.pecho = pecho; }

    public String getTriceps() { return triceps; }
    public void setTriceps(String triceps) { this.triceps = triceps; }

    public String getBiceps() { return biceps; }
    public void setBiceps(String biceps) { this.biceps = biceps; }

    public String getPiernas() { return piernas; }
    public void setPiernas(String piernas) { this.piernas = piernas; }

    public String getEspalda() { return espalda; }
    public void setEspalda(String espalda) { this.espalda = espalda; }

    @Override
    public String toString() {
        return "pecho: " + pecho + "\n" +
                "triceps: " + triceps + "\n" +
                "biceps: " + biceps + "\n" +
                "piernas: " + piernas + "\n" +
                "espalda: " + espalda;
    }
}

