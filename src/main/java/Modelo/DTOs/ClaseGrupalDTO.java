package Modelo.DTOs;

/**
 *
 * @author Danny
 */
public class ClaseGrupalDTO {
    private String codigo;
    private int cupoMax;
    private int numSalon;
    private String especialidad;
    private String horario;
    private String cedulaInstructor; // Referencia como String en lugar de objeto Instructor

    // Constructor privado para el Builder
    private ClaseGrupalDTO(Builder builder) {
        this.codigo = builder.codigo;
        this.cupoMax = builder.cupoMax;
        this.numSalon = builder.numSalon;
        this.especialidad = builder.especialidad;
        this.horario = builder.horario;
        this.cedulaInstructor = builder.cedulaInstructor;
    }

    // Builder class
    public static class Builder {
        private String codigo;
        private int cupoMax;
        private int numSalon;
        private String especialidad;
        private String horario;
        private String cedulaInstructor;

        public Builder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder cupoMax(int cupoMax) {
            this.cupoMax = cupoMax;
            return this;
        }

        public Builder numSalon(int numSalon) {
            this.numSalon = numSalon;
            return this;
        }

        public Builder especialidad(String especialidad) {
            this.especialidad = especialidad;
            return this;
        }

        public Builder horario(String horario) {
            this.horario = horario;
            return this;
        }

        public Builder cedulaInstructor(String cedulaInstructor) {
            this.cedulaInstructor = cedulaInstructor;
            return this;
        }

        public ClaseGrupalDTO build() {
            return new ClaseGrupalDTO(this);
        }
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCupoMax() {
        return cupoMax;
    }

    public void setCupoMax(int cupoMax) {
        this.cupoMax = cupoMax;
    }

    public int getNumSalon() {
        return numSalon;
    }

    public void setNumSalon(int numSalon) {
        this.numSalon = numSalon;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCedulaInstructor() {
        return cedulaInstructor;
    }

    public void setCedulaInstructor(String cedulaInstructor) {
        this.cedulaInstructor = cedulaInstructor;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + "\n" +
                "Cupo Máximo: " + cupoMax + "\n" +
                "Número de Salón: " + numSalon + "\n" +
                "Especialidad: " + especialidad + "\n" +
                "Horario: " + horario + "\n" +
                "Cédula Instructor: " + cedulaInstructor;
    }
}