/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Danny
 */
public class Medicion {
    private Cliente cliente;
    private float peso;
    private float estatura;
    private float porcGrasa;
    private float porcMusculo;
    private int edadMetabolica;
    private float porcGrasaVis;
    private float cintura;
    private float pecho;
    private float muslo;
    private float imc;
    private String clasifImc;
    private float recomAgua;
    private float recomProte;

    // Constructor vacío
    public Medicion() {
        this.cliente = new Cliente();
        this.peso = 0.0f;
        this.estatura = 0.0f;
        this.porcGrasa = 0.0f;
        this.porcMusculo = 0.0f;
        this.edadMetabolica = 0;
        this.porcGrasaVis = 0.0f;
        this.cintura = 0.0f;
        this.pecho = 0.0f;
        this.muslo = 0.0f;
        this.imc = 0.0f;
        this.clasifImc = "";
        this.recomAgua = 0.0f;
        this.recomProte = 0.0f;
    }

    // Constructor con todos los atributos
    public Medicion(Cliente cliente, float peso, float estatura, float porcGrasa,
                    float porcMusculo, int edadMetabolica, float porcGrasaVis,
                    float cintura, float pecho, float muslo, float imc,
                    String clasifImc, float recomAgua, float recomProte) {
        this.cliente = cliente;
        this.peso = peso;
        this.estatura = estatura;
        this.porcGrasa = porcGrasa;
        this.porcMusculo = porcMusculo;
        this.edadMetabolica = edadMetabolica;
        this.porcGrasaVis = porcGrasaVis;
        this.cintura = cintura;
        this.pecho = pecho;
        this.muslo = muslo;
        this.imc = imc;
        this.clasifImc = clasifImc;
        this.recomAgua = recomAgua;
        this.recomProte = recomProte;
    }

    // Getters y Setters
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public float getPeso() { return peso; }
    public void setPeso(float peso) { this.peso = peso; }

    public float getEstatura() { return estatura; }
    public void setEstatura(float estatura) { this.estatura = estatura; }

    public float getPorcGrasa() { return porcGrasa; }
    public void setPorcGrasa(float porcGrasa) { this.porcGrasa = porcGrasa; }

    public float getPorcMusculo() { return porcMusculo; }
    public void setPorcMusculo(float porcMusculo) { this.porcMusculo = porcMusculo; }

    public int getEdadMetabolica() { return edadMetabolica; }
    public void setEdadMetabolica(int edadMetabolica) { this.edadMetabolica = edadMetabolica; }

    public float getPorcGrasaVis() { return porcGrasaVis; }
    public void setPorcGrasaVis(float porcGrasaVis) { this.porcGrasaVis = porcGrasaVis; }

    public float getCintura() { return cintura; }
    public void setCintura(float cintura) { this.cintura = cintura; }

    public float getPecho() { return pecho; }
    public void setPecho(float pecho) { this.pecho = pecho; }

    public float getMuslo() { return muslo; }
    public void setMuslo(float muslo) { this.muslo = muslo; }

    public float getImc() { return imc; }
    public void setImc(float imc) { this.imc = imc; }

    public String getClasifImc() { return clasifImc; }
    public void setClasifImc(String clasifImc) { this.clasifImc = clasifImc; }

    public float getRecomAgua() { return recomAgua; }
    public void setRecomAgua(float recomAgua) { this.recomAgua = recomAgua; }

    public float getRecomProte() { return recomProte; }
    public void setRecomProte(float recomProte) { this.recomProte = recomProte; }

    @Override
    public String toString() {
        return "Medicion{" +
                "cliente=" + (cliente != null ? cliente.getNombre() : "N/A") +
                ", peso=" + peso +
                ", estatura=" + estatura +
                ", porcGrasa=" + porcGrasa +
                ", porcMusculo=" + porcMusculo +
                ", edadMetabolica=" + edadMetabolica +
                ", porcGrasaVis=" + porcGrasaVis +
                ", cintura=" + cintura +
                ", pecho=" + pecho +
                ", muslo=" + muslo +
                ", imc=" + imc +
                ", clasifImc='" + clasifImc + '\'' +
                ", recomAgua=" + recomAgua +
                ", recomProte=" + recomProte +
                '}';
    }
}
