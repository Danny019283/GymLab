package Modelo.DTOs;

/**
 *
 * @author Danny
 */
public class SucursalDTO {
    private String cod;
    private String provi;
    private String canton;
    private String correo;
    private int telef;

    // Constructor privado para el Builder
    private SucursalDTO(Builder builder) {
        this.cod = builder.cod;
        this.provi = builder.provi;
        this.canton = builder.canton;
        this.correo = builder.correo;
        this.telef = builder.telef;
    }

    // Builder class
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

        public SucursalDTO build() {
            return new SucursalDTO(this);
        }
    }

    // Getters y Setters
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getProvi() {
        return provi;
    }

    public void setProvi(String provi) {
        this.provi = provi;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelef() {
        return telef;
    }

    public void setTelef(int telef) {
        this.telef = telef;
    }

    @Override
    public String toString() {
        return "SucursalDTO{" +
                "cod='" + cod + '\'' +
                ", provi='" + provi + '\'' +
                ", canton='" + canton + '\'' +
                ", correo='" + correo + '\'' +
                ", telef=" + telef +
                '}';
    }
}
