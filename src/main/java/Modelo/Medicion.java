/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.time.LocalDate;

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
    private float porcGrasaVis;
    private float cintura;
    private float pecho;
    private float muslo;
    private LocalDate fechaDeMedicion;

    // Constructor vacío
    public Medicion() {
        this.cliente = new Cliente();
        this.peso = 0.0f;
        this.estatura = 0.0f;
        this.porcGrasa = 0.0f;
        this.porcMusculo = 0.0f;
        this.porcGrasaVis = 0.0f;
        this.cintura = 0.0f;
        this.pecho = 0.0f;
        this.muslo = 0.0f;
    }

    // Constructor con todos los atributos
    public Medicion(Cliente cliente, float peso, float estatura, float porcGrasa,
                    float porcMusculo, float porcGrasaVis,
                    float cintura, float pecho, float muslo, LocalDate fechaDeMedicion) {
        this.cliente = cliente;
        this.peso = peso;
        this.estatura = estatura;
        this.porcGrasa = porcGrasa;
        this.porcMusculo = porcMusculo;
        this.porcGrasaVis = porcGrasaVis;
        this.cintura = cintura;
        this.pecho = pecho;
        this.muslo = muslo;
        this.fechaDeMedicion = fechaDeMedicion;
    }


    // Constructor con Builder
    public Medicion(Builder builder) {
        this.cliente = builder.cliente;
        this.peso = builder.peso;
        this.estatura = builder.estatura;
        this.porcGrasa = builder.porcGrasa;
        this.porcMusculo = builder.porcMusculo;
        this.porcGrasaVis = builder.porcGrasaVis;
        this.cintura = builder.cintura;
        this.pecho = builder.pecho;
        this.muslo = builder.muslo;
        this.fechaDeMedicion = builder.fechaDeMedicion;
    }

    public static class Builder{
        private Cliente cliente;
        private float peso;
        private float estatura;
        private float porcGrasa;
        private float porcMusculo;
        private float porcGrasaVis;
        private float cintura;
        private float pecho;
        private float muslo;
        private LocalDate fechaDeMedicion;

        public Builder cliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder peso(float peso) {
            this.peso = peso;
            return this;
        }

        public  Builder estatura(float estatura) {
            this.estatura = estatura;
            return this;
        }
        //crea los demas con el mismo formato
        public Builder porcGrasa(float porcGrasa) {
            this.porcGrasa = porcGrasa;
            return this;
        }

        public Builder porcMusculo(float porcMusculo) {
            this.porcMusculo = porcMusculo;
            return this;
        }

        public Builder porcGrasaVis(float porcGrasaVis) {
            this.porcGrasaVis = porcGrasaVis;
            return this;
        }

        public Builder cintura(float cintura) {
            this.cintura = cintura;
            return this;
        }

        public Builder pecho(float pecho) {
            this.pecho = pecho;
            return this;
        }

        public Builder muslo(float muslo) {
            this.muslo = muslo;
            return this;
        }

        public Builder fechaDeMedicion(LocalDate fechaDeMedicion) {
            this.fechaDeMedicion = fechaDeMedicion;
            return this;
        }

        public Medicion build() {
            return new Medicion(this);
        }
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

    public float getPorcGrasaVis() { return porcGrasaVis; }
    public void setPorcGrasaVis(float porcGrasaVis) { this.porcGrasaVis = porcGrasaVis; }

    public float getCintura() { return cintura; }
    public void setCintura(float cintura) { this.cintura = cintura; }

    public float getPecho() { return pecho; }
    public void setPecho(float pecho) { this.pecho = pecho; }

    public LocalDate getFechaDeMedicion() { return fechaDeMedicion; }
    public void setFechaDeMedicion(LocalDate fechaDeMedicion) { this.fechaDeMedicion = fechaDeMedicion; }

    public float getMuslo() { return muslo; }
    public void setMuslo(float muslo) { this.muslo = muslo; }
    public float calcularIMC() {
        return this.peso / (this.estatura * this.estatura);
    }

    public String clasificarIMC() {
        float imc = calcularIMC();
        if (imc < 16) {
            return "Delgadez severa";
        } else if (imc > 16.01 && imc < 16.99) {
            return "Delgadez moderada";
        } else if (imc > 17.0 && imc < 18.49) {
            return "Delgadez leve";
        } else if (imc > 18.5 && imc < 24.99) {
            return  "Normal";
        } else if (imc > 25.0 && imc < 29.99) {
            return "Preobesidad";
        } else if (imc > 30.0 && imc < 34.99) {
            return "Obesidad leve";
        } else if (imc > 35.0 && imc < 39.99) {
            return "Obesidad media";
        } else  {
            return"Obesidad morbida";
        }
    }

    public double calcEdadMetabolica(){
        double edadMetabolica = (10 * this.peso) + (6.25 * this.estatura) - (5 * this.cliente.getEdad());
        if (this.cliente.getSexo().equals("Masculino")) {
            return edadMetabolica + 5;
        }
        else {
            return edadMetabolica - 161;
        }
    }

    public double calcCantVasosDeAguaDia(){
        return this.peso * 7;
    }

    public double calcCantProteinasDia(boolean haceEjercicio){
        double recomProte = 0.8f * this.peso;
        if (this.cliente.getSexo().equals("Masculino") && haceEjercicio) {
            return recomProte + (2.1 * this.peso);
        }
        else if (this.cliente.getSexo().equals("Femenino") && haceEjercicio) {
            return recomProte + (1.7f * this.peso);
        }
        return recomProte;
    }

    public String reporteDeMedicion(boolean haceEjercicio) {
        return "Nombre del cliente: " + cliente.getNombre() + "\n" +
                "Cedula del cliente: " + cliente.getCedula() + "\n" +
                "Fecha de medicion: " + this.fechaDeMedicion + "\n" +
                "Porcentaje de grasa: " + this.porcGrasa + " %\n" +
                "Porcentaje de musculo: " + this.porcMusculo + " %\"n" +
                "Edad Metabólica: " + String.format("%.2f", calcEdadMetabolica()) + " años\n" +
                "Porcentaje de grasa visceral: " + this.porcGrasaVis + " %\n" +
                "Cimtura: " + this.cintura + " cm\n" +
                "pecho: " + this.pecho + " cm\n" +
                "Muslo: " + this.muslo + " cm\n" +
                "Peso: " + this.peso + " kg\n" +
                "Estatura: " + this.estatura + " m\n" +
                "IMC: " + String.format("%.2f", calcularIMC()) + " (" + clasificarIMC() + ")\n" +
                "Cantidad recomendada de vasos de agua por día: " + String.format("%.2f", calcCantVasosDeAguaDia()) + " ml\n" +
                "Cantidad recomendada de proteínas por día: " + String.format("%.2f", calcCantProteinasDia(haceEjercicio)) + " g\n";
    }

}
