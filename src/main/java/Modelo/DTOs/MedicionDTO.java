package Modelo.DTOs;

import java.time.LocalDate;

/**
 *
 * @author Danny
 */
public class MedicionDTO {
    private String cedulaCliente; // Referencia como String en lugar de objeto Cliente
    private float peso;
    private float estatura;
    private float porcGrasa;
    private float porcMusculo;
    private float porcGrasaVis;
    private float cintura;
    private float pecho;
    private float muslo;
    private LocalDate fechaDeMedicion;

    // Constructor privado para el Builder
    private MedicionDTO(Builder builder) {
        this.cedulaCliente = builder.cedulaCliente;
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

    // Builder class
    public static class Builder {
        private String cedulaCliente;
        private float peso;
        private float estatura;
        private float porcGrasa;
        private float porcMusculo;
        private float porcGrasaVis;
        private float cintura;
        private float pecho;
        private float muslo;
        private LocalDate fechaDeMedicion;

        public Builder cedulaCliente(String cedulaCliente) {
            this.cedulaCliente = cedulaCliente;
            return this;
        }

        public Builder peso(float peso) {
            this.peso = peso;
            return this;
        }

        public Builder estatura(float estatura) {
            this.estatura = estatura;
            return this;
        }

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

        public MedicionDTO build() {
            return new MedicionDTO(this);
        }
    }

    // Getters y Setters
    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public float getPorcGrasa() {
        return porcGrasa;
    }

    public void setPorcGrasa(float porcGrasa) {
        this.porcGrasa = porcGrasa;
    }

    public float getPorcMusculo() {
        return porcMusculo;
    }

    public void setPorcMusculo(float porcMusculo) {
        this.porcMusculo = porcMusculo;
    }

    public float getPorcGrasaVis() {
        return porcGrasaVis;
    }

    public void setPorcGrasaVis(float porcGrasaVis) {
        this.porcGrasaVis = porcGrasaVis;
    }

    public float getCintura() {
        return cintura;
    }

    public void setCintura(float cintura) {
        this.cintura = cintura;
    }

    public float getPecho() {
        return pecho;
    }

    public void setPecho(float pecho) {
        this.pecho = pecho;
    }

    public float getMuslo() {
        return muslo;
    }

    public void setMuslo(float muslo) {
        this.muslo = muslo;
    }

    public LocalDate getFechaDeMedicion() {
        return fechaDeMedicion;
    }

    public void setFechaDeMedicion(LocalDate fechaDeMedicion) {
        this.fechaDeMedicion = fechaDeMedicion;
    }

    @Override
    public String toString() {
        return "MedicionDTO{" +
                "cedulaCliente='" + cedulaCliente + '\'' +
                ", peso=" + peso +
                ", estatura=" + estatura +
                ", porcGrasa=" + porcGrasa +
                ", porcMusculo=" + porcMusculo +
                ", porcGrasaVis=" + porcGrasaVis +
                ", cintura=" + cintura +
                ", pecho=" + pecho +
                ", muslo=" + muslo +
                ", fechaDeMedicion=" + fechaDeMedicion +
                '}';
    }
}
